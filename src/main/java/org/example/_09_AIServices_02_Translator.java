package org.example;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;
import java.util.Locale;

public class _09_AIServices_02_Translator {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        TextUtils textUtils = AiServices.create(TextUtils.class, model);

        String answer = textUtils.translate(Locale.GERMAN, "Hello World, welcome to my presentation about langchain4j");
        System.out.println(answer);

        String text = """
                A Locale object represents a specific geographical, political, or cultural region.
                An operation that requires a Locale to perform its task is called locale-sensitive and uses the Locale
                to tailor information for the user. For example, displaying a number is a locale-sensitive operation—
                the number should be formatted according to the customs and conventions of the user's
                native country, region, or culture.
                """;

//        List<String> bulletPoints = textUtils.summarize(text, 2);
//        System.out.println(bulletPoints);
    }
}

interface TextUtils{

    @SystemMessage("Translate text to {{language}}")
    @UserMessage("Translate the following text: {{text}}")
    String translate(@V("language")Locale language, @V("text") String text);

    @SystemMessage("Summarize the user message to {{n}} bullet points and only bullet points")
    List<String> summarize(@UserMessage String message, @V("n") int n);
}