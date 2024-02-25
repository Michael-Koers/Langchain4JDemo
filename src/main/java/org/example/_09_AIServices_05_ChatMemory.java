package org.example;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

public class _09_AIServices_05_ChatMemory {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        ChatAssistant assistant = AiServices.builder(ChatAssistant.class)
                .chatLanguageModel(model)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();


        System.out.println(assistant.chat(1, "Hello, my name is Michael"));

        System.out.println(assistant.chat(2, "Hello, my name is Karl"));

        System.out.println(assistant.chat(2, "What is my name?"));

        System.out.println(assistant.chat(1, "What is my name?"));

    }
}


interface ChatAssistant {

    String chat(@MemoryId int memoryId, @UserMessage String message);
}