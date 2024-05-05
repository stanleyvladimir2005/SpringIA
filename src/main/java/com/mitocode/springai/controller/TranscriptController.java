package com.mitocode.springai.controller;

import com.mitocode.springai.dto.ResponseDTO;
import lombok.val;
import org.springframework.ai.openai.OpenAiAudioTranscriptionClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transcripts")
@CrossOrigin(origins = "*")
public class TranscriptController {

    //Se usa el modelo Whisper
    @Autowired
    private OpenAiAudioTranscriptionClient transcriptionClient;

    @GetMapping("/es")
    public ResponseEntity<String> transcriptES() {
        val transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withLanguage("es")
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .build();

        val audioFile = new ClassPathResource("/audios/es_audio1.flac");
        val transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        val response = transcriptionClient.call(transcriptionRequest);
        return ResponseEntity.ok(response.getResult().getOutput());
    }

    @GetMapping("/en")
    public ResponseEntity<String> transcriptEN(){
        val transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withLanguage("en")
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .build();

        val audioFile = new ClassPathResource("/audios/en_audio2.mp3");
        val transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        val response = transcriptionClient.call(transcriptionRequest);
        return ResponseEntity.ok(response.getResult().getOutput());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<String>> handleAudioUpload(@RequestParam("audio") MultipartFile audioFile) throws Exception {
        val uploadDirPath = "src/main/resources/audios/uploads/";

        // Crear el directorio de uploads si no existe
        val uploadPath = Paths.get(uploadDirPath);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);


        // Guardar el archivo de audio en el servidor
        val fileName = "audio_" + System.currentTimeMillis() + ".mp3";
        val filePath = uploadPath.resolve(fileName);
        Files.copy(audioFile.getInputStream(), filePath);

        // Realizar cualquier procesamiento adicional necesario
        val transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withLanguage("es")
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .build();

        val audioFileUploaded = new FileSystemResource(uploadDirPath + fileName);
        val transcriptionRequest = new AudioTranscriptionPrompt(audioFileUploaded, transcriptionOptions);
        val response = transcriptionClient.call(transcriptionRequest);
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", response.getResult().getOutput()));
    }
}