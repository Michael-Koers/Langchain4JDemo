package com.michael.koers.examples;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;

import java.util.ArrayList;
import java.util.List;

public class _07_FewShot {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        // Provide LLM with some examples
        List<ChatMessage> fewShotHistory = new ArrayList<>();

        // Positive
        fewShotHistory.add(UserMessage.from("""
                I just would like to tell you guys, your smarthome app is working flawlessly. Thanks for the amazing work!
                """));

        fewShotHistory.add(AiMessage.from("""
                Thanks for the kind words, here at Company X we strive to deliver the best service to our customers!
                """));

        // Negative
        fewShotHistory.add(UserMessage.from("""
                Your smarthome app is thrash, as a color blind person, the UI is extremely difficult to navigate.
                """));

        fewShotHistory.add(AiMessage.from("""
                I'm sorry to hear, we've created a customer support ticket. We will look into the issue as soon as possible.
                """));

        // More positive
        fewShotHistory.add(UserMessage.from("""
                Great app, 10/10!
                """));

        fewShotHistory.add(AiMessage.from("""
                Thanks for the kind words, here at Company X we strive to deliver the best service to our customers!
                """));

        // Neutral
        fewShotHistory.add(UserMessage.from("""
                Your smarthome app is refusing to connect to my Home Assistent running on a local Raspberry Pi. Any ideas?
                """));

        fewShotHistory.add(AiMessage.from("""
                I'm sorry to hear, we've created a customer support ticket. We will look into the issue as soon as possible.
                """));

        // And now a real question/remark
        fewShotHistory.add(UserMessage.from("""
                Good, love it!
                """));

//        fewShotHistory.add(UserMessage.from("""
//                 Bad, any improvements?
//                 """));

        Response<AiMessage> answer = model.generate(fewShotHistory);
        System.out.println(answer.content().text());
    }
}
