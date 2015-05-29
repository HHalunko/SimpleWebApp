package com.global;

import java.io.File;
import java.io.FileNotFoundException;

import com.global.model.SearchEngine;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		File x = new File("D:/test.txt");
		try {
			System.out.println(SearchEngine.searchMethog(x, 10000, "java", 0, true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
