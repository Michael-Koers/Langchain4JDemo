package com.michael.koers.nljug;

import com.michael.koers.examples.ApiKeys;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.nio.file.Path;


public class EasyRAG {

    public static void main(String[] args) {

        var embeddingStore = loadDocument(Path.of("src/main/resources/Martin-and-Donny.txt"));

        RAGAssistant assistant = AiServices.builder(RAGAssistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(ApiKeys.OPENAI_PAID))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .systemMessageProvider((var o) -> "Keep your answers short and concise")
                .build();

        System.out.println("[Q]: Where are Martin and Donnie ? ");
        System.out.println("[AI] " + assistant.chat("Where are Martin and Donnie?"));
        System.out.println();

        System.out.println("[Q]: What does Martin eat?");
        System.out.println("[AI] " + assistant.chat("What does Martin eat?"));
        System.out.println();

        System.out.println("[Q]: What is Donnie?");
        System.out.println("[AI] " + assistant.chat("What is Donnie?"));
        System.out.println();

    }

    private static InMemoryEmbeddingStore<TextSegment> loadDocument(Path path) {
        Document document = FileSystemDocumentLoader.loadDocument(path);
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        return embeddingStore;
    }
}


interface RAGAssistant {
    String chat(String message);
}