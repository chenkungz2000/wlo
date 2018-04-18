package com.wlo.commons.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*证明这个类是一个配置文件*/
@Configuration
/*定时器开关*/
@EnableScheduling
public class ScheduledConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

/*    @Scheduled(cron = "0 0/1 * * * ?") //每分钟执行一次
    public void statusCheck() {
        logger.info("每分钟执行一次。开始……");
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //statusTask.healthCheck();
        logger.info("每分钟执行一次。结束。");
    }*/

    @Scheduled(fixedRate = 300000)
    public void testTasks() {
        logger.info("每300000秒执行一次。开始……");
        
        
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //statusTask.healthCheck();
        logger.info("每300000秒执行一次。结束。");
    }
}