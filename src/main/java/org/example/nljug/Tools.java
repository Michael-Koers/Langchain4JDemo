package org.example.nljug;

public class Tools {
}


interface Chatbot {
    String chat(String message);
}

class ChatBotTools {

    private static final double TAXES = 0.21d;

    public double calculateTaxes(double price) {
        return price * TAXES;
    }
}
