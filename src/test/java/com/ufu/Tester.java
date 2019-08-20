package com.ufu;

import java.io.File;
import java.nio.file.Path;

public class Tester {
	
	public Tester() {
		File file = new File(getClass().getClassLoader().getResource("stanford_stop_words.txt").getFile());
		Path yourPath = file.toPath();
		
		
	}
	
	
	public static void main(String[] args) {
		Tester t = new Tester();
	}
}
