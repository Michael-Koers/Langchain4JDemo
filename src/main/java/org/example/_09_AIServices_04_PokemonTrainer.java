package org.example;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.structured.Description;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;

import java.util.List;

public class _09_AIServices_04_PokemonTrainer {
    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        PokemonTrainerGeneratorService pokemonTrainer = AiServices.create(PokemonTrainerGeneratorService.class, model);

        Trainer trainer = pokemonTrainer.generate("Generate a low level trainer named 'Kelvin' with 2 bug and 2 fire pokemon");

        System.out.println(trainer);
    }
}

interface PokemonTrainerGeneratorService {

    @UserMessage("You generate random pokemon trainers with random pokemon, according to {{it}}")
    Trainer generate(String text);
}

record Trainer(String name, List<Pokemon> team) {
}

record Pokemon(String name
        , String type
        , int level
        , int hp
        , @Description("Random number of moves between 1 and 4") List<String> moves)
{}