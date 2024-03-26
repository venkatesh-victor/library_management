package com.zsgs.librarymanagement.librarysetup;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Library;


public class LibrarySetupModel {
    private LibrarySetupView librarySetupView;
    private Library library;

    public LibrarySetupModel(LibrarySetupView librarySetupView) {
        this.librarySetupView = librarySetupView;
        library = LibraryDatabase.getInstance().getLibrary();
    }

    public void startSetup(String userName) {
        if(library == null || library.getLibraryId() == null) {
            librarySetupView.initiateSetup(userName);
        } else {
            librarySetupView.onSetupComplete(library, userName);
        }
    }

    public void setupLibrary(String libraryName, String email, String address,
                             String phoneNo, String userName) {
        library = new Library();
        library.setLibraryName(libraryName);
        library.setEmailId(email);
        library.setAddress(address);
        library.setPhoneNo(phoneNo);
        library.setLibraryId(libraryName.substring(0, 3) + generateId());

        createLibrary(library, userName);
    }

    public void createLibrary(Library library2, String userName) {
        this.library = LibraryDatabase.getInstance().insertLibrary(library2);
        librarySetupView.onSetupComplete(library, userName);
    }

    private int generateId() {
        return (int) (Math.random() * 999 + 999);
    }

    public void bookFile() {
        LibraryDatabase.getInstance().bookFromFile();
    }

    public void userFile() {
        LibraryDatabase.getInstance().userFromFile();
    }

    public void saveLibrary() {
        LibraryDatabase.getInstance().saveLibrary();
    }

    public void IssueBookFile() {
        LibraryDatabase.getInstance().issueBookFromFile();
    }


}
