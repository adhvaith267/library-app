package com.library.controller;

import com.library.model.Book;
import com.library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // Show all books as JSON (for API)
    @GetMapping("/api/library/books")
    @ResponseBody
    public List<Book> getBooks() {
        return libraryService.getAllBooks();
    }

    // Show the UI
    @GetMapping("/")
    public String showBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "index";
    }

    // Handle form submission to add a book
    @PostMapping("/add-book")
    public String addBook(@RequestParam String title, @RequestParam String author) {
        libraryService.addBook(title, author);
        return "redirect:/";
    }
    @PostMapping("/borrow/{bookId}")
    public String borrowBook(@PathVariable Long bookId) {
    libraryService.borrowBook(bookId);
    return "redirect:/";
}

}

