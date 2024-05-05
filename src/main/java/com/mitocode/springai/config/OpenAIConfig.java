package com.mitocode.springai.config;

import lombok.val;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    /* Se comenta porque da conflicto con las function Calling
    @Value("${spring.ai.openai.api-key}")
    private String API_KEY;

    @Bean
    public OpenAiChatClient openAiChatClient() {
        val openAiApi = new OpenAiApi(API_KEY);

        return new OpenAiChatClient(openAiApi, OpenAiChatOptions.builder()
                .withModel("gpt-3.5-turbo")
                .withTemperature(1f)
                .withMaxTokens(200)
                .build()
        );
    }*/
}
