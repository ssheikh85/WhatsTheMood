package com.whatsTheMood.backend;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import twitter4j.*;

@RestController
@SpringBootApplication
public class BackendApplication {

	@RequestMapping("/")
	List<String> home() throws TwitterException {
		try {
			TwitterAccess twitterClient = new TwitterAccess();
			return twitterClient.getTweets("#SundayMorning");
		} catch (TwitterException e) {
			throw e;
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
