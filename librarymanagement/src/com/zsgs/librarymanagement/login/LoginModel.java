package com.zsgs.librarymanagement.login;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.useraccount.UserAccountView;

public class LoginModel {
    private LoginView loginView;


    LoginModel(LoginView loginView) {
        this.loginView = loginView;
    }

    public void validateUser(String userName, String password) {
        if(LibraryDatabase.getInstance().isUser(userName)) {
            if(LibraryDatabase.getInstance().passwordMatch(userName, password)) {
                int role = LibraryDatabase.getInstance().getRole(userName);
                if(role == 0) {
                    loginView.onSuccess(userName);
                } else {
                    LibraryDatabase.getInstance().issueBookFromFile();
                    LibraryDatabase.getInstance().bookFromFile();
                    LibraryDatabase.getInstance().userFromFile();
                    new UserAccountView().init(userName);
                }
            } else {
                loginView.showAlert("Invalid password");
            }
        } else {
            loginView.showAlert("Invalid username");
        }
    }


    public void libraryFile() {
        LibraryDatabase.getInstance().libraryFromFile();
    }

    public void getAndSave() {
        LibraryDatabase.getInstance().credentialsFromFile();
        LibraryDatabase.getInstance().createCredentials();
        LibraryDatabase.getInstance().saveCredentials();
    }

    public void saveAll() {
        LibraryDatabase.getInstance().saveAll();
    }

}
