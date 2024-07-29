package com.michael.koers.examples;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.Scanner;

public class _08_ConversationalChain {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        ConversationalChain chain = ConversationalChain.builder()
                .chatLanguageModel(model)
                .build();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {

            String in = scanner.nextLine();

            String answer = chain.execute(in);
            System.out.println(answer);
        }
    }
}
