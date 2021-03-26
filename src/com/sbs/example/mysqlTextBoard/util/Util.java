package com.sbs.example.mysqlTextBoard.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.example.mysqlTextBoard.apiDto.DisqusApiDataListThread;
import com.sbs.example.mysqlTextBoard.dto.Article;

public class Util {

	// 신규 폴더 생성 유틸
	public static void mkdir(String path) {

		File dir = new File(path);

		if (dir.exists() == false) {
			dir.mkdirs();
		}

	}

	// 파일 생성 유틸
	public static void writeFile(String path, String body) { // body = sb.toString
		File file = new File(path);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(body);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 기존 폴더 삭제 유틸
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

	// 파일내용 읽어오는 유틸
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

	// 파일 복붙 유틸
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

	// 폴더 복붙 유틸
	public static void copyDir(String sourceDirectoryLocation, String destinationDirectoryLocation) {
		rmdir(destinationDirectoryLocation);

		try {
			Files.walk(Paths.get(sourceDirectoryLocation)).forEach(source -> {
				Path destination = Paths.get(destinationDirectoryLocation,
						source.toString().substring(sourceDirectoryLocation.length()));
				try {
					Files.copy(source, destination);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getNowDateStr() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	}

	// url을 자바 내에서 확인하고 싶을때 사용하는 유틸
	public static String callApi(String urlStr, String... args) {
		// URL 구성 시작
		StringBuilder queryString = new StringBuilder();

		for (String param : args) {
			if (queryString.length() == 0) {
				queryString.append("?");
			} else {
				queryString.append("&");
			}

			queryString.append(param);
		}

		urlStr += queryString.toString();
		// URL 구성 끝

		// 연결생성 시작
		HttpURLConnection con = null;

		try {
			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000); // 최대통신시간 제한
			con.setReadTimeout(5000); // 최대데이터읽기시간 제한
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (ProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		// 연결생성 끝

		// 연결을 통해서 데이터 가져오기 시작
		StringBuffer content = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 연결을 통해서 데이터 가져오기 끝

		return content.toString();
	}

	// API 응답을 jsonString과 Map형태로 변환하는 유틸
	public static Map<String, Object> callApiResponseToMap(String url, String... args) {

		String jsonString = callApi(url, args);

		ObjectMapper mapper = new ObjectMapper();

		try {
			return (Map<String, Object>) mapper.readValue(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	// API 응답을 jsonString과 클래스로 변환하는 유틸
	public static Object callApiResponseTo(Class cls, String url, String... args) {
		String jsonString = callApi(url, args);

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(jsonString, cls);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	// JSON 형태로 변환하는 유틸
	public static String getJsonText(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String rs = "";
		try {
			rs = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
