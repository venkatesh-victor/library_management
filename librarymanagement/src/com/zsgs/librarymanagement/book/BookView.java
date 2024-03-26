package com.zsgs.librarymanagement.book;

import com.zsgs.librarymanagement.model.Book;

import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

public class BookView {
    private Scanner sc = new Scanner(System.in);
    private BookModel bookModel;

    public BookView () {
        bookModel = new BookModel(this);
    }

    public void init() {
        try {
            while (true) {
                System.out.println();
                System.out.println("1. Add book\n2.Search Book\n3.Update Book\n4.View all books");
                System.out.println("5. Return to home page\nEnter your choice.");

                int choice = sc.nextInt();

                switch(choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        searchBook();
                        break;
                    case 3:
                        updateBook();
                        break;
                    case 4:
                        viewBook();
                        break;
                    case 5:
                        bookModel.saveBook();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch(InputMismatchException e) {
            System.out.println("Invalid input. Try again.");
            init();
        }
    }

    public void addBook(){
        try {
            sc.nextLine();
            System.out.print("Enter book name: ");
            String bookName = sc.nextLine();
            System.out.print("Enter author name: ");
            String author = sc.nextLine();
            System.out.print("Enter publication: ");
            String publication = sc.nextLine();
            System.out.print("Enter edition: ");
            String edition = sc.nextLine();
            System.out.print("Enter genre: ");
            String genre = sc.nextLine();
            System.out.print("Enter book id: ");
            long bookId = sc.nextLong();
            System.out.print("Enter book available count: ");
            int availableCount = sc.nextInt();
            System.out.print("Enter book volume: ");
            int volume = sc.nextInt();
            sc.nextLine();

            bookModel.setupBook(bookName, author, publication,
                    edition, genre, bookId, availableCount, volume);
        } catch(InputMismatchException e) {
            System.out.println("Invalid book details. Try again.");
            addBook();
        }
    }
    public void searchBook() {
        sc.nextLine();
        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();
        bookModel.findBook(bookName.toUpperCase());
    }
    public void updateBook() {
        System.out.println("1. Update edition\n2. Update volume\n3. Update count");
        System.out.println("4. Go back.\nEnter your choice.");

        int choice = 0;
        try {
            choice = sc.nextInt();
        } catch(InputMismatchException e) {
            showAlert("Invalid choice. Try again");
            updateBook();
        }

        switch (choice) {
            case 1: updateEdition(); break;
            case 2: updateVolume(); break;
            case 3: updateAvailableCount(); break;
            case 4: break;
            default: showAlert("Invalid choice try again."); updateBook();
        }
    }

    public void updateAvailableCount() {
        try {
            System.out.print("Enter book id to update: ");
            long bookId = sc.nextLong();
            sc.nextLine();
            System.out.print("Enter the availbale count");
            int availableCount = sc.nextInt();

            bookModel.updateBookAvailableCount(bookId, availableCount);
        } catch (InputMismatchException e) {
            showAlert("Invalid input. Try again.");
            updateAvailableCount();
        }
    }

    public void updateVolume() {
        try {
            System.out.print("Enter Book id to update");
            long bookId = sc.nextLong();
            System.out.print("Enter the volume: ");
            int volume = sc.nextInt();
            bookModel.updateBookVolume(bookId, volume);
        }catch (InputMismatchException e) {
            showAlert("Invalid input. Try again.");
            updateAvailableCount();
        }
    }

    private void updateEdition() {
        try {
            System.out.print("Enter book id to update");
            long bookId = sc.nextLong();
            sc.nextLine();
            System.out.print("Enter the new edition: ");
            String edition = sc.nextLine();
            bookModel.updateBookEdition(bookId, edition);
        } catch (InputMismatchException e) {
            showAlert("Invalid input. Try again.");
            updateEdition();
        }
    }


    public void viewBook() {
        sc.nextLine();
        System.out.print("Enter book name");
        String bookName = sc.nextLine();
        bookModel.findBook(bookName.toUpperCase());
    }

    public void showAlert(String alert) {
        System.out.print(alert);
    }

    public void showBook(List<Book> bookList) {

        for(Book book : bookList) {
            System.out.println("Name: " + book.getName());
            System.out.println("\tID: " + book.getId());
            System.out.println("\tWritten by: " + book.getAuthor());
            System.out.println("\tPublished by: " + book.getPublication());
            System.out.println("\tGenre: " + book.getJournal());
            System.out.println("\tNo of book available: " + book.getAvailableCount());
            System.out.println();
        }
    }

}
