package org.example;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class _09_AIServices_03_Extractor {
    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_DEMO);

        DateTimeExtractor dateTimeExtractor = AiServices.create(DateTimeExtractor.class, model);

        String text = """
            Het was kerstochtend 1961
            Ik weet het nog zo goed
            Mijn konijnenhok was leeg
            En moeder zei dat ik niet in de schuur mocht komen
            En als ik lief ging spelen dat ik dan wat lekkers kreeg
            Zij wist ook niet waar Flappie uit kon hangen
            Ze zou het papa vragen maar omdat die bezig was
            In dat fietsenschuurtje moest ik maar een uurtje
            Goed naar Flappie zoeken hij liep vast wel ergens op het gras
            
            25 minuten voor de middag
            """;

        LocalDate localDate = dateTimeExtractor.extractDate(text);
        System.out.println(localDate);

        LocalDateTime localDateTime = dateTimeExtractor.extractDateTime(text);
        System.out.println(localDateTime);
    }
}


interface DateTimeExtractor {

    @UserMessage("Extract date from {{it}}")
    LocalDate extractDate(String text);

    @UserMessage("Extract date from {{it}}")
    LocalDateTime extractDateTime(String text);
}
