package com.whatsTheMood.backend;

import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import twitter4j.TwitterException;

@RestController
public class BackendController {

    @GetMapping("/predict")
    int home(@RequestParam(value = "tag") String tag) throws IOException, TwitterException {
        if (!(tag instanceof String) || !tag.isEmpty()) {
            GoogleML googleClient = new GoogleML();
            return googleClient.getPrediction(tag);
        } else {
            throw new IllegalArgumentException("Invalid or missing tag for query string");
        }

    }

}