package com.moit.meetup.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moit.meetup.dto.MeetupCategoryStatisticsDto.MeetupCategoryStatisticsRawDto;
import com.moit.meetup.dto.MeetupCategoryStatisticsDto.MeetupCategoryStatisticsResponseDto;
import com.moit.meetup.service.StatisticsSyncService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "statistics Api", description = "분석 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsSyncService statisticsSyncService;

    // DB 통계 조회 테스트
    @GetMapping("/meetups")
    public List<MeetupCategoryStatisticsRawDto> getMeetupStatistics() {

        return statisticsSyncService.getMeetupCategoryStatistics();
    }

    // Django로 통계 전송
    @PostMapping("/meetups/sync")
    public ResponseEntity<String> syncMeetupStatistics() {
    	
    	try {
    		statisticsSyncService.syncMeetupCategoryStatistics();
    		return ResponseEntity.ok("Spring Boot -> Django 통계 동기화 성공!");
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("동기화 실패: " + e.getMessage());
    	}
    }
    
    @GetMapping("/meetups/result")
    public List<MeetupCategoryStatisticsResponseDto> getMeetupStatisticsResult() {

        return statisticsSyncService
                .getMeetupCategoryStatisticsResult();
    }
    
}