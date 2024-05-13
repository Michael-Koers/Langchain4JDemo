package org.example;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.model.output.Response;

import java.util.ArrayList;
import java.util.List;

import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

public class _06_Memory {

    public static void main(String[] args) {

        // Only available with ChatGPT 3.5 Turbo or ChatGPT 4
        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        List<ChatMessage> chatMemory = new ArrayList<>();

        // Provide LLM with background information
        SystemMessage systemMessage = SystemMessage.from(
        """
            We work for a company renowned for its Java knowledge and experts.
            Our company only works with Java and Java-based tools and frameworks, no other programming languages.
            You are an advisor for the developers of this company. Answer in max 2 sentences.
            """
        );

        chatMemory.add(systemMessage);

        UserMessage userMessage = UserMessage.from("""
            Which framework should I use for one of our back-end API's? ASP.NET Core Web API, Django, Spring Boot or Rocket?
        """);

        chatMemory.add(userMessage);

        Response<AiMessage> answer = model.generate(chatMemory);
        System.out.println(answer.content().text()); // I recommend using Spring Boot for developing your back-end API,
        // as it is a widely used and well-supported Java-based framework known for its ease of use and productivity.

    }
}
