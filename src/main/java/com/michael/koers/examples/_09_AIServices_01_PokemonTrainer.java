package com.michael.koers.examples;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public class _09_AIServices_01_PokemonTrainer {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        PokemonTrainerService pokemonTrainer = AiServices.create(PokemonTrainerService.class, model);

        System.out.println(pokemonTrainer.fight("Let's fight!"));


//        System.out.println();

//        System.out.println(pokemonTrainer.defeat("Let's fight!"));
//
//        System.out.println();
//
//        System.out.println(pokemonTrainer.team("Let's fight!"));
    }
}

interface PokemonTrainerService {
    @SystemMessage("""
            You are a pokemon trainer.
            When you encounter the player, you say something random about challenging the player to a pokemon battle.
            """)
    String fight(String s);

}


//
//    @SystemMessage("""
//            You are a pokemon trainer.
//            The player has just defeated you. You are in disbelief. Say something random about losing.
//            """)
//    String defeat(String s);
//
//    @SystemMessage("""
//            You are a pokemon trainer. Your team consists of low-level bug-type pokemon, with a maximum of six pokemon.
//            Generate a random team between 1 to 6 pokemon, only using generation 1 or 2 pokemon.
//            List the pokemon as follows:
//            Name: [..], level: [..], moves: [..], [..], ..
//
//            Keep the answer as short as possible.
//            """)
//    String team(String s);

