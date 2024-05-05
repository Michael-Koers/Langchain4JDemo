package org.example;

import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.util.Scanner;

public class _05_Streaming {

    public static void main(String[] args) {

        // Only available with ChatGPT 3.5 Turbo or ChatGPT 4
        StreamingChatLanguageModel model = OpenAiStreamingChatModel.withApiKey(ApiKeys.OPENAI_PAID);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            System.out.println();
            String in = scanner.nextLine();

            model.generate(in, new StreamingResponseHandler<>() {
                @Override
                public void onNext(String s) {
                    System.out.print(s);
                }

                @Override
                public void onError(Throwable error) {
                    System.out.println(error.toString());
                }
            });
        }
    }
}
