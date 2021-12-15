package com.bookman;

import com.bookman.model.Book;
import com.bookman.model.BookManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String option = "";
        BookManager bookManager = new BookManager();
        bookManager.loadFromFile();
        option = printMenu();
        while (true) {
            switch (Integer.parseInt(option)) {
                case 1:
                    bookManager.printBooks(bookManager.getBooks());
                    option = printMenu();
                    break;
                case 2:
                    addNewBook(bookManager);
                    option = printMenu();
                    break;
                case 3:
                    editNewBook(bookManager);
                    option = printMenu();
                    break;
                case 4:
                    deleteBook(bookManager);
                    option = printMenu();
                    break;
                case 5:
                    searchByName(bookManager);
                    option = printMenu();
                    break;
                case 6:
                    bookManager.sortDescByPrice();
                    option = printMenu();
                    break;
                case 0:
                    saveAndExit(bookManager);
                    break;
                default:
                    System.out.println("Invalid option!");
                    option = printMenu();
                    break;
            }
        }
    }

    public static void saveAndExit(BookManager bookManager) throws IOException {
        bookManager.saveToFile();
        System.out.println("Saving to file...");
        System.out.println("Bye!");
        System.exit(0);
    }

    public static void searchByName(BookManager bookManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter keyword:");
        String keyword = scanner.nextLine();
        List<Book> result = bookManager.searchByName(keyword);
        if (result.size() <= 0) {
            System.out.println("(empty)");
        } else {
            bookManager.printBooks(new ArrayList<>(result));
        }
    }

    public static void deleteBook(BookManager bookManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book id: ");
        String id = scanner.nextLine();
        Book book = bookManager.getBookById(Integer.parseInt(id));
        if (book != null) {
            bookManager.remove(book);
            System.out.println("Deleted successfully.");
            return;
        }
        System.out.println("Invalid ID!");
    }

    public static void editNewBook(BookManager bookManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book id: ");
        String id = scanner.nextLine();
        scanner = new Scanner(System.in);
        System.out.println("Enter book name: ");
        String name = scanner.nextLine();
        scanner = new Scanner(System.in);
        System.out.println("Enter book price: ");
        String price = scanner.nextLine();
        Book book = bookManager.getBookById(Integer.parseInt(id));
        if (book != null) {
            book.setName(name);
            book.setPrice(Double.parseDouble(price));
            System.out.println("Updated successfully.");
            return;

        }
        System.out.println("Invalid ID!");
    }

    public static void addNewBook(BookManager bookManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book id: ");
        String id = scanner.nextLine();
        scanner = new Scanner(System.in);
        System.out.println("Enter book name: ");
        String name = scanner.nextLine();
        scanner = new Scanner(System.in);
        System.out.println("Enter book price: ");
        String price = scanner.nextLine();
        Book book = new Book(Integer.parseInt(id), name, Double.parseDouble(price));
        boolean result = bookManager.add(book);
        if (!result) {
            System.out.println("Duplicated ID!");
            return;
        }
        System.out.println("Added successfully.");
    }

    public static String printMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("1. list all books");
        System.out.println("2. add a new book");
        System.out.println("3. edit book");
        System.out.println("4. delete a book");
        System.out.println("5. search books by name");
        System.out.println("6. sort books descending by price");
        System.out.println();
        System.out.println("0. save & exit");
        System.out.println("---------------------------------------------");
        System.out.println("Your option:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
