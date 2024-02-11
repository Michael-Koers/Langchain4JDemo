package org.example;

import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.List;

public class _04_StructeredPrompt {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        CreateRecipePrompt createRecipePrompt = new CreateRecipePrompt(
                "spaghetti",
                List.of("olive oil", "pasta", "tomato", "garlic", "water"));

        Prompt prompt = StructuredPromptProcessor.toPrompt(createRecipePrompt);

        String answer = model.generate(prompt.toString());

        System.out.println(answer);
    }
}


@StructuredPrompt({
        """
                Create a recipe of {{dish}} that can be prepared only using {{ingredients}}.
                Structure your answer in the following way:
                                
                Recipe name: ...
                Description: ...
                Preparation time: ...
                                
                Required ingredients:
                - ...
                - ...
                                
                Instructions:
                - ...
                - ...
                                
                Make sure to mention it's as good as grandma's recipe in the description
                """
})
class CreateRecipePrompt {

    String dish;
    List<String> ingredients;

    public CreateRecipePrompt(String dish, List<String> ingredients) {
        this.dish = dish;
        this.ingredients = ingredients;
    }
}




