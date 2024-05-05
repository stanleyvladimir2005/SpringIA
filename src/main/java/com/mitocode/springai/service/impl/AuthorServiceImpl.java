package com.mitocode.springai.service.impl;

import com.mitocode.springai.model.Author;
import com.mitocode.springai.repo.IAuthorRepo;
import com.mitocode.springai.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private IAuthorRepo repo;

    @Override
    public Author save(Author author) throws Exception {
        return repo.save(author);
    }

    @Override
    public List<Author> saveAll(List<Author> t) throws Exception {
        return repo.saveAll(t);
    }

    @Override
    public Author update(Author author, Integer integer) throws Exception {
        return repo.save(author);
    }

    @Override
    public List<Author> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public Author findById(Integer id) throws Exception {
        return repo.findById(id).orElse(new Author());
    }

    @Override
    public void delete(Integer id) throws Exception {
        repo.deleteById(id);
    }
}
