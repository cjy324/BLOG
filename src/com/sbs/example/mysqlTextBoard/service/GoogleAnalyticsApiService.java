package com.sbs.example.mysqlTextBoard.service;

import java.io.IOException;

import com.google.analytics.data.v1alpha.AlphaAnalyticsDataClient;
import com.google.analytics.data.v1alpha.DateRange;
import com.google.analytics.data.v1alpha.Dimension;
import com.google.analytics.data.v1alpha.Entity;
import com.google.analytics.data.v1alpha.Metric;
import com.google.analytics.data.v1alpha.Row;
import com.google.analytics.data.v1alpha.RunReportRequest;
import com.google.analytics.data.v1alpha.RunReportResponse;
import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.Ga4DataDao;

public class GoogleAnalyticsApiService {

	Ga4DataDao ga4DataDao;
	ArticleService articleService;

	public GoogleAnalyticsApiService() {
		ga4DataDao = Container.ga4DataDao;
		articleService = Container.articleService;
	}

	// 게시물별 조회수
	public boolean updateGa4DataPageHits() {
		String ga4PropertyId = Container.appConfig.getGa4PropertyId();

		try (AlphaAnalyticsDataClient analyticsData = AlphaAnalyticsDataClient.create()) {
			RunReportRequest request = RunReportRequest.newBuilder()
					.setEntity(Entity.newBuilder().setPropertyId(ga4PropertyId)) // 검색(데이터 가져올) 대상
					// 해설 참고:
					// http://www.goldenplanet.co.kr/blog/2017/01/24/google-analytics-dimension-metric/
					// 공식 정보:
					// https://developers.google.com/analytics/devguides/reporting/data/v1/api-schema
					// Dimension(측정 기준): 웹사이트 방문자들의 특성(속성)
					.addDimensions(Dimension.newBuilder().setName("pagePath")) // pagePath: The web pages visited,
																				// listed by URI.
					// Metric(측정 항목): Dimension을 측정하는 “숫자”
					.addMetrics(Metric.newBuilder().setName("screenPageViews")) // activeUsers: The number of distinct
																				// users who visited your site or app.
					.addDateRanges(DateRange.newBuilder().setStartDate("2021-01-01").setEndDate("today")).setLimit(-1)
					.build();

			// Make the request
			RunReportResponse response = analyticsData.runReport(request);

			System.out.println("Report result:");
			for (Row row : response.getRowsList()) {
				String pagePath = row.getDimensionValues(0).getValue();
				int hit = Integer.parseInt(row.getMetricValues(0).getValue());
				System.out.printf("pagePath: %s, hit: %d%n", pagePath, hit);

				// pagePath와 hit 정보를 받아 업데이트
				update(pagePath, hit);

			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	private void update(String pagePath, int hit) {
		// 기존의 페이지경로에 있는 정보 삭제(초기화)
		ga4DataDao.deletePageData(pagePath);
		// 페이지경로에 정보 업데이트(새 정보로 저장)
		ga4DataDao.savePageData(pagePath, hit);

	}

	public void updatePageHits() {
		// ga4DataPagePath 테이블의 hit 정보 업데이트
		updateGa4DataPageHits();
		// article 테이블의 hitCount 정보 업데이트
		articleService.updatePageHits();
	}

}
