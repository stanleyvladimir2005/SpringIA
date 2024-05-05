package com.mitocode.springai.controller;

import com.mitocode.springai.dto.ResponseDTO;
import lombok.val;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "images")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageClient imageClient;

    @GetMapping("/generate")
    public ResponseEntity<?> generateImage(@RequestParam("param") String param) {
        var response = imageClient.call(new ImagePrompt(param, OpenAiImageOptions.builder()
                .withModel("dall-e-3")
                .withQuality("standard")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build()));
        val url = response.getResult().getOutput().getUrl();
        return ResponseEntity.ok(new ResponseDTO<>(200, "sucess", url));
    }

    @GetMapping(value ="/generateBytes", produces =MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> generateBytes(@RequestParam("param") String param) {
        var response = imageClient.call(new ImagePrompt(param, OpenAiImageOptions.builder()
                .withModel("dall-e-3")
                .withQuality("standard")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .withResponseFormat("b64_json")
                .build()));

        val url = response.getResult().getOutput().getUrl();
        return ResponseEntity.ok(new ResponseDTO<>(200, "sucess", url));
    }
}
