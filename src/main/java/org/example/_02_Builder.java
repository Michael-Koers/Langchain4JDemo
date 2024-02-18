package org.example;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;

public class _02_Builder {

    public static void main(String[] args) {

        OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_DEMO)
                .modelName(OpenAiChatModelName.GPT_4)
                .temperature(0.3d)
                .maxTokens(50)
                .logRequests(true)
                .logResponses(true)
                .maxRetries(3)
                .build();
    }
}
