package com.mitocode.springai.controller;

import com.mitocode.springai.dto.AuthorBookDTO;
import com.mitocode.springai.dto.BookInfoDTO;
import com.mitocode.springai.dto.ResponseDTO;
import com.mitocode.springai.utils.ChatHistory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String result = chatClient.prompt(message).call().content();
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

    @GetMapping("/generate/prompt")
    public ResponseEntity<ResponseDTO<String>> generateTextWithPrompt(@RequestParam(value = "param") String param,
                                                                      @RequestParam(value = "topic") String topic) {
        String result = chatClient.prompt()
                .user(user -> user.text("Tell me about {param} and {topic}")
                        .param("param", param)
                        .param("topic", topic))
                .call()
                .content();
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

    @GetMapping("/generate/output")
    public ResponseEntity<AuthorBookDTO> generateOutputParser(@RequestParam(value = "author") String author){
        AuthorBookDTO authorBook = chatClient.prompt()
                .user(user -> user.text("Tell me book titles of {author}.")
                        .param("author", author))
                .call()
                .entity(AuthorBookDTO.class);
        return ResponseEntity.ok(authorBook);
    }

    @GetMapping("/generate/book/output")
    public ResponseEntity<BookInfoDTO> generateOutputParserBook(@RequestParam(value = "name") String bookName){
        BookInfoDTO authorBook = chatClient.prompt()
                .user(user -> user.text("Tell me book titles of {bookName}.")
                        .param("bookName", bookName))
                .call()
                .entity(BookInfoDTO.class);
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
        chatHistory.addMessage("1", new UserMessage(message));
        String result = chatClient.prompt()
                .messages(chatHistory.getAll("1"))
                .call()
                .content();
        chatHistory.addMessage("1", new AssistantMessage(result));
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", result));
    }

}
