package com.whatsTheMood.backend;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.io.IOException;
import twitter4j.TwitterException;

@RestController
public class BackendController {

    @GetMapping("/predict")
    Map<String, Integer> home(@RequestParam(value = "tag") String tag) throws IOException, TwitterException {
        if (!(tag instanceof String) || !tag.isEmpty()) {
            GoogleML googleClient = new GoogleML();
            int accumulatedScore = googleClient.getPrediction(tag);
            HashMap<String, Integer> dataMap = new HashMap<>();
            dataMap.put("data", accumulatedScore);
            return dataMap;

        } else {
            throw new IllegalArgumentException("Invalid or missing tag for query string");
        }

    }

}