package com.ufu.crokage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.simple.Sentence;



@Component
public class CrokageUtils {
	private static List<String> stopWordsList;
	
	@PostConstruct
	public void initializeCrokageUtils() throws IOException {
		File file = new File(getClass().getClassLoader().getResource("stanford_stop_words.txt").getFile());
		stopWordsList = Files.readAllLines(file.toPath()); 		
		
	}

	public static boolean isNumeric(String str)
	{
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
	
	private static void assembleValidWords(Set<String> validWords, String[] words) {
		for(String word:words) {
			word = word.trim();
			if(!stopWordsList.contains(word) && !(word.length()<2) && !StringUtils.isBlank(word) && !isNumeric(word)) {
				validWords.add(word);
			}
			
		}
	}

	
	public static String removeAllPunctuations(String body) {
		body = body.replaceAll("\\p{Punct}+"," "); 
		body = body.replaceAll("[^\\x20-\\x7e]", " "); //non-UTF-8 chars
		return body;
	}
	
	public static String processQuery(String query, Boolean lemmatize) {
		query = query.toLowerCase();
		query = translateHTMLSimbols(query);
		
		//remove punctuations
		query = removeAllPunctuations(query);
		String[] words = query.split("\\s+");
		Set<String> validWords = new LinkedHashSet<>();
		
		//remove stop words or small words or numbers only
		assembleValidWords(validWords,words);
				
		String finalContent = String.join(" ", validWords);
		finalContent = finalContent.replaceAll("\u0000", "");
		
		if(lemmatize) {
			edu.stanford.nlp.simple.Document stfDoc = new edu.stanford.nlp.simple.Document(finalContent);
			List<String> sentList; 
					
			List<Sentence> sentences = stfDoc.sentences();
			if(!sentences.isEmpty()) {
				sentList= sentences.get(0).lemmas();
				finalContent = String.join(" ", sentList);
			}
		}
		
		validWords = null;
		query = null;
		words = null;
		return finalContent;
	}
	
	public static String translateHTMLSimbols(String finalContent) {
		finalContent = finalContent.replaceAll("&amp;","&");
		finalContent = finalContent.replaceAll("&lt;", "<");
		finalContent = finalContent.replaceAll("&gt;", ">");
		finalContent = finalContent.replaceAll("&quot;", "\"");
		finalContent = finalContent.replaceAll("&apos;", "\'"); 
		
		return finalContent;
	}
	

}
