package com.michael.koers.nljug;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import com.michael.koers.examples.ApiKeys;

import java.util.List;

public class AIServices {
    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_PAID);


        AIAssistant assistant = AiServices.create(AIAssistant.class, model);

        String answer = assistant.chat("What is the NLJUG?");
        System.out.println(answer);

        Trainer trainer = assistant.generate("Generate a low level trainer named 'Kelvin' with 2 bug and 2 fire pokemon");
        System.out.println(trainer);


    }
}

interface AIAssistant {
    String chat(String message);

    @SystemMessage("Generate random pokemon teams in accordance with User Message")
    Trainer generate(@UserMessage String message);
}

    record Trainer(String name, List<Pokemon> team) {}

    record Pokemon(String name, String type, int level, List<String> moves) {}