package org.example;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

public class _10_Tools {

    public static void main(String[] args) {

        // Zet Logger of Debug

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(OpenAiChatModel.builder()
                        // Tools only work with paid openai license, this is not documented anywhere...
                        .apiKey(ApiKeys.OPENAI_PAID)
                        .logRequests(true)
                        .temperature(0d)
                        .build())
                .tools(new CustomerTools())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .build();

        String answer = assistant.chat("""
                Create a customer for Kevin and Sarah and save it to our database.
                List the customers with their new ID's.
                """);

        System.out.println(answer);
    }
}

interface Assistant {
    String chat(String message);
}


class CustomerTools {

    private static final String database = "src/main/resources/customers.txt";
    private static final AtomicInteger customerId = new AtomicInteger(1000);

    @Tool("Creates and returns a new customer")
    Customer createCustomer(String name) {
        return new Customer(customerId.getAndIncrement(), name);
    }

    @Tool("Saves given customer to the database, returns HTTP status code indicating success or failure")
    int saveCustomer(int customerId, String customerName) {
        try {
//            if (name.equalsIgnoreCase("Alex")) throw new IOException("We don't like Alex");

            Files.writeString(Paths.get(database),
                    new Customer(customerId, customerName) + "\n",
                    StandardOpenOption.APPEND);

            return HttpURLConnection.HTTP_CREATED;
        } catch (IOException e) {
            return HttpURLConnection.HTTP_BAD_REQUEST;
        }
    }
}

record Customer(int id, String name) {
};