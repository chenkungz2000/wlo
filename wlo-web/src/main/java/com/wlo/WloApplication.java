package com.wlo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
//多數據源
//@MyBadisLoader({"saas = com.llc.admin.web.dao.saas = classpath:mapper/*xml" ,	"saas2 = com.llc.admin.web.dao.saas2 = classpath:mapper/*.xml,classpath:mapper/user/*.xml"})
//@ComponentScan({"com.wlo"})
@SpringBootApplication
public class WloApplication {
	final static Logger logger = LoggerFactory.getLogger(WloApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(WloApplication.class, args);
		logger.info("==begin==");
	}
}
