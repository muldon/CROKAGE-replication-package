package com.ufu.crokage;

import java.net.InetAddress;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.ufu.crokage.to.Post;
import com.ufu.crokage.to.PostRestTransfer;

@Component
@SuppressWarnings("unused")
public class DyCrokageApp{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${stackOverflowSolutionsURL}")
	public String stackOverflowSolutionsURL;
	
	@Value("${numberOfComposedAnswers}")
	public Integer numberOfComposedAnswers;
	
	protected WebResource webResourceGetCandidadeBuckets;
	protected Gson gson;
	protected Client client;
	protected String processedQuery;
	protected String processedInput;
	protected String jsonInput;
	
	
	@PostConstruct
	public void init() throws Exception {
		System.out.println("Initializing app...");
		
		client = Client.create();
		
		gson = new GsonBuilder()
				  .excludeFieldsWithoutExposeAnnotation()
				  .create();
		
		jsonInput = "{\"numberOfComposedAnswers\":"+numberOfComposedAnswers+",\"reduceSentences\":false,\"queryText\":\"___queryText___\",\"ipAddress\":\"__ipAddress__\"}";
		jsonInput = jsonInput.replace("__ipAddress__", InetAddress.getLocalHost().toString());
		
		webResourceGetCandidadeBuckets = client.resource(stackOverflowSolutionsURL);
		
		String queries[] = {
				"how to add an element in an array in a specific position",
				"Convert between a file path and url",
				"convert string to number",
				"How to build rest service in java",
				"how do iterate and filter to a stream",
				"parse html with regex",
				"how to deserialize json string to object",			
				"how to remove characters from a string",
				"how to print to screen",
				
		};
		
		
		for(String query: queries) {
			processedQuery = CrokageUtils.processQuery(query,true);
			
			processedInput = jsonInput.replace("___queryText___", processedQuery);
			System.out.println("\n\nGetting solutions for: "+processedQuery);
			ClientResponse response = webResourceGetCandidadeBuckets.type("application/json").post(ClientResponse.class, processedInput);
			String output = response.getEntity(String.class);
			PostRestTransfer postRestTransfer = gson.fromJson(output, PostRestTransfer.class);
			
			List<Post> posts = postRestTransfer.getPosts();
			int pos=1;
			logger.info("Solutions for: "+query);
			for(Post answer: posts) {
				//via link
				logger.info("https://stackoverflow.com/questions/"+answer.getId());
				
				//or directly 
				//logger.info("Answer: "+pos+" -id:"+answer.getId()+ " -body: "+answer.getBody());
				
				pos++;
			}
			
			
		}
		
	}
	
	

}
