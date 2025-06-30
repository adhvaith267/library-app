package com.library;

import com.library.model.Book;
import com.library.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class LibraryTest {

    private LibraryService libraryService;

    @BeforeEach
    public void setup() {
        libraryService = new LibraryService();
    }

    @Test
    public void testAddBookIncreasesCopiesIfExists() {
        // Get the existing "Effective Java" book
        Book existingBook = libraryService.getAllBooks().stream()
            .filter(b -> b.getTitle().equals("Effective Java"))
            .findFirst()
            .orElse(null);
        
        assertNotNull(existingBook);
        int initialCopies = existingBook.getNumcopies();

        // Add the same book again
        libraryService.addBook("Effective Java", "Joshua Bloch");

        // Copies should increase
        assertEquals(initialCopies + 1, existingBook.getNumcopies());
        // Total book count should remain the same
        assertEquals(3, libraryService.getAllBooks().size());
    }

    @Test
    public void testAddNewBookCreatesNewEntry() {
        // Add a new book
        Book newBook = libraryService.addBook("Domain-Driven Design", "Eric Evans");

        // New book should be added
        assertEquals(4, libraryService.getAllBooks().size());
        assertEquals(1, newBook.getNumcopies());
    }

    @Test
    public void testBorrowBookDecreasesCopies() {
        // Get the first book
        Book book = libraryService.getAllBooks().get(0);
        int initialCopies = book.getNumcopies();

        // Borrow the book
        libraryService.borrowBook(book.getId());

        // Copies should decrease
        assertEquals(initialCopies - 1, book.getNumcopies());
    }

    @Test
    public void testBorrowBookDoesNotGoBelowZero() {
        // Add a new book with 1 copy
        Book book = libraryService.addBook("Test Book", "Test Author");
        assertEquals(1, book.getNumcopies());

        // Borrow once
        libraryService.borrowBook(book.getId());
        assertEquals(0, book.getNumcopies());

        // Try to borrow again
        libraryService.borrowBook(book.getId());
        assertEquals(0, book.getNumcopies()); // Should remain 0
    }

    @Test
    public void testGetBookByIdReturnsCorrectBook() {
        // Get first book
        Book expectedBook = libraryService.getAllBooks().get(0);
        
        // Find by ID
        Book actualBook = libraryService.getBook(expectedBook.getId());
        
        // Should match
        assertNotNull(actualBook);
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
    }
}

