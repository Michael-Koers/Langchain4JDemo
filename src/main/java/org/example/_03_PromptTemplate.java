package org.example;

import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.Map;

public class _03_PromptTemplate {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        PromptTemplate promptTemplate =
                PromptTemplate.from("Give me an overview of {{amount}} pokemon of type {{type}}");

        Map<String, Object> values = Map.of(
                "amount", 10,
                "type", "fire"
        );

        Prompt prompt = promptTemplate.apply(values);

        String answer = model.generate(prompt.toString());

        System.out.println(answer);
    }
}
