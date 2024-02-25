package org.example;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.huggingface.HuggingFaceModelName;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;

public class _02_Builder {

    public static void main(String[] args) {

        ChatLanguageModel openAI = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_DEMO)
                .modelName(OpenAiChatModelName.GPT_4)
                .temperature(0.3d)
                .maxTokens(50)
                .logRequests(true)
                .logResponses(true)
                .maxRetries(3)
                .build();

        ChatLanguageModel huggingFace = HuggingFaceChatModel.builder()
                .accessToken(ApiKeys.OPENAI_DEMO)
                .modelId(HuggingFaceModelName.TII_UAE_FALCON_7B_INSTRUCT)
                .temperature(0.3d)
                .build();
    }
}
