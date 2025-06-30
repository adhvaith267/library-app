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
        // Initialize with some books
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
    
    public boolean borrowBook(Long id) {
        Book book = books.get(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            return true;
        }
        return false;
    }
    
    public boolean returnBook(Long id) {
        Book book = books.get(id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }
    
    public List<Book> getAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .toList();
    }
}

