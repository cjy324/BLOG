package com.sbs.example.mysqlTextBoard.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Util {

	//신규 폴더 생성
	public static void mkdir(String path) {
		
		File dir = new File(path);
		
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
	}
	
	//파일 생성
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

	//기존 폴더 삭제
	public static boolean rmdir(String path) {
		return rmdir(new File(path));
	}

	public static boolean rmdir(File dirToBeDeleted) {
		File[] allContents = dirToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				rmdir(file);
			}
		}

		return dirToBeDeleted.delete();
	}

	// 파일내용 읽어오기
	public static String getFileContents(String filePath) {
		String rs = null;
		try {
			// 바이트 단위로 파일읽기
			FileInputStream fileStream = null; // 파일 스트림

			fileStream = new FileInputStream(filePath);// 파일 스트림 생성
			// 버퍼 선언
			byte[] readBuffer = new byte[fileStream.available()];
			while (fileStream.read(readBuffer) != -1) {
			}

			rs = new String(readBuffer);

			fileStream.close(); // 스트림 닫기
		} catch (Exception e) {
			e.getStackTrace();
		}

		return rs;
	}

	// 파일 복붙
	public static boolean copy(String sourcePath, String destPath) {
		Path source = Paths.get(sourcePath);
        Path target = Paths.get(destPath);

        if (!Files.exists(target.getParent())) {
            try {
				Files.createDirectories(target.getParent());
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
        }

        try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			return true;
		}
        
        return true;
	}

}
