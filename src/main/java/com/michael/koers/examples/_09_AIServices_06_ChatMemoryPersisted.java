package com.michael.koers.examples;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class _09_AIServices_06_ChatMemoryPersisted {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        FileStore store = new FileStore();

        ChatMemoryProvider provider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(store)
                .build();

        ChatAssistant assistant = AiServices.builder(ChatAssistant.class)
                .chatLanguageModel(model)
                .chatMemoryProvider(provider)
                .build();

        System.out.println(assistant.chat(1, "Hello my name is Michael"));
        System.out.println(assistant.chat(2, "Hello my name is Karl"));

//        System.out.println(assistant.chat(1, "What is my name?"));
//        System.out.println(assistant.chat(2, "What is my name?"));
    }
}

/*
 Simple local file storage (it's breaks after 1 run, but it's just to prove a point :)  )
 */
class FileStore implements ChatMemoryStore {

    public static final String PATH = "src/main/resources/messages_%s.txt";

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        String file = PATH.formatted(memoryId);

        try {
            if (!Files.exists(Paths.get(file))) {
                Files.createFile(Paths.get(file));
            }

            for (String s : Files.readAllLines(Paths.get(file))) {
                chatMessages.add(UserMessage.from(s));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return chatMessages;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String file = PATH.formatted(memoryId);

        for (ChatMessage message : messages) {
            try {
                Files.writeString(Paths.get(file), message.text() + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        System.out.println("Not implemented");
    }
}