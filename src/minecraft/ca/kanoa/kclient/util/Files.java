package ca.kanoa.kclient.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Files {

	public static String readTextFile(File file) {
		BufferedReader br;
		String contents = "";
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return contents;
		}
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append('\n');
	            line = br.readLine();
	        }
	        contents = sb.toString();
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return contents;
	}
	
	public static void saveTextFile(String txt, File file) {
		PrintWriter out;
		try {
			out = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		try {
			for (String line : txt.split("\n")) {
				out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
}
