package com.bookman.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookManager {
    private ArrayList<Book> books;

    public BookManager() {
        books = new ArrayList<>();
    }

    public ArrayList<Book> getBooks() {
        return this.books;
    }

    public void loadFromFile() throws IOException {
        System.out.println("Loading books...");
        String path = System.getProperty("user.dir") + "/src/main/java/com/bookman/data/books.txt";
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        for (String line : lines) {
            String id = line.substring(0, 5).trim();
            String name = line.substring(5, 50).trim();
            String price = line.substring(50).trim();
            books.add(new Book(Integer.parseInt(id), name, Double.parseDouble(price)));
        }
    }

    public void printBooks(ArrayList<Book> books) {
        if (books.size() <= 0) {
            System.out.println("(empty)");
            return;
        }
        System.out.println(String.format("%-5s %-45s %-10s", "ID", "Name", "Price"));
        for (Book book : books) {
            System.out.println(String.format("%5d %-45s %10.2f", book.getId(), book.getName(), book.getPrice()));
        }
    }

    public boolean add(Book book) {
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                return false;
            }
        }
        books.add(book);
        return true;
    }

    public Book getBookById(int id) {
        for (Book book : this.books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void remove(Book book) {
        this.books.remove(book);
    }

    public void sortDescByPrice() {
        Collections.sort(this.books, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return Double.compare(b2.getPrice(), b1.getPrice());
            }
        });
    }

    public List<Book> searchByName(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public void saveToFile() throws IOException {
        if (books.size() > 0) {
            String path = System.getProperty("user.dir") + "/src/main/java/com/bookman/data/books.txt";
            File file = new File(path);
            Files.write(Paths.get(path), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
            for (Book book : books) {
                String text = String.format("%5d %-45s %10.2f", book.getId(), book.getName(), book.getPrice());
                //Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.APPEND);
                bufferedWriter.write(text);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        }
    }
}
