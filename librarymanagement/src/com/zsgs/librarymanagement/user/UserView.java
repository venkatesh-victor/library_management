package com.zsgs.librarymanagement.user;

import com.zsgs.librarymanagement.model.User;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
public class UserView {
    Scanner sc = new Scanner(System.in);
    private UserModel userModel;
    public UserView() {
        userModel = new UserModel(this);
    }

    public void init() {
        try {
            while(true) {
                System.out.println();
                System.out.println("1.Add User\n2.View User.\n3.Return to home page");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1: initAdd(); break;
                    case 2: viewUser(); break;
                    case 3:
                        userModel.saveUserCredentials();
                        return;
                    default:System.out.println("Invalid choice. Try again."); init();
                }
            }
        }catch (InputMismatchException e) {
            showAlert("Invalid input. Try again.");
            init();
        }
    }

    public void initAdd() {
        try {
            sc.nextLine();
            System.out.println("Enter the following user details:-");
            System.out.print("Enter the user name with alpha numeric character: ");
            String userName = sc.nextLine();
            System.out.print("Enter user email id: ");
            String userEmail = sc.nextLine();
            System.out.print("Enter user password: ");
            String password = sc.nextLine();
            System.out.print("Enter user address: ");
            String address = sc.nextLine();
            System.out.print("Enter user phone number: ");
            String phoneNo = sc.nextLine();

            userModel.createUserAccount(userName, userEmail, password, address, phoneNo, true);
        } catch (InputMismatchException e) {
            showAlert("Invalid details. Try again.");
            initAdd();
        }
    }

    public void viewUser() {
        userModel.viewAllUser();
    }


    public void showAlert(String alert) {
        System.out.println(alert);
    }

    public void showUserList(List<User> allUser) {
        System.out.println("-----------------------------------------------------");
        for(User user : allUser) {
            System.out.println("Name: " + user.getUserName());
            System.out.println("\tEmail: " + user.getEmailId());
            System.out.println("\tPhone No: " + user.getPhoneNo());
            System.out.println("\tAddress: " + user.getAddress());
            System.out.println("\tNo of borrowed books: " + user.getBookCount());
        }
        System.out.println("-----------------------------------------------------");
    }
}
