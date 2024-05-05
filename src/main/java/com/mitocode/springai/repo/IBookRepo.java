package com.mitocode.springai.repo;

import com.mitocode.springai.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepo extends JpaRepository<Book, Integer> {}
