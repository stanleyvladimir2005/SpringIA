package com.mitocode.springai.service.impl;

import com.mitocode.springai.model.Book;
import com.mitocode.springai.repo.IBookRepo;
import com.mitocode.springai.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

    private final IBookRepo repo;

    @Override
    public Book save(Book book)  {
        return repo.save(book);
    }

    @Override
    public List<Book> saveAll(List<Book> t) {
        return repo.saveAll(t);
    }

    @Override
    public Book update(Book book, Integer integer) {
        return repo.save(book);
    }

    @Override
    public List<Book> findAll() {
        return repo.findAll();
    }

    @Override
    public Book findById(Integer id)  {
        return repo.findById(id).orElse(new Book());
    }

    @Override
    public void delete(Integer id)  {
        repo.deleteById(id);
    }
}
