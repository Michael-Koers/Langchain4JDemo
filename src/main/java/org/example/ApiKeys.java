package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApiKeys {

    public static final String OPENAI_DEMO;
    public static final String OPENAI_PAID;

    static {
        try {
            OPENAI_DEMO = Files.readString(Paths.get("src/main/resources/openai_demo.key"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            OPENAI_PAID = Files.readString(Paths.get("src/main/resources/openai_paid.key"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
