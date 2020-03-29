package com.whatsTheMood.backend;

import java.util.*;
import twitter4j.*;
import com.google.cloud.automl.v1.AnnotationPayload;
import com.google.cloud.automl.v1.ExamplePayload;
import com.google.cloud.automl.v1.ModelName;
import com.google.cloud.automl.v1.PredictRequest;
import com.google.cloud.automl.v1.PredictResponse;
import com.google.cloud.automl.v1.PredictionServiceClient;
import com.google.cloud.automl.v1.TextSnippet;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

class GoogleML {

    private String projectId = "";
    private String modelId = "";

    int getPrediction(String tag) throws IOException, TwitterException {
        Dotenv dotenv = Dotenv.load();
        TwitterAccess twitterClient = new TwitterAccess();
        this.projectId = dotenv.get("GOOGLE_PRJ_ID");
        this.modelId = dotenv.get("AUTOML_MODEL_ID");

        List<String> tweets = twitterClient.getTweets(tag);

        int totalScore = 0;
        for (String tweet : tweets) {
            totalScore += predict(projectId, modelId, tweet);
        }
        return totalScore;

    }

    int predict(String projectId, String modelId, String content) throws IOException {
        // Initialize client that will be used to send requests. This client only needs
        // to be created
        // once, and can be reused for multiple requests. After completing all of your
        // requests, call
        // the "close" method on the client to safely clean up any remaining background
        // resources.
        try (PredictionServiceClient client = PredictionServiceClient.create()) {
            // Get the full path of the model.
            ModelName name = ModelName.of(projectId, "us-central1", modelId);
            TextSnippet textSnippet = TextSnippet.newBuilder().setContent(content).setMimeType("text/plain") // Types:
                                                                                                             // text/plain,
                                                                                                             // text/html
                    .build();
            ExamplePayload payload = ExamplePayload.newBuilder().setTextSnippet(textSnippet).build();
            PredictRequest predictRequest = PredictRequest.newBuilder().setName(name.toString()).setPayload(payload)
                    .build();

            PredictResponse response = client.predict(predictRequest);

            int sentimentScore = 0;
            for (AnnotationPayload annotationPayload : response.getPayloadList()) {
                sentimentScore = annotationPayload.getTextSentiment().getSentiment();
            }
            return sentimentScore;

        }
    }
}