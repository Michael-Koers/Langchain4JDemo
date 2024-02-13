package org.example;

import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.util.Scanner;

public class _05_Streaming {

    public static void main(String[] args) {

        // Alleen ChatGPT 3.5 Turbo of ChatGPT 4
        OpenAiStreamingChatModel model = OpenAiStreamingChatModel.withApiKey(ApiKeys.OPENAI_PAID);
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
