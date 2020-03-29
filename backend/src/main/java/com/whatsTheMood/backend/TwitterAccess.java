package com.whatsTheMood.backend;

import org.springframework.stereotype.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;
import twitter4j.*;
import twitter4j.conf.*;

@Component
public class TwitterAccess {
    private String consumerAPIKey = "";
    private String consumerAPISecret = "";
    private String accessToken = "";
    private String accessSecret = "";

    private final int tweetCount = 100;
    private final String lang = "en";

    List<String> getTweets(String searchItem) throws TwitterException {
        try {
            Dotenv dotenv = Dotenv.load();
            this.consumerAPIKey = dotenv.get("CONSUMER_API_KEY");
            this.consumerAPISecret = dotenv.get("CONSUMER_API_SECRET");
            this.accessToken = dotenv.get("ACCESS_TOKEN");
            this.accessSecret = dotenv.get("ACCESS_SECRET");
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true).setOAuthConsumerKey(this.consumerAPIKey)
                    .setOAuthConsumerSecret(this.consumerAPISecret).setOAuthAccessToken(this.accessToken)
                    .setOAuthAccessTokenSecret(this.accessSecret);

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            Query query = new Query(searchItem).count(this.tweetCount).lang(this.lang);
            QueryResult result = twitter.search(query);

            List<String> resultList = new ArrayList<String>();
            for (Status status : result.getTweets()) {
                resultList.add(status.getText());
            }

            return resultList;

        } catch (TwitterException e) {
            throw e;
        }

    }
}
