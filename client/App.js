import React, { useState } from "react";
import {
  StyleSheet,
  Button,
  Text,
  TextInput,
  SafeAreaView,
  View
} from "react-native";
import Emoji from "react-native-emoji";
import { apiUrl } from "./config";

const App = () => {
  const [scoreState, setScoreState] = useState(0);
  const [tag, setTag] = useState("");

  const ReactionRenderer = score => {
    const { scoreProp } = score;
    const scoreToCompare = parseInt(scoreProp);

    if (scoreToCompare >= 150) {
      return <Emoji name=":heart_eyes:" style={styles.emoji} />;
    } else if (scoreToCompare >= 100 && scoreToCompare < 150) {
      return <Emoji name=":grinning:" style={styles.emoji} />;
    } else if (scoreToCompare >= 50 && scoreToCompare < 100) {
      return <Emoji name=":neutral_face:" style={styles.emoji} />;
    } else if (scoreToCompare < 50) {
      return <Emoji name=":unamused:" style={styles.emoji} />;
    }
  };

  const getPrediction = async event => {
    try {
      event.preventDefault();
      const response = await fetch(`${apiUrl}"${tag}`, {
        method: "GET",
        mode: "cors"
      });

      const returnedResponse = await response.json();
      const { data } = returnedResponse;
      setScoreState(data);
      setTag("");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <View style={styles.container}>
      <SafeAreaView>
        <Text style={styles.header}>How do Twitter users feel?</Text>
        {scoreState === 0 ? (
          <Emoji name=":wink:" style={styles.emoji} />
        ) : (
          <ReactionRenderer scoreProp={scoreState} />
        )}
        <Text style={styles.prompt}>Enter something below to find out!</Text>
        <TextInput
          style={styles.textIn}
          onChangeText={text => setTag(text)}
          value={tag}
        />
        <Button
          onPress={getPrediction}
          title="Predict"
          color="#4B88A2"
          accessibilityLabel="Get's prediction of how people feel"
        />
      </SafeAreaView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    backgroundColor: "#CDE7B0",
    alignItems: "center",
    justifyContent: "center"
  },
  header: {
    fontSize: 30,
    fontWeight: "bold",
    color: "#4B88A2"
  },
  emoji: {
    fontSize: 100,
    margin: 25,
    textAlign: "center"
  },
  prompt: {
    textAlign: "center",
    fontSize: 14
  },
  textIn: {
    height: 30,
    borderColor: "gray",
    borderWidth: 1,
    margin: 15
  }
});

export default App;
