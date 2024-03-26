package com.zsgs.librarymanagement.useraccountcreation;

import com.zsgs.librarymanagement.useraccount.UserAccountModel;

import java.util.Scanner;

public class UserAccountCreationView {
    Scanner sc = new Scanner(System.in);
    private UserAccountCreationModel userAccountCreationModel;

    public UserAccountCreationView() {
        userAccountCreationModel = new UserAccountCreationModel(this);
    }

    public void init() {
        getUserDetails();
    }

    public void getUserDetails() {
        System.out.println("Enter User details to create account:- ");

        System.out.print("Enter user name: ");
        String userName = sc.nextLine();

        System.out.print("Enter user email id: ");
        String emailId = sc.nextLine();

        System.out.print("Enter user address: ");
        String address = sc.nextLine();

        System.out.print("Enter user phone numeber");
        String phoneNo = sc.nextLine();

        userAccountCreationModel.createUserAccount(userName, emailId,
                address, phoneNo, true);
    }

    public void showAlert(String alert) {
        System.out.println(alert);
    }

    public void checkAgainAdd() {
        System.out.println("Do you want to create account?");
        System.out.println("(type 'yes' or 'no')");

        String choice = sc.nextLine();
        if(choice.equalsIgnoreCase("yes")) {
            getUserDetails();
        } else if(choice.equalsIgnoreCase("no")) {
            System.out.println("Add another feature");
        } else {
            System.out.println("Invlaid choice. Try again.");
            checkAgainAdd();
        }
    }
}
