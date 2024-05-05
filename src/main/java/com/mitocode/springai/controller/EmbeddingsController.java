package com.mitocode.springai.controller;

import lombok.val;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/embeddings")
public class EmbeddingsController {

    @Autowired
    private EmbeddingClient embeddingClient;

    @Autowired
    private VectorStore vectorStore;

    @GetMapping("/generate")
    public Map<String, EmbeddingResponse> generateEmbeddings(@RequestParam (value ="message") String message) {
        val embeddingResponse = this.embeddingClient.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }

    @GetMapping("/vectorstore")
    public List<Document> useVectorStore(@RequestParam (value ="message") String message) {
        val documents = List.of(
                new Document("Spring AI es lo maximo", Map.of("meta1", "meta1")),
                new Document("Python es mas popular en la IA Deep Learning"),
                new Document("El futuro es la inteligencia artificial", Map.of("meta2", "meta2"))
        );
        vectorStore.add(documents);
        return vectorStore.similaritySearch(SearchRequest.query(message).withTopK(1));
    }

}
