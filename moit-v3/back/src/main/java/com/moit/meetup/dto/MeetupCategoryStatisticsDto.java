package com.moit.meetup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MeetupCategoryStatisticsDto {
	
	//카테고리별 집계 데이터
	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MeetupCategoryStatisticsResponseDto{
		private String categoryName;
		private Integer meetupCount;
		private Integer applicantCount;
		private Double averageApplicationRate;
	}
	

    // DB에서 모임별 통계를 가져오기 위한 Raw DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MeetupCategoryStatisticsRawDto {

        private String categoryName;
        private Integer maxParticipants;
        private Long applicantCount;
    }
    
    //최종 통계 데이터
}
