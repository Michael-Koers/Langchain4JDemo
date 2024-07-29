package org.example;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Tools_Workshop {

    public static void main(String[] args) {
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(OpenAiChatModel.builder()
                        // Tools only work with paid openai license, this is not documented anywhere...
                        .apiKey("")
                        .modelName(OpenAiChatModelName.GPT_4)
                        .temperature(0d)
                        .build())
                .tools(new KlantTools())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(5))
                .build();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String in = scanner.nextLine();

            String answer = assistant.chat(in);
            System.out.println(answer);
        }
    }

}


record Klant(int id, String name) {
}

record OrderLine(int quantity, Product product) {
}

record Order(int id, Klant klant, List<OrderLine> orderLines) {
}

enum Product {
    CAR(500),
    BIKE(10),
    BASKET(5.50),
    BOAT(1000),
    HELM(17.50),
    SWORD(19.99);

    final double price;

    Product(double price) {
        this.price = price;
    }
}

interface ChatBot {
    String chat(String message);
}


class KlantTools {

    private static final Database database = Database.getDatabase();

    @Tool("Creates and returns a new customer, requires customer name as input")
    Klant createCustomer(String name) {
        return database.createNewKlant(name);
    }

    @Tool("Get customer ID by name, requires customer name as input")
    int getCustomerId(String customerName) {
        return database.getCustomerByName(customerName).id();
    }

    @Tool("Get customer by ID, requires customer ID as input")
    Klant getCustomerById(int id) {
        return database.getCustomerById(id);
    }

    @Tool("get customer orders by customer name, requires customer name as input")
    List<Order> getCustomerOrders(String customerName) {
        return database.getOrdersByKlant(database.getCustomerByName(customerName));
    }

    @Tool("Create an order for a customer, requires customer name, product and quantity as input, returns order ID")
    int createOrder(Klant klant, Product product, int quantity) {
        return database.saveOrder(klant, List.of(new OrderLine(quantity, product)));
    }

    @Tool("Get order by ID, requires order ID as input")
    Order getOrderById(int orderId) {
        return database.getOrderById(orderId);
    }

    @Tool("Returns all customers in the database")
    List<Klant> getCustomers() {
        return database.getKlants();
    }

    @Tool("Returns all orders in the database")
    List<Order> getOrders() {
        return database.getOrders();
    }
}


class Database {
    private static List<Order> orders = new ArrayList<>();
    private static List<Klant> klants = new ArrayList<>();
    private static AtomicInteger klantId = new AtomicInteger(0);
    private static AtomicInteger orderId = new AtomicInteger(0);

    private static Database database;

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    Klant createNewKlant(String name) {
        Klant klant = new Klant(klantId.getAndIncrement(), name);
        klants.add(klant);
        return klant;
    }

    Klant getCustomerByName(String name) {
        return klants.stream()
                .filter(klant -> klant.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }

    Klant getCustomerById(int id) {
        return klants.stream()
                .filter(klant -> klant.id() == id)
                .findFirst()
                .orElseThrow();
    }

    Order getOrderById(int orderId) {
        return orders.stream()
                .filter(order -> order.id() == orderId)
                .findFirst()
                .orElseThrow();
    }

    List<Order> getOrdersByKlant(Klant klant) {
        return orders.stream()
                .filter(order -> order.klant().equals(klant))
                .toList();
    }

    int saveOrder(Klant klant, List<OrderLine> orderLines) {
        int id = orderId.getAndIncrement();
        orders.add(new Order(id, klant, orderLines));
        return id;
    }

    List<Klant> getKlants() {
        return List.copyOf(klants);
    }

    List<Order> getOrders() {
        return List.copyOf(orders);
    }
}