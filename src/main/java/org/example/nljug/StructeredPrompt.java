package org.example.nljug;

import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.example.ApiKeys;

import java.util.List;

public class StructeredPrompt {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        CreateArticlePrompt createArticlePromptPromt = new CreateArticlePrompt("Java"
                , "Artificial Intelligence"
                , List.of("Mind-bending", "future", "skynet", "booming"));

        Prompt prompt = StructuredPromptProcessor.toPrompt(createArticlePromptPromt);

        String answer = model.generate(prompt.toString());

        System.out.println(answer); //Title: The Mind-Bending Future of Artificial Intelligence: Is Skynet Booming? ...
    }
}

    @StructuredPrompt({
            """
            Create a {{language}} article about {{subject}}. Include all of the following keywords: {{keywords}}
            The article is a max of 100 words.
            Make sure the article exists out of the following parts:
            - Title
            - Introduction
            - Main body
            - Conclusion
            """
    })
    class CreateArticlePrompt {
        String language;
        String subject;
        List<String> keywords;

        public CreateArticlePrompt(String language, String subject, List<String> keywords) {
            this.language = language;
            this.subject = subject;
            this.keywords = keywords;
        }
    }




