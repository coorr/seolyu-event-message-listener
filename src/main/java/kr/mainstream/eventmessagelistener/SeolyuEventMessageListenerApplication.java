package kr.mainstream.eventmessagelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SeolyuEventMessageListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeolyuEventMessageListenerApplication.class, args);
	}

}
