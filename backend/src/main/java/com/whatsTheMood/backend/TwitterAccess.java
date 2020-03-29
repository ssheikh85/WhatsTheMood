package com.whatsTheMood.backend;

import java.util.*;
import twitter4j.*;
import twitter4j.conf.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

@Component
public class TwitterAccess {

    @Value("${CONSUMER_API_KEY}")
    private String consumerAPIKey;

    @Value("${CONSUMER_API_SECRET}")
    private String consumerAPISecret;

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    @Value("${ACCESS_SECRET}")
    private String accessSecret;

    private final int tweetCount = 100;

    List<String> getTweets(String searchItem) throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerAPIKey).setOAuthConsumerSecret(consumerAPISecret)
                .setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        Query query = new Query(searchItem).count(this.tweetCount).lang("en");
        QueryResult result = twitter.search(query);

        List<String> resultList = new ArrayList<String>();
        for (Status status : result.getTweets()) {
            resultList.add(status.getText());
        }

        return resultList;

    }
}
