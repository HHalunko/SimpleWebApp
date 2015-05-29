package com.global.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

//import java.time.LocalDateTime;

public class SearchEngine {

	public SearchEngine() {
		// TODO Auto-generated constructor stub
	}

	public static String searchMethog(File inputFile, int limit, String q,
			int length, boolean metaData) throws FileNotFoundException {
		System.out.println("STARTING SEARCH");
		if (limit == 0) {
			limit = 10000;
		}
		if (q == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String str;
			while (sb.length() <= limit && (str = in.readLine()) != null) {
				if (str.contains(q)) {
					if (str.length() <= length || length == 0) {
						sb.append(str).append("\r\n");
					}
				}
			}
			in.close();
		} catch (IOException e) {
		}
		// System.out.println(sb.toString());
		File resultFile = new File("D:/2/globalResult.txt");
		// LocalDateTime time = LocalDateTime.now();
		PrintWriter pw = new PrintWriter(
				new FileOutputStream(resultFile, false));
		if (sb.length() == 0) {
			pw.close();
			return null;
		} else {
			pw.print(sb.toString());
			// pw.print("\r\nFile name: " + resultFile.getName());
			// pw.print("\r\nFile size: " + resultFile.length() + " bytes");
			// pw.print("\r\nFile creation time: " + time.toString());
			pw.close();
			return sb.toString();
		}
	}
}
