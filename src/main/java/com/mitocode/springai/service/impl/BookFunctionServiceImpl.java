package com.mitocode.springai.service.impl;

import com.mitocode.springai.model.Book;
import com.mitocode.springai.repo.IBookRepo;
import lombok.RequiredArgsConstructor;
import lombok.val;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class BookFunctionServiceImpl implements Function<BookFunctionServiceImpl.Request, BookFunctionServiceImpl.Response> {

    private final IBookRepo repo;

    public record Request(String bookName){}
    public record Response(List<Book> books){}

    public Response apply(Request request) {
        val books = repo.findByName(request.bookName);
        return new BookFunctionServiceImpl.Response(books);
    }
}
