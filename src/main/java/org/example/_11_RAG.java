package org.example;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Scanner;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

public class _11_RAG {

    public static void main(String[] args) throws FileNotFoundException {

        // In process model
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 0))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_PAID)
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO)
                .temperature(0d)
                .build();

        Document document = loadDocument(Paths.get("src/main/resources/Martin-and-Donny.txt"), new TextDocumentParser());
        ingestor.ingest(document);

        // Set up chatmemory to keep answers short
        Tokenizer tokenizer = new OpenAiTokenizer(GPT_3_5_TURBO);
        ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens(1000, tokenizer);
        chatMemory.add(SystemMessage.from("Keep your answers short."));

        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
                .chatLanguageModel(model)
                .contentRetriever(retriever)
                .chatMemory(chatMemory)
                .build();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Embedding store ready. Start asking questions.");
        System.out.println();

        while (scanner.hasNext()) {
            String in = scanner.nextLine();

            String answer = chain.execute(in);
            System.out.println(answer);
        }

    }
}

/*
    I tried to make parser for reading Excel files.
    It "sort of" worked. It could read data from Excel files, but could only retrieve
    information from a single line. If you tried to get information from multiple lines (like aggregating a column)
    the model would retrieve incorrect or incomplete information. This probably has to do with the structure of Excel files (XML).
    Have not found a solution, but this was a quick try-out.
 */
class ApacheExcelParser implements DocumentParser {

    @Override
    public Document parse(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFExcelExtractor extractor = new XSSFExcelExtractor(workbook);
            return Document.from(extractor.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
