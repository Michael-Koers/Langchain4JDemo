package com.michael.koers.nljug;

import dev.langchain4j.model.openai.OpenAiChatModel;
import com.michael.koers.examples.ApiKeys;

public class ConversationalChain {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        dev.langchain4j.chain.ConversationalChain chain = dev.langchain4j.chain.ConversationalChain.builder()
                .chatLanguageModel(model)
                .build();

        String answer = chain.execute("Hello World! My name is NLJug!");
        System.out.println(answer); // Nice to meet you, NLJug! How can I assist you today?

        answer = chain.execute("Remind me of my name please");
        System.out.println(answer); // Your name is NLJug.
    }
}
