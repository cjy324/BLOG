package com.sbs.example.mysqlTextBoard.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.analytics.data.v1alpha.AlphaAnalyticsDataClient;
import com.google.analytics.data.v1alpha.DateRange;
import com.google.analytics.data.v1alpha.Dimension;
import com.google.analytics.data.v1alpha.Entity;
import com.google.analytics.data.v1alpha.Metric;
import com.google.analytics.data.v1alpha.Row;
import com.google.analytics.data.v1alpha.RunReportRequest;
import com.google.analytics.data.v1alpha.RunReportResponse;
import com.sbs.example.mysqlTextBoard.apiDto.DisqusApiDataListThread;
import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.util.Util;

public class testApp {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class TestDataType1 {
		public int age;
		public String name;
		public int height;
	}

	public void run() {
		 //testApp3();

		//testJackson5();
		
	//	testGoogleCredentials();
		testUpdateGoogleAnalyticsApi();
	//	testUpdatePageHitsByGa4Api();
	}

	private void testUpdatePageHitsByGa4Api() {
		MysqlUtil.setDBInfo(Container.appConfig.getDBHost(), Container.appConfig.getDBId(), Container.appConfig.getDBPw(), Container.appConfig.getDBName());
		
		Container.googleAnalyticsApiService.updatePageHits();
	}

	private void testUpdateGoogleAnalyticsApi() {
		//GoogleAnalytics 버전4의 PropertyId 가져오기
		String ga4PropertyId = Container.appConfig.getGa4PropertyId();

	    try (AlphaAnalyticsDataClient analyticsData = AlphaAnalyticsDataClient.create()) {
	      RunReportRequest request = RunReportRequest.newBuilder()
	          .setEntity(Entity.newBuilder().setPropertyId(ga4PropertyId)) //검색(데이터 가져올) 대상
	          //해설 참고: http://www.goldenplanet.co.kr/blog/2017/01/24/google-analytics-dimension-metric/
	          //공식 정보: https://developers.google.com/analytics/devguides/reporting/data/v1/api-schema
	          //Dimension(측정 기준): 웹사이트 방문자들의 특성(속성)
	          .addDimensions(
	              Dimension.newBuilder().setName("pagePath")) //pagePath: The web pages visited, listed by URI.
	          //Metric(측정 항목): Dimension을 측정하는 “숫자”
	          .addMetrics(Metric.newBuilder().setName("activeUsers")) //activeUsers: The number of distinct users who visited your site or app.
	          .addDateRanges(
	              DateRange.newBuilder().setStartDate("2021-01-01").setEndDate("today")).build();

	      // Make the request
	      RunReportResponse response = analyticsData.runReport(request);

	      System.out.println("Report result:");
	      for (Row row : response.getRowsList()) {
	        System.out.printf("%s, %s%n", row.getDimensionValues(0).getValue(),
	            row.getMetricValues(0).getValue());
	      }
	    } catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void testGoogleCredentials() {
		//환경변수 경로를 가져오기
		String variablesPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
		System.out.println(variablesPath);
		
		
	}

	// HTTP REQUEST 후 응답 받아오는 함수 구현
	private void testApp() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";

		// disqus thread 경로?
		// https://disqus.com/api/3.0/forums/listThreads.json?api_key=mr5Mv3I4DJ893SADMVmxOu7iUzXrkL3GvNnWxJ4dBy5ZBHvd32lKlEw0qYI5x76F&forum=blog&thread:ident=1.html

		String rs = Util.callApi(url, "api_key=mr5Mv3I4DJ893SADMVmxOu7iUzXrkL3GvNnWxJ4dBy5ZBHvd32lKlEw0qYI5x76F",
				"forum=blog", "thread:ident=1.html");
		System.out.println(rs);
	}

	private void testApp2() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		
		for(int i = 1; i < 11; i++ ) {
		Map<String, Object> rs = Util.callApiResponseToMap(url,
				"api_key=mr5Mv3I4DJ893SADMVmxOu7iUzXrkL3GvNnWxJ4dBy5ZBHvd32lKlEw0qYI5x76F", "forum=devj-blog",
				"thread:ident=article_detail_"+ i +".html");

		List<Map<String, Object>> response = (List<Map<String, Object>>) rs.get("response");

		Map<String, Object> thread = response.get(0);

		System.out.println("likes " + (int) thread.get("likes"));
		System.out.println("comments " + (int) thread.get("posts"));
		}
	}

	private void testApp3() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		
		for(int i = 1; i < 11; i++ ) {
			DisqusApiDataListThread rs = (DisqusApiDataListThread) Util.callApiResponseTo(DisqusApiDataListThread.class,
					url, "api_key=mr5Mv3I4DJ893SADMVmxOu7iUzXrkL3GvNnWxJ4dBy5ZBHvd32lKlEw0qYI5x76F", "forum=devj-blog",
					"thread:ident=article_detail_" + i + ".html");
			System.out.println("likes " + rs.response.get(0).likes);
			System.out.println("comments " + rs.response.get(0).posts);
		}
		
	}

	// Jackson 라이브러리 추가 및 테스트
	private void testJackson1() {
		String jsonString = "{\"age\":22, \"name\":\"홍길동\"}";
		Map rs = null;

		ObjectMapper ob = new ObjectMapper();
		try {
			rs = ob.readValue(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get("name"));
	}

	private void testJackson2() {
		String jsonString = "50";
		int rs = 0;

		ObjectMapper ob = new ObjectMapper();
		try {
			rs = ob.readValue(jsonString, Integer.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);
	}

	private void testJackson3() {
		String jsonString = "[1,2,3,4]";

		List<Integer> rs = null;

		ObjectMapper ob = new ObjectMapper();
		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(3));
	}

	private void testJackson4() {
		String jsonString = "[{\"age\":22, \"name\":\"홍길동\"},{\"age\":40, \"name\":\"임꺽정\"},{\"age\":30, \"name\":\"아무개\"}]";
		List<Map<String, Object>> rs = null;

		ObjectMapper ob = new ObjectMapper();
		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(2).get("name"));
	}

	private void testJackson5() {
		String jsonString = "[{\"age\":22, \"name\":\"홍길동\", \"height\":178},{\"age\":23, \"name\":\"홍길순\", \"height\":168},{\"age\":24, \"name\":\"임꺽정\"}]";

		ObjectMapper ob = new ObjectMapper();
		List<TestDataType1> rs = null;

		try {
			rs = ob.readValue(jsonString, new TypeReference<List<TestDataType1>>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(0).height + rs.get(1).height);
		System.out.println(rs.get(1).height + rs.get(2).height);
	}

}
