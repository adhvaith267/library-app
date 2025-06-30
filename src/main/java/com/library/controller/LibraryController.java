package com.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class LibraryController {

    @GetMapping("/api/library/books")
    public List<String> getBooks() {
        return List.of("Effective Java", "Clean Code");
    }
}
