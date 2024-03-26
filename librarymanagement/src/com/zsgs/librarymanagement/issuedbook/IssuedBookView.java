package com.zsgs.librarymanagement.issuedbook;

import com.zsgs.librarymanagement.book.BookView;
import com.zsgs.librarymanagement.user.UserView;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.IssueBookDetails;
import com.zsgs.librarymanagement.model.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class IssuedBookView {
    Scanner sc = new Scanner(System.in);
    private IssuedBookModel issuedBookModel;

    public IssuedBookView () {
        this.issuedBookModel = new IssuedBookModel(this);
    }

    public void init() {
        try {
            while (true) {
                System.out.println("\n1. View all books\n2.View Issued books");
                System.out.println("3. Go back\nEnter you choice");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1: new BookView().viewBook(); break;
                    case 2: viewIssuedBookList(); break;
                    case 3: return;
                    default: showAlert("Invalid choice. Try again."); init();
                }
            }
        } catch (InputMismatchException e) {
            showAlert("Invalid input. Try again");
            init();
        }
    }

    public void returnIssuedBook() {
        sc.nextLine();
        System.out.print("Enter your user name: ");
        String userName = sc.nextLine();
        returnBook(userName);
    }

    public void issuedBook(String userName) {
        issuedBookModel.isUserExist(userName);
    }

    public void wantBook(String userName) {
        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();
        issuedBookModel.isBookExist(userName, bookName);
    }

    public void checkAgain(String userName) {
        System.out.println("Do you want to get another book?");
        System.out.println("(type 'yes' or 'no')");
        String choice = sc.nextLine();

        if(choice.equalsIgnoreCase("yes")) {
            wantBook(userName);
        } else if(choice.equalsIgnoreCase("no")) {
            return;
        } else {
            System.out.println("Invali choice. Try again");
            checkAgain(userName);
        }
    }

    public void createUserAccount() {
        System.out.println("Do you want to create new Accont?");
        System.out.println("(type 'yes' or 'no')");
        String choice = sc.nextLine();

        if(choice.equalsIgnoreCase("yes")) {
            new UserView().initAdd();
        } else if(choice.equalsIgnoreCase("no")) {
            return;
        } else {
            System.out.println("Invalid choice. Try again.");
            createUserAccount();
        }
    }

    public void showIssuedBooks(List<IssueBookDetails> issueBookDetails) {
        int n = 1;
        System.out.println();
        for(IssueBookDetails issueBookDetails1 : issueBookDetails) {
            System.out.println("Borrowed by: " + issueBookDetails1.getUserName());
            for(Book book : issueBookDetails1.getBookList()) {
                System.out.println(n++ + "." + book.getName() + "\n");
                System.out.println();
            }
            n = 1;
        }
    }


    public void checkReturn(String userName) {
        System.out.println("Do you want to return the book?");
        System.out.println("(type 'yes' or 'no')");

        String choice = sc.nextLine();

        if(choice.equalsIgnoreCase("yes")) {
            returnBook(userName);
        } else if(choice.equalsIgnoreCase("no")) {
            return;
        } else {
            showAlert("Invalid choice. Try again");
            checkAgain(userName);
        }
    }

    public void returnBook(String userName) {
        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();
        issuedBookModel.returnBook(userName, bookName);
    }

    public void viewIssuedBookList() {
        issuedBookModel.viewIssuedBooks();
    }

    public void showAlert(String alert) {
        System.out.println(alert);
    }
}
