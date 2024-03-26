package com.zsgs.librarymanagement.user;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Credentials;
import com.zsgs.librarymanagement.model.User;

import java.util.List;

public class UserModel {

    private UserView userView;

    public UserModel(UserView userView) {
        this.userView = userView;
    }

    public void createUserAccount(String userName, String emailId, String password,
                                  String address, String phoneNo, boolean memberShip) {
        if(LibraryDatabase.getInstance().getUser(userName) == null) {
            User user = new User();
            user.setUserName(userName);
            user.setAddress(address);
            user.setEmailId(emailId);
            user.setPhoneNo(phoneNo);
            user.setMembership(memberShip);
            LibraryDatabase.getInstance().insertUserAccount(user);
            userView.showAlert("User account created successfully.");
            Credentials credentials = new Credentials();
            credentials.setUserName(userName);
            credentials.setPassword(password);
            credentials.setRole(1);
            LibraryDatabase.getInstance().addCredentials(credentials);
        } else {
            userView.showAlert("User Already exists");
        }
    }

    public void viewAllUser() {
        List<User> usersList = LibraryDatabase.getInstance().getAllUser();
        if(usersList.isEmpty()) {
            userView.showAlert("No users exists");
        } else {
            userView.showUserList(usersList);
        }
    }

    public void saveUserCredentials() {
        LibraryDatabase.getInstance().saveUsers();
        LibraryDatabase.getInstance().saveCredentials();
    }
}
