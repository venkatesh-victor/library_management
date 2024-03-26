package com.zsgs.librarymanagement.useraccount;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.IssueBookDetails;

public class UserAccountModel {
    private UserAccountView userAccountView;

    public UserAccountModel(UserAccountView userAccountView) {
        this.userAccountView = userAccountView;
    }

    public void viewBooks(String userName) {
        IssueBookDetails issueBookDetails = LibraryDatabase.getInstance().getMyBookList(userName);
        if(issueBookDetails == null) {
            userAccountView.showAlert("Your book list is empty");
        } else {
            userAccountView.showBook(issueBookDetails);
        }
    }

    public void IssuBookUserBookFile() {
        LibraryDatabase.getInstance().issueBookFromFile();
        LibraryDatabase.getInstance().bookFromFile();
        LibraryDatabase.getInstance().userFromFile();
    }

    public void saveIssueBookUserBooks() {
        LibraryDatabase.getInstance().saveIssueBook();
        LibraryDatabase.getInstance().saveBook();
        LibraryDatabase.getInstance().saveUsers();
    }
}
