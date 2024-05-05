package com.mitocode.springai.service.impl;

import com.mitocode.springai.model.Book;
import com.mitocode.springai.repo.IBookRepo;
import com.mitocode.springai.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private IBookRepo repo;


    @Override
    public Book save(Book book) throws Exception {
        return repo.save(book);
    }

    @Override
    public List<Book> saveAll(List<Book> t) throws Exception {
        return repo.saveAll(t);
    }

    @Override
    public Book update(Book book, Integer integer) throws Exception {
        return repo.save(book);
    }

    @Override
    public List<Book> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public Book findById(Integer id) throws Exception {
        return repo.findById(id).orElse(new Book());
    }

    @Override
    public void delete(Integer id) throws Exception {
        repo.deleteById(id);
    }
}
