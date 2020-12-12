package com.sbs.example.mysqlTextBoard.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Util {

	public static void mkdir(String path) {
		
		File dir = new File(path);
		
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
	}

	public static void writeFile(String path, String body) {  //body = sb.toString
		File file = new File(path);

		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(body);
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}

}
