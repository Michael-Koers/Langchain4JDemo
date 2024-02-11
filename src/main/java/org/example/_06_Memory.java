package org.example;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiTokenizer;

public class _06_Memory {

    public static void main(String[] args) {

        // Alleen ChatGPT 3.5 Turbo of ChatGPT 4
        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_PAID);

        // Opzet chatmemory
        Tokenizer tokenizer = new OpenAiTokenizer(OpenAiChatModelName.GPT_3_5_TURBO.name());
        ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(1000, tokenizer);

        // Geef LLM achtergrond informatie
        SystemMessage systemMessage = SystemMessage.from(
                """
                        We work for a company renowned for its Java knowledge and experts.
                        Our company only works with Java and Java-based tools and frameworks, no other programming languages.
                        You are an advisor for the developers of this company, keep your answers short but do explain your choices.
                        """
        );

        chatMemory.add(systemMessage);

        UserMessage userMessage = UserMessage.from("""
                Which framework should I use for one of our back-end API? Spring Boot, ASP.NET Core Web API, Django or Rocket?
                """);

        chatMemory.add(userMessage);

        model.generate(chatMemory.messages());
    }
}
