package com.endyary.springgraphqlexample.book;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping("books")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @QueryMapping("bookById")
    public Book getById(@Argument final int id) {
        return bookService.getById(id);
    }

    @MutationMapping("deleteBook")
    public Book deleteById(@Argument final int id) {
        return bookService.deleteById(id);
    }

    @MutationMapping("addBook")
    public Book add(@Argument("book") final BookInput bookInput) {
        return bookService.add(bookInput);
    }

    @MutationMapping("updateBook")
    public Book update(@Argument("id") final int id, @Argument("book") final BookInput bookInput) {
        return bookService.update(id, bookInput);
    }
}
