package com.endyary.springgraphqlexample.author;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping("authors")
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @QueryMapping("authorById")
    public Author getById(@Argument int id) {
        return authorService.getById(id);
    }

    @MutationMapping("deleteAuthor")
    public Author deleteById(@Argument int id) {
        return authorService.delete(id);
    }

    @MutationMapping("addAuthor")
    public Author add(@Argument("author") AuthorInput authorInput) {
        return authorService.add(authorInput);
    }

    @MutationMapping("updateAuthor")
    public Author update(@Argument("id") final int id,
            @Argument("author") AuthorInput authorInput) {
        return authorService.update(id, authorInput);
    }
}
