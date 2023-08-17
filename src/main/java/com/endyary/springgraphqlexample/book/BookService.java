package com.endyary.springgraphqlexample.book;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.endyary.springgraphqlexample.author.Author;
import com.endyary.springgraphqlexample.author.AuthorService;

import jakarta.annotation.PostConstruct;

@Service
public class BookService {

    private final AuthorService authorService;

    private final List<Book> books = new LinkedList<>();

    private int bookId = 0;

    public BookService(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostConstruct
    void postConstruct() {
        Book book1 = new Book(++bookId, "Harry Potter and the Philosopher's Stone", 223,
                authorService.getById(1));
        Book book2 = new Book(++bookId, "Moby Dick", 635, authorService.getById(2));
        Book book3 = new Book(++bookId, "Interview with the vampire", 371,
                authorService.getById(3));

        books.add(book1);
        books.add(book2);
        books.add(book3);
    }

    public List<Book> getAll() {
        return books;
    }

    public Book getById(final int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public Book deleteById(final int id) {
        Book book = getById(id);
        books.remove(book);
        return book;
    }

    public Book add(final BookInput bookInput) {
        Book book = toBook(bookInput);
        books.add(book);
        return book;
    }

    public Book update(final int id, final BookInput bookInput) {
        Book book = getById(id);
        book.setName(bookInput.name());
        book.setPageCount(bookInput.pageCount());
        book.setAuthor(authorService.getById(bookInput.authorId()));
        return book;
    }

    private Book toBook(final BookInput bookInput) {
        Author author = authorService.getById(bookInput.authorId());
        return new Book(++bookId, bookInput.name(), bookInput.pageCount(), author);
    }
}
