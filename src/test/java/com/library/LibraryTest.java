package com.library;

import com.library.service.LibraryService;
import com.library.model.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    @Test
    public void testAddBookIncreasesCount() {
        LibraryService library = new LibraryService();
        int initialCount = library.getAllBooks().size();
        library.addBook("Clean Code", "Robert C. Martin");
        int newCount = library.getAllBooks().size();
        assertEquals(initialCount + 1, newCount);
    }
    @Test
    public void testGetBookById() {
        LibraryService library = new LibraryService();
        library.addBook("Effective Java", "Joshua Bloch");
        Book book = library.getBook(4L); // Assuming 3 books are pre-added, the new one gets ID 4
        assertNotNull(book);
        assertEquals("Effective Java", book.getTitle());
    }
    @Test
    public void testListAllBooks() {
        LibraryService library = new LibraryService();
        assertTrue(library.getAllBooks().size() >= 3); // Should have at least 3 books from constructor
    }
}

