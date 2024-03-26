package com.zsgs.librarymanagement.useraccountcreation;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.User;

public class UserAccountCreationModel {
    private UserAccountCreationView userAccountCreationView;

    public UserAccountCreationModel(UserAccountCreationView userAccountCreationView) {
        this.userAccountCreationView = userAccountCreationView;
    }

    public void createUserAccount(String userName, String emailId,
                                  String address, String phoneNo,
                                  boolean membership) {
        User user = new User();
        user.setUserName(userName);
        user.setAddress(address);
        user.setEmailId(emailId);
        user.setPhoneNo(phoneNo);
        user.setMembership(membership);
        LibraryDatabase.getInstance().insertUserAccount(user);
        userAccountCreationView.showAlert("User account created successfully");
    }
}
