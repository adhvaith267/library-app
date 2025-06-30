package com.library.controller;

import com.library.service.LibraryService;
import com.library.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/library/books")
    public List<Book> getBooks() {
        return libraryService.getAllBooks();
    }
}

