package com.zsgs.librarymanagement.librarysetup;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Library;
import com.zsgs.librarymanagement.login.LoginView;
import com.zsgs.librarymanagement.book.BookView;
import com.zsgs.librarymanagement.user.UserView;
import com.zsgs.librarymanagement.issuedbook.IssuedBookView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LibrarySetupView {

    Scanner sc = new Scanner(System.in);
    private LibrarySetupModel librarySetupModel;

    public LibrarySetupView() {
        librarySetupModel = new LibrarySetupModel(this);
    }

    public void init(String userName) {
        librarySetupModel.startSetup(userName);
    }

    public void initiateSetup(String userName) {
        System.out.println();

        try {
            System.out.print("Enter Library Name: ");
            String libraryName = sc.nextLine();
            System.out.print("Enter library email: ");
            String email = sc.nextLine();
            System.out.print("Enter library address: ");
            String address = sc.nextLine();
            System.out.print("Enter library phone no: ");
            String phoneNo = sc.nextLine();

            librarySetupModel.setupLibrary(libraryName, email, address, phoneNo, userName);
        } catch (InputMismatchException e) {
            showAlert("Invalid input. Try again.");
            sc.nextLine();
            initiateSetup(userName);
        }
    }

    public void onSetupComplete(Library library, String userName) {
        librarySetupModel.saveLibrary();

        try {
            System.out.println("\nLibrary setup completed");
            System.out.println(library.getLibraryName());

            while(true) {
                System.out.println("1. Manage book\n2. Manage user\n3. Manage Issued books.");
                System.out.println("5. Logout\nEnter your choice.");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        librarySetupModel.bookFile();
                        new BookView().init();
                        break;
                    case 2:
                        librarySetupModel.userFile();
                        new UserView().init();
                        break;
                    case 3:
                        librarySetupModel.IssueBookFile();
                        new IssuedBookView().init();
                        break;
                    case 5:
                        System.out.println("\nYou are logged out successfully.");
                        new LoginView().init();
                        break;
                    default:
                        System.out.println("InvalidChoice, Try again.");
                }
            }
        } catch (InputMismatchException e) {
            showAlert("Invalid input. Try again.");
            onSetupComplete(library, userName);
        }
    }


    private void showAlert(String alert) {
        System.out.println(alert);
    }

}
