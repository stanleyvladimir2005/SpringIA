package com.mitocode.springai.controller;

import com.mitocode.springai.model.Author;
import com.mitocode.springai.service.IAuthorService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

    @Autowired
    private IAuthorService service;

    @GetMapping
    public ResponseEntity<List<Author>> findAll() throws Exception {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody Author author) throws Exception {
        val obj = service.save(author);
        return ResponseEntity.created(URI.create("http://localhost:8080/authors/" + obj.getIdAuthor())).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@RequestBody Author author, @PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(service.update(author, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
