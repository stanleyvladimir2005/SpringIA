package com.mitocode.springai.controller;

import com.mitocode.springai.service.impl.BookFunctionServiceImpl;
import com.mitocode.springai.service.impl.MockWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functions")
@RequiredArgsConstructor
public class FunctionCallingController {

    private final ChatClient chatClient;
    private final MockWeatherService weatherService;
    private final BookFunctionServiceImpl bookService;

    @GetMapping()
    public ResponseEntity<?> getWeather() {
        String response = chatClient.prompt("What's the weather like in San Francisco, Tokyo, and Paris?")
                .tools(weatherService)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book")
    public ResponseEntity<?> getBookInfo(@RequestParam("bookName") String bookName) {
        String response = chatClient.prompt("What's the book info of " + bookName + "?")
                .tools(bookService)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }

}
