package com.endyary.springgraphqlexample;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.endyary.springgraphqlexample.book.Book;

@SpringBootTest
@AutoConfigureGraphQlTester
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void testGetAll() {
        String query = "{ books { id name author {firstName lastName} } }";
        List<Book> books = graphQlTester.document(query)
                .execute()
                .path("data.books[*]")
                .entityList(Book.class)
                .get();

        Assertions.assertFalse(books.isEmpty());
        Assertions.assertNotNull(books.get(0).getName());
    }

    @Test
    void testGetById() {
        String query = "{ bookById(id: 1) { id name pageCount author {firstName lastName} } }";
        Book book = graphQlTester.document(query)
                .execute()
                .path("data.bookById")
                .entity(Book.class)
                .get();

        Assertions.assertEquals(1, book.getId());
        Assertions.assertEquals(223, book.getPageCount());
    }

    @Test
    void testAdd() {
        String mutation =
                "mutation { addBook(book: { name: \"New Book\", pageCount: 123, authorId: 1 }) { id name } }";
        Book book = graphQlTester.document(mutation)
                .execute()
                .path("data.addBook")
                .entity(Book.class)
                .get();

        Assertions.assertNotNull(book);
        Assertions.assertEquals("New Book", book.getName());
    }

    @Test
    void testUpdate() {
        String mutation =
                "mutation { updateBook(id: 1, book: { name: \"New Book\", pageCount: 123, authorId: 1 }) { id name } }";
        Book book = graphQlTester.document(mutation)
                .execute()
                .path("data.updateBook")
                .entity(Book.class)
                .get();

        Assertions.assertNotNull(book);
        Assertions.assertEquals("New Book", book.getName());
    }

    @Test
    void testDelete() {
        String mutation =
                "mutation { deleteBook(id: 2) { id name } }";
        Book book = graphQlTester.document(mutation)
                .execute()
                .path("data.deleteBook")
                .entity(Book.class)
                .get();

        Assertions.assertNotNull(book);
        Assertions.assertEquals(2, book.getId());
    }

    @Test
    void testGetByIdNotFound() {
        String query = "{ bookById(id: 5) { id name pageCount author {firstName lastName} } }";
        graphQlTester.document(query)
                .execute()
                .errors()
                .expect(e -> Objects.equals(e.getMessage(), "Author not found with id: 5"));
    }
}
