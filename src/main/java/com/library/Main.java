package com.library;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();
        lib.addBook("Effective Java");
        lib.addBook("Clean Code");
        System.out.println("Borrowing 'Effective Java': " + lib.borrowBook("Effective Java"));
        System.out.println("Returning 'Effective Java': " + lib.returnBook("Effective Java"));
    }
}
