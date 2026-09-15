package com.moit.meetup.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moit.meetup.dto.MeetupCategoryStatisticsDto.MeetupCategoryStatisticsRawDto;
import com.moit.meetup.dto.MeetupCategoryStatisticsDto.MeetupCategoryStatisticsResponseDto;
import com.moit.meetup.repository.MeetupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsSyncServiceImpl implements StatisticsSyncService{
	
	private final MeetupRepository meetupRepository;
	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;
	
	@Value("${statistics.django-url}")
	private String djangoUrl;
	
	@Override
	public List<MeetupCategoryStatisticsRawDto> getMeetupCategoryStatistics() {

		return meetupRepository.getMeetupCategoryStatistics();
	}
	
    // 카테고리별 통계 → Django 전송
    @Override
    public void syncMeetupCategoryStatistics() {

        try {

            // 1. Oracle에서 모임별 통계 조회
            List<MeetupCategoryStatisticsRawDto> rawList =
                    getMeetupCategoryStatistics();


            // 2. 카테고리별 집계
            Map<String, CategoryStatistics> statisticsMap =
                    new LinkedHashMap<>();

            for (MeetupCategoryStatisticsRawDto raw : rawList) {

                String categoryName = raw.getCategoryName();

                CategoryStatistics statistics =
                        statisticsMap.computeIfAbsent(
                                categoryName,
                                key -> new CategoryStatistics()
                        );


                // 모임 수
                statistics.meetupCount++;


                // 승인된 신청자 수
                long applicantCount =
                        raw.getApplicantCount() != null
                                ? raw.getApplicantCount()
                                : 0L;

                statistics.applicantCount += applicantCount;


                // 평균 신청률
                Integer maxParticipants =
                        raw.getMaxParticipants();

                if (maxParticipants != null
                        && maxParticipants > 0) {

                    double applicationRate =
                            ((double) applicantCount
                                    / maxParticipants) * 100;

                    statistics.totalApplicationRate
                            += applicationRate;

                    statistics.applicationRateCount++;
                }
            }


            // 3. 최종 DTO 생성
            List<MeetupCategoryStatisticsResponseDto> result =
                    new ArrayList<>();

            for (Map.Entry<String, CategoryStatistics> entry
                    : statisticsMap.entrySet()) {

                String categoryName = entry.getKey();

                CategoryStatistics statistics =
                        entry.getValue();

                double averageApplicationRate = 0.0;

                if (statistics.applicationRateCount > 0) {

                    averageApplicationRate =
                            statistics.totalApplicationRate
                                    / statistics.applicationRateCount;
                }

                averageApplicationRate =
                        Math.round(
                                averageApplicationRate * 100.0
                        ) / 100.0;


                result.add(
                        new MeetupCategoryStatisticsResponseDto(
                                categoryName,
                                statistics.meetupCount,
                                (int) statistics.applicantCount,
                                averageApplicationRate
                        )
                );
            }


            // 4. JSON 생성
            Map<String, Object> requestData =
                    new LinkedHashMap<>();

            requestData.put("statistics", result);

            String jsonData =
                    objectMapper.writeValueAsString(requestData);


            // 5. Django 전송
            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            HttpEntity<String> request =
                    new HttpEntity<>(
                            jsonData,
                            headers
                    );


            String response =
                    restTemplate.postForObject(
                            djangoUrl,
                            request,
                            String.class
                    );


            System.out.println("Django 통계 전송 성공");
            System.out.println("Django 응답 : " + response);


        } catch (Exception e) {

            System.out.println("Django 통계 전송 실패");
            e.printStackTrace();
        }
    }


    // 카테고리별 집계용 내부 클래스
    private static class CategoryStatistics {

        private int meetupCount = 0;

        private long applicantCount = 0;

        private double totalApplicationRate = 0.0;

        private int applicationRateCount = 0;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<MeetupCategoryStatisticsResponseDto> getMeetupCategoryStatisticsResult() {

        Map<String, Object> response =
                restTemplate.getForObject(
                        djangoUrl,
                        Map.class
                );

        if (response == null || response.get("statistics") == null) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> statistics =
                (List<Map<String, Object>>) response.get("statistics");

        List<MeetupCategoryStatisticsResponseDto> result =
                new ArrayList<>();

        for (Map<String, Object> statistic : statistics) {

            String categoryName =
                    (String) statistic.get("categoryName");

            Integer meetupCount =
                    ((Number) statistic.get("meetupCount")).intValue();

            Integer applicantCount =
                    ((Number) statistic.get("applicantCount")).intValue();

            Double averageApplicationRate =
                    ((Number) statistic.get("averageApplicationRate"))
                            .doubleValue();

            result.add(
                    new MeetupCategoryStatisticsResponseDto(
                            categoryName,
                            meetupCount,
                            applicantCount,
                            averageApplicationRate
                    )
            );
        }

        return result;
    }
}
