package com.mitocode.springai.repo;

import com.mitocode.springai.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IBookRepo extends JpaRepository<Book, Integer> {

    List<Book> findByName(String name);
}
