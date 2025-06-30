package com.library;

public class Book {
    private String title;
    private boolean available;
    
    public Book(String title) {
        this.title = title;
        this.available = true;
    }
    public String getTitle() { return title; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean status) { available = status; }
}
