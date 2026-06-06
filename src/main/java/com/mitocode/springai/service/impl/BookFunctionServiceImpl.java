package com.mitocode.springai.service.impl;

import com.mitocode.springai.model.Book;
import com.mitocode.springai.repo.IBookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BookFunctionServiceImpl implements Function<BookFunctionServiceImpl.Request, BookFunctionServiceImpl.Response> {

    private final IBookRepo repo;

    public record Request(String bookName){}
    public record Response(List<Book> books){}

    public Response apply(Request request) {
        List<Book> books = repo.findByName(request.bookName);
        return new BookFunctionServiceImpl.Response(books);
    }

    @Tool(name = "BookInfo", description = "Get book info from a book name")
    public String bookInfo(String bookName) {
        return repo.findByName(bookName).toString();
    }
}
