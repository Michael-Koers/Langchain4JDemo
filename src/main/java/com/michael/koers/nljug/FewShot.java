package com.michael.koers.nljug;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import com.michael.koers.examples.ApiKeys;

import java.util.ArrayList;
import java.util.List;

public class FewShot {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        // Set up few-shot, providing example User and AI message's for the
        List<ChatMessage> fewShotHistory = new ArrayList<>();
        fewShotHistory.add(UserMessage.from("Cat"));
        fewShotHistory.add(AiMessage.from("Kat"));
        fewShotHistory.add(UserMessage.from("Dog"));
        fewShotHistory.add(AiMessage.from("Hond"));
        fewShotHistory.add(UserMessage.from("Horse"));
        fewShotHistory.add(AiMessage.from("Paard"));

        // And now for an actual question
        fewShotHistory.add(UserMessage.from("Bird"));

        Response<AiMessage> answer = model.generate(fewShotHistory);
        System.out.println(answer.content().text()); // "Vogel"

    }
}
