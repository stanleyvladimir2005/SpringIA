package com.mitocode.springai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.springai.model.Author;
import com.mitocode.springai.model.Book;
import com.mitocode.springai.service.IAuthorService;
import com.mitocode.springai.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringAiApplication implements ApplicationRunner {
    private final ResourceLoader resourceLoader;
    private final IAuthorService authorService;
    private final IBookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringAiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        val resource1 = resourceLoader.getResource("classpath:json/authors.json");
        val resource2 = resourceLoader.getResource("classpath:json/books.json");
        // Leer el contenido del archivo JSON
        val jsonData1 = FileCopyUtils.copyToByteArray(resource1.getInputStream());
        val jsonData2 = FileCopyUtils.copyToByteArray(resource2.getInputStream());

        val jsonString1 = new String(jsonData1, StandardCharsets.UTF_8);
        val jsonString2 = new String(jsonData2, StandardCharsets.UTF_8);

        // Puedes usar una librería como Jackson para convertir el JSON a objetos Java
        val objectMapper = new ObjectMapper();
        List<Author> authors = objectMapper.readValue(jsonString1, new TypeReference<>(){});
        List<Book> books = objectMapper.readValue(jsonString2, new TypeReference<>(){});

        authorService.saveAll(authors);
        bookService.saveAll(books);
    }

}
