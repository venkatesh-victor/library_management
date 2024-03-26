package com.zsgs.librarymanagement.useraccount;

import com.zsgs.librarymanagement.book.BookView;
import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.issuedbook.IssuedBookView;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.IssueBookDetails;
import com.zsgs.librarymanagement.model.User;
import com.zsgs.librarymanagement.login.LoginView;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class UserAccountView {
    Scanner sc = new Scanner(System.in);
    private UserAccountModel userAccountModel;

    public UserAccountView() {
        this.userAccountModel = new UserAccountModel(this);
    }

    public void init(String userName) {
        BookView bookView = new BookView();
        IssuedBookView issuedBookView = new IssuedBookView();

        try {
            while(true) {
                System.out.println(
                        "1.View User\n2.Search Books\n3.View Books\n4.Get Book\n5.Return Book" +
                                "\n6.View My Book List\n7.Logout\nEnter your choice."
                );

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        viewUser(userName);
                        break;
                    case 2:
                        bookView.searchBook();
                        break;
                    case 3:
                        bookView.viewBook();
                        break;
                    case 4:
                        issuedBookView.issuedBook(userName);
                        break;
                    case 5:
                        issuedBookView.returnBook(userName);
                        break;
                    case 6:
                        userAccountModel.viewBooks(userName);
                        break;
                    case 7:
                        userAccountModel.saveIssueBookUserBooks();
                        new LoginView().init();
                        return;
                    default:
                        showAlert("Invalid choice. Try again.");
                        init(userName);
                }
            }
        } catch(InputMismatchException e) {
            showAlert("Invalid input. Try again.");
        }
    }
    private void viewUser(String userName) {
        User user = LibraryDatabase.getInstance().getUser(userName);
        System.out.println("Name: " + user.getUserName());
        System.out.println("\tEmail: " + user.getEmailId());
        System.out.println("\tPhone No: " + user.getPhoneNo());
        System.out.println("\tAddress: " + user.getAddress());
        System.out.println("\tNo of borrowed books: " + user.getBookCount());
        System.out.println();
    }

    public void showBook(IssueBookDetails issueBookDetails) {
        int n = 1;
        System.out.println("Name: " + issueBookDetails.getUserName());
        for(Book book : issueBookDetails.getBookList()) {
            System.out.println(n++ + "Book name: " + book.getName() + "\n");
        }
    }

    public void showAlert(String alert) {
        System.out.println(alert);
    }
}
