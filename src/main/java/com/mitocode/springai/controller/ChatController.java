package com.mitocode.springai.controller;

import com.mitocode.springai.dto.AuthorBookDTO;
import com.mitocode.springai.dto.BookInfoDTO;
import com.mitocode.springai.dto.ResponseDTO;
import com.mitocode.springai.utils.ChatHistory;
import lombok.val;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.ChatMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/chats")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatHistory chatHistory;

    @GetMapping("/generate")
    public ResponseEntity<ResponseDTO<String>> generateText(@RequestParam(value = "message") String message) {
        val chatResponse = chatClient.call(new Prompt(message));
        val result = chatResponse.getResult().getOutput().getContent();
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

    @GetMapping("/generate/prompt")
    public ResponseEntity<ResponseDTO<String>> generateTextWithPrompt(@RequestParam(value = "param") String param,
                                                                      @RequestParam(value = "topic") String topic) {
        val promptTemplate = new PromptTemplate("Tell me about {param} and {topic}");
        val prompt = promptTemplate.create(Map.of("param", param, "topic", topic));
        val chatResponse = chatClient.call(prompt);
        val result = chatResponse.getResult().getOutput().getContent();
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

    @GetMapping("/generate/output")
    public ResponseEntity<AuthorBookDTO> generateOutputParser(@RequestParam(value = "author") String author){
        val outputParser = new BeanOutputParser<>(AuthorBookDTO.class);
        val template = """
                Tell me book titles of {author}. {format}
                """;

        val promptTemplate = new PromptTemplate(template, Map.of("author", author, "format", outputParser.getFormat()));
        val prompt = promptTemplate.create(Map.of("author", author));
        val chatResponse = chatClient.call(prompt);
        val generation = chatResponse.getResult();
        val authorBook = outputParser.parse(generation.getOutput().getContent());
        return ResponseEntity.ok(authorBook);
    }

    @GetMapping("/generate/book/output")
    public ResponseEntity<BookInfoDTO> generateOutputParserBook(@RequestParam(value = "name") String bookName){
        val outputParser = new BeanOutputParser<>(BookInfoDTO.class);
        val template = """
                Tell me book titles of {bookName}. {format}
                """;

        val promptTemplate = new PromptTemplate(template, Map.of("bookName", bookName, "format", outputParser.getFormat() ));
        val prompt = promptTemplate.create(Map.of("bookName", bookName));
        val chatResponse = chatClient.call(prompt);
        val generation = chatResponse.getResult();
        val authorBook = outputParser.parse(generation.getOutput().getContent());
        return ResponseEntity.ok(authorBook);
    }

    /*@GetMapping("/ollama")
    public ResponseEntity<String> testOllama(@RequestParam("text") String text){
        val response = chatClient.call(new Prompt(text, OllamaOptions.create().withModel("codellama:7b")));
        val content = response.getResult().getOutput().getContent();
        return ResponseEntity.ok(content);
    }*/

    @GetMapping("/generateConversation")
    public ResponseEntity<ResponseDTO<String>> generateConversation(@RequestParam(value = "message") String message){
        chatHistory.addMessage("1", new ChatMessage(MessageType.USER, message));
        val chatResponse = chatClient.call(new Prompt(chatHistory.getAll("1")));
        val result = chatResponse.getResult().getOutput().getContent();
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

}
