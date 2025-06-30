package com.library.model;

public class Book {
    private Long id;
    private String title;
    private String author;
    private int numcopies = 1;  // Default to 1 copy

    public Book() {}

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getNumcopies() { return numcopies; }
    public void setNumcopies(int numcopies) { this.numcopies = numcopies; }
    
    public void borrowBook() {
        if (this.numcopies > 0) {
            this.numcopies--;
        }
    }
}

