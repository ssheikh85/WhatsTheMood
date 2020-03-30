import React, { useState } from "react";
import {
  StyleSheet,
  Button,
  Text,
  TextInput,
  SafeAreaView,
  View
} from "react-native";
import { apiUrl } from "./config";

const App = () => {
  const [score, setScore] = useState(0);
  const [tag, setTag] = useState("");

  const getPrediction = async () => {
    try {
      console.log(`${apiUrl}"${tag}"`);
      const response = await fetch(`${apiUrl}"${tag}`, {
        method: "GET",
        mode: "cors"
      });

      const retrievedScore = await response;
      console.log(retrievedScore);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <View style={styles.container}>
      <SafeAreaView>
        <Text style={styles.header}>How do Twitter users feel?</Text>
        <Text style={styles.prompt}>
          Enter a hashtag or something trending below, to find out
        </Text>
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
    justifyContent: "space-between"
  },
  header: {
    fontSize: 30
  },
  prompt: {
    fontSize: 14
  },
  textIn: {
    height: 30,
    borderColor: "gray",
    borderWidth: 1
  }
});

export default App;
