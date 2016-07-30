package com.qait.automation.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reads the PageObjectRepository text files. Uses buffer reader.
 * @author prashantshukla
 *
 */
public class ObjectFileReader {

	static String filepath = "src/test/resources/PageObjectRepository/";

	public static String[] getELementFromFile(String pageName,
			String elementName) {
		BufferedReader br = null;
		String returnElement = "";
		try {
			br = new BufferedReader(
					new FileReader(filepath + pageName + ".txt"));
			String line = br.readLine();

			while (line != null) {
				if (line.split(":",3)[0].trim().equalsIgnoreCase(elementName.trim())) {
					returnElement = line;
					break;
				}
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnElement.split(":",3);
		// TODO: Handle Arrayoutofbounds and Filenotfound exceptions gracefully.
	}

	public static String getPageTitleFromFile(String pageName) {
		BufferedReader br = null;
		String returnElement = "";
		try {
			br = new BufferedReader(
					new FileReader(filepath + pageName + ".txt"));
			String line = br.readLine();

			while (line != null) {
				if (line.split(":",3)[0].trim().equalsIgnoreCase("pagetitle")
						|| line.split(":",3)[0].trim().equalsIgnoreCase("title")) {
					returnElement = line;
					break;
				}
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnElement.split(":",3)[1].trim();
	}

}
