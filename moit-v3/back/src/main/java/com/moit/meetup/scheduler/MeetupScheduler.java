package com.moit.meetup.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.moit.meetup.service.MeetupService;
import com.moit.meetup.service.StatisticsSyncService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetupScheduler {
	
	private final MeetupService meetupService;
	private final StatisticsSyncService statisticsSyncService;
	
	//@Scheduled(cron = "0 * * * * *")   // 테스트용 1분마다
    //@Scheduled(cron = "0 0 18 * * *", zone = "Asia/Seoul")  // 오후 6시 문자알림발송
    public void sendWeatherNotification() {
    	meetupService.sendTomorrowWeatherNotification();
    }
    
    // 매일 새벽 1시 실행
    //@Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
    @Scheduled(cron = "0 * * * * *")   // 테스트용 1분마다
    public void syncMeetupStatistics() {

        statisticsSyncService.syncMeetupCategoryStatistics();
    }

}
