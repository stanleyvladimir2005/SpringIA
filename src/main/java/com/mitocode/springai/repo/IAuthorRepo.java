package com.mitocode.springai.repo;

import com.mitocode.springai.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorRepo  extends JpaRepository<Author, Integer> {}
