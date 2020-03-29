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
	void home() throws IOException, TwitterException {
		GoogleML googleClient = new GoogleML();
		googleClient.getPrediction();
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
