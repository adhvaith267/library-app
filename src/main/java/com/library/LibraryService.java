package com.library.service;

import com.library.model.Book;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LibraryService {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public LibraryService() {
        // Initialize with some books (with 1 copy each)
        addBook("Effective Java", "Joshua Bloch");
        addBook("Clean Code", "Robert Martin");
        addBook("Spring in Action", "Craig Walls");
    }
    
    public Book addBook(String title, String author) {
        Long id = idGenerator.getAndIncrement();
        Book book = new Book(id, title, author);
        books.put(id, book);
        return book;
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    public Book getBook(Long id) {
        return books.get(id);
    }
    
    public void borrowBook(Long bookId) {
        Book book = getBook(bookId);
        if (book != null && book.getNumcopies() > 0) {
            book.borrowBook();
        }
    }
}

