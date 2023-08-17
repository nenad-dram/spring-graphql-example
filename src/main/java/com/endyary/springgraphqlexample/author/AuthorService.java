package com.endyary.springgraphqlexample.author;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class AuthorService {

    private static final List<Author> authors = new ArrayList<>();

    private int authorId = 0;

    @PostConstruct
    void postConstruct() {
        Author author1 = new Author(++authorId, "Joanne", "Rowling");
        Author author2 = new Author(++authorId, "Herman", "Melville");
        Author author3 = new Author(++authorId, "Anne", "Rice");

        authors.add(author1);
        authors.add(author2);
        authors.add(author3);
    }

    public List<Author> getAll() {
        return authors;
    }

    public Author getById(final int id) {
        return authors.stream().filter(author -> author.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    public Author delete(final int id) {
        Author author = getById(id);
        authors.remove(author);
        return author;
    }

    public Author add(final AuthorInput authorInput) {
        Author author = toAuthor(authorInput);
        authors.add(author);
        return author;
    }

    public Author update(final int id, final AuthorInput authorInput) {
        Author author = getById(id);
        author.setFirstName(authorInput.firstName());
        author.setLastName(authorInput.lastName());
        return author;
    }

    private Author toAuthor(final AuthorInput authorInput) {
        return new Author(++authorId, authorInput.firstName(), authorInput.lastName());
    }
}
