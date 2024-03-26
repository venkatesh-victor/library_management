package com.zsgs.librarymanagement.login;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.zsgs.librarymanagement.LibraryManagement;
import com.zsgs.librarymanagement.librarysetup.LibrarySetupView;

public class LoginView {
    Scanner sc = new Scanner(System.in);
    private LoginModel loginModel;

    public LoginView() {
        loginModel = new LoginModel(this);
    }


    public void init(){
        try {
            System.out.println("--- " + LibraryManagement.getInstance().getAppName() + " ---");
            System.out.println("Version - " + LibraryManagement.getInstance().getAppversion());

            System.out.println("1.Login\n2.Exit\nEnter the choice");

            int choice = sc.nextInt();
            sc.nextLine();

            while(true) {
                switch (choice) {
                    case 1:
                        loginModel.getAndSave();
                        getLoginDetails();
                        break;
                    case 2:
                        System.out.println("\t\t\t---Thanks for using " + LibraryManagement.getInstance().getAppName() + "---");
                        System.exit(0);
                    default:
                        showAlert("Invalid choice, Try again.");
                        init();
                        break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Try again.");
            checker();
        }
    }

    private void checker() {
        sc.nextLine();
        System.out.println("Do you want to try again? \nYes\nNo");
        String choice = sc.nextLine();
        if(choice.equalsIgnoreCase("yes")) init();
        else if(choice.equalsIgnoreCase("no")) {
            System.out.println("\t\t\t---Thanks for using " + LibraryManagement.getInstance().getAppName() + "---");
            return;
        } else {
            System.out.println("Invalid choice. Try again.");
            checker();
        }
    }

    public void getLoginDetails() {
       System.out.print("Enter the username: ");
       String userName = sc.nextLine();
       System.out.print("Enter the password: ");
       String password = sc.nextLine();
       loginModel.validateUser(userName, password);
    }

    public void onSuccess(String userName) {
        System.out.flush();
        System.out.println("\n\nLogin Successful...\n\n--- Welcome to " + LibraryManagement.getInstance().getAppName()
            + " - v" + LibraryManagement.getInstance().getAppversion() + "----");
        loginModel.libraryFile();
        new LibrarySetupView().init(userName);
    }

    public void showAlert(String alert) {
        System.out.println(alert);
        checkForLogin();
    }

    public void checkForLogin() {
        System.out.println("Do you want to try again? \nYes\nNo");
        String choice = sc.nextLine();

        if(choice.equalsIgnoreCase("yes")) getLoginDetails();
        else if(choice.equalsIgnoreCase("no")) {
            System.out.println("Thank you.");
            System.exit(0);
        }
        else {
            System.out.println("Invalid choice. Try again");
            checkForLogin();
        }
    }
}
