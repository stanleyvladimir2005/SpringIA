package com.mitocode.springai.service.impl;

import com.mitocode.springai.model.Author;
import com.mitocode.springai.repo.IAuthorRepo;
import com.mitocode.springai.service.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements IAuthorService {

    private final IAuthorRepo repo;

    @Override
    public Author save(Author author) {
        return repo.save(author);
    }

    @Override
    public List<Author> saveAll(List<Author> t)  {
        return repo.saveAll(t);
    }

    @Override
    public Author update(Author author, Integer integer)  {
        return repo.save(author);
    }

    @Override
    public List<Author> findAll()  {
        return repo.findAll();
    }

    @Override
    public Author findById(Integer id) {
        return repo.findById(id).orElse(new Author());
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
