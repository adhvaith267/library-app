package com.library;

import java.util.HashMap;
import java.util.Map;

public class Library {
    private Map<String, Book> books = new HashMap<>();
    
    public void addBook(String title) {
        books.put(title, new Book(title));
    }
    public boolean borrowBook(String title) {
        Book book = books.get(title);
        if(book != null && book.isAvailable()) {
            book.setAvailable(false);
            return true;
        }
        return false;
    }
    public boolean returnBook(String title) {
        Book book = books.get(title);
        if(book != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }
}
