package com.mitocode.springai.controller;

import com.openai.models.audio.AudioResponseFormat;
import com.mitocode.springai.dto.ResponseDTO;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private OpenAiAudioTranscriptionModel transcriptionModel;

    @GetMapping("/es")
    public ResponseEntity<String> transcriptES() {
        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .language("es")
                .responseFormat(AudioResponseFormat.TEXT)
                .temperature(0f)
                .build();

        Resource audioFile = new ClassPathResource("/audios/es_audio1.flac");
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);
        return ResponseEntity.ok(response.getResult().getOutput());
    }

    @GetMapping("/en")
    public ResponseEntity<String> transcriptEN(){
        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .language("en")
                .responseFormat(AudioResponseFormat.TEXT)
                .temperature(0f)
                .build();

        Resource audioFile = new ClassPathResource("/audios/en_audio2.mp3");
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);
        return ResponseEntity.ok(response.getResult().getOutput());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<String>> handleAudioUpload(@RequestParam("audio") MultipartFile audioFile) throws Exception {
        String uploadDirPath = "src/main/resources/audios/uploads/";

        // Crear el directorio de uploads si no existe
        Path uploadPath = Paths.get(uploadDirPath);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);


        // Guardar el archivo de audio en el servidor
        String fileName = "audio_" + System.currentTimeMillis() + ".mp3";
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(audioFile.getInputStream(), filePath);

        // Realizar cualquier procesamiento adicional necesario
        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .language("es")
                .responseFormat(AudioResponseFormat.TEXT)
                .temperature(0f)
                .build();

        Resource audioFileUploaded = new FileSystemResource(uploadDirPath + fileName);
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFileUploaded, transcriptionOptions);
        AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);
        return ResponseEntity.ok(new ResponseDTO<>(200, "success", response.getResult().getOutput()));
    }
}
