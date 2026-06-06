package com.mitocode.springai.controller;

import com.mitocode.springai.dto.ResponseDTO;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.image.ImageModel;
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
    private ImageModel imageModel;

    @GetMapping("/generate")
    public ResponseEntity<?> generateImage(@RequestParam("param") String param) {
        ImageResponse response = imageModel.call(new ImagePrompt(param, OpenAiImageOptions.builder()
                .model("dall-e-3")
                .quality("standard")
                .N(1)
                .height(1024)
                .width(1024)
                .build()));
        String url = response.getResult().getOutput().getUrl();
        return ResponseEntity.ok(new ResponseDTO<>(200, "sucess", url));
    }

    @GetMapping(value ="/generateBytes", produces =MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> generateBytes(@RequestParam("param") String param) {
        ImageResponse response = imageModel.call(new ImagePrompt(param, OpenAiImageOptions.builder()
                .model("dall-e-3")
                .quality("standard")
                .N(1)
                .height(1024)
                .width(1024)
                .responseFormat("b64_json")
                .build()));

        String url = response.getResult().getOutput().getUrl();
        return ResponseEntity.ok(new ResponseDTO<>(200, "sucess", url));
    }
}
