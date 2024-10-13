package com.michael.koers.nljug;

import com.michael.koers.examples.ApiKeys;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class Tools {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_PAID);

        Chatbot chatbot = AiServices.builder(Chatbot.class)
                .chatLanguageModel(model)
                .tools(new ChatBotTools())
                .build();

        String answer = chatbot.chat("How much tax do I have to pay on €50.75?");
        System.out.println(answer);
    }
}


    interface Chatbot {
        String chat(String message);
    }

    class ChatBotTools {

        private static final double TAXES = 0.21d;

        @Tool("Calculates the taxes for given amount")
        public double calculateTaxes(double amount) {
            System.out.println("LLM used calculateTaxes!");
            return amount * TAXES;
        }
    }
