package com.whatsTheMood.backend;

import java.io.IOException;

// import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import twitter4j.TwitterException;

@RestController
@SpringBootApplication
public class BackendApplication {

	@RequestMapping("/")
	int home() throws IOException, TwitterException {
		String tag = "#SundayMorning";
		GoogleML googleClient = new GoogleML();
		return googleClient.getPrediction(tag);
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
