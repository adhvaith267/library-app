package com.library;

import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {
    @Test
    public void testAddAndBorrowReturnBook() {
        Library lib = new Library();
        lib.addBook("Test Driven Development");
        assertTrue(lib.borrowBook("Test Driven Development"));
        assertFalse(lib.borrowBook("Test Driven Development")); // Already borrowed
        assertTrue(lib.returnBook("Test Driven Development"));
        assertFalse(lib.returnBook("Nonexistent Book"));
    }
}
