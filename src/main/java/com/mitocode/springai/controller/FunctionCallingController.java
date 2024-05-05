package com.mitocode.springai.controller;

import lombok.val;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/functions")
public class FunctionCallingController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping()
    public ResponseEntity<?> getWeather() {
        val userMessage = new UserMessage("What's the weather like in San Francisco, Tokyo, and Paris?");
        var response = chatClient.call(new Prompt(List.of(userMessage),
                                        OpenAiChatOptions.builder().withFunction("WeatherInfo").build()));

        return ResponseEntity.ok(response.getResult().getOutput().getContent());
    }
    @GetMapping("/book")
    public ResponseEntity<?> getBookInfo(@RequestParam("bookName") String bookName) {
        val userMessage = new UserMessage("What's the book info of " + bookName + "?" );
        var response = chatClient.call(new Prompt(List.of(userMessage),
                                      OpenAiChatOptions.builder().withFunction("BookInfo").build() ));
        return ResponseEntity.ok(response.getResult().getOutput().getContent());
    }

}
