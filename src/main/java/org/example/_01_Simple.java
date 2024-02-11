package org.example;

import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.Scanner;

public class _01_Simple {
    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String in = scanner.nextLine();

            String answer = model.generate(in);
            System.out.println(answer);
        }
    }
}