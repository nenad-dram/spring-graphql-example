package com.endyary.springgraphqlexample;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.endyary.springgraphqlexample.author.Author;

@SpringBootTest
@AutoConfigureGraphQlTester
class AuthorControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void testGetAll() {
        String query = "{ authors { id firstName lastName } }";
        List<Author> authors = graphQlTester.document(query)
                .execute()
                .path("data.authors[*]")
                .entityList(Author.class)
                .get();

        Assertions.assertFalse(authors.isEmpty());
        Assertions.assertNotNull(authors.get(0).getFirstName());
    }

    @Test
    void testGetById() {
        String query = "{ authorById(id: 1) { id firstName lastName } }";
        Author author = graphQlTester.document(query)
                .execute()
                .path("data.authorById")
                .entity(Author.class)
                .get();

        Assertions.assertEquals(1, author.getId());
        Assertions.assertEquals("Joanne", author.getFirstName());
    }

    @Test
    void testAdd() {
        String mutation =
                "mutation { addAuthor(author: { firstName: \"John\" lastName: \"Doe\" }) { id firstName lastName } }";
        Author author = graphQlTester.document(mutation)
                .execute()
                .path("data.addAuthor")
                .entity(Author.class)
                .get();

        Assertions.assertNotNull(author);
        Assertions.assertEquals("John", author.getFirstName());
    }

    @Test
    void testUpdate() {
        String mutation =
                "mutation { updateAuthor(id: 1 author: { firstName: \"Jane\" lastName: \"Rowling\" }) { id firstName lastName } }";
        Author author = graphQlTester.document(mutation)
                .execute()
                .path("data.updateAuthor")
                .entity(Author.class)
                .get();

        Assertions.assertNotNull(author);
        Assertions.assertEquals("Jane", author.getFirstName());
    }

    @Test
    void testDelete() {
        String mutation =
                "mutation { deleteAuthor(id: 2) { id firstName lastName } }";
        Author author = graphQlTester.document(mutation)
                .execute()
                .path("data.deleteAuthor")
                .entity(Author.class)
                .get();

        Assertions.assertNotNull(author);
        Assertions.assertEquals(2, author.getId());
    }

    @Test
    void testGetByIdNotFound() {
        String query = "{ bookById(id: 5) { id name } }";
        graphQlTester.document(query)
                .execute()
                .errors()
                .expect(e -> Objects.equals(e.getMessage(), "Book not found with id: 5"));
    }
}
