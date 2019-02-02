package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@SpringBootApplication
@PropertySources({ @PropertySource(value = "classpath:/Demo/application-dev.properties", ignoreResourceNotFound = true),
		@PropertySource("classpath:application.properties") })
public class DemoApplication {

	private static Logger logger = LogManager.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RestController
	@Data
	static class MyController {

		@Autowired
		private Environment env;

		@Value("${my.prop.val}")
		private String myPropByVal;
		@Value("${spring.datasource.url}")
		private String urlByVal;

		@GetMapping("/")
		String home() {
			logger.error("my.prop.val:" + myPropByVal);
			logger.error("spring.datasource.url: " + urlByVal);
			String urlByEnv = env.getProperty("spng.datasource.url");
			logger.error("url da env: " + urlByEnv);
			String usrByEnv = env.getProperty("spring.datasource.username");
			logger.error("password: " + usrByEnv);
			return "hello!";
		}
	}

}