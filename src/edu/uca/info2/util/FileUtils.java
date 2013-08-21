/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @author Toshiba
 */
public class FileUtils {
	public static String getContent(String filename)
			throws FileNotFoundException, IOException {
		BufferedReader bf = new BufferedReader(new java.io.FileReader(filename));
		String stracu = new String();
		String line = null;

		while ((line = bf.readLine()) != null) {
			stracu = stracu + line;
		}

		bf.close();
		return stracu;
	}

}
