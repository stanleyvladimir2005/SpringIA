package com.mitocode.springai.controller;

import com.mitocode.springai.model.Book;
import com.mitocode.springai.service.IBookService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private IBookService service;

    @GetMapping
    public ResponseEntity<List<Book>> findAll() throws Exception {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) throws Exception {
        val obj = service.save(book);
        return ResponseEntity.created(URI.create("http://localhost:8080/books/" + obj.getIdBook())).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@RequestBody Book book, @PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(service.update(book, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.ok().build();
    }


}
