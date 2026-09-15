package com.moit.meetup.service;

import java.util.List;

import com.moit.meetup.dto.MeetupCategoryStatisticsDto.MeetupCategoryStatisticsRawDto;
import com.moit.meetup.dto.MeetupCategoryStatisticsDto.MeetupCategoryStatisticsResponseDto;

public interface StatisticsSyncService {
	
	//카테고리별 모임 통계
	public List<MeetupCategoryStatisticsRawDto> getMeetupCategoryStatistics();
	
    // Django 통계 데이터 전송
	public void syncMeetupCategoryStatistics();
	
	public List<MeetupCategoryStatisticsResponseDto> getMeetupCategoryStatisticsResult();
}
