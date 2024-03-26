package com.zsgs.librarymanagement.issuedbook;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.IssueBookDetails;
import com.zsgs.librarymanagement.model.Library;
import com.zsgs.librarymanagement.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
public class IssuedBookModel {
    private IssuedBookView issuedBookView;

    public IssuedBookModel(IssuedBookView issuedBookView) {
        this.issuedBookView = issuedBookView;
    }

    public void isUserExist(String userName) {
        User user = LibraryDatabase.getInstance().getUser(userName);
        if(user.getBookCount() < 3) {
            issuedBookView.wantBook(userName);
        } else {
            issuedBookView.showAlert("You have reached the limit to borrow more books.");
            issuedBookView.showAlert("Return atlease one book first to borrow a new one.");
            issuedBookView.returnBook(userName);
        }
    }

    public void isBookExist(String userName, String bookName) {
        List<Book> bookList  = LibraryDatabase.getInstance().searchBook(bookName.toUpperCase());
        if(bookList.isEmpty()) {
            issuedBookView.showAlert("Book does not exist");
            issuedBookView.checkAgain(userName);
        } else {
            for(Book book : bookList) {
                if(bookName.toUpperCase().equalsIgnoreCase(book.getName()) && book.getAvailableCount() > 0) {
                    issueBook(userName, book);
                    book.setAvailableCount(book.getAvailableCount() - 1);
                } else {
                    issuedBookView.showAlert("Book does not exist now");
                    issuedBookView.checkAgain(userName);
                }
            }
        }
    }

    private void issueBook(String userName, Book book) {
        IssueBookDetails issueBookDetails = LibraryDatabase.getInstance().getMyBookList(userName);
        if(issueBookDetails == null) {
            issueBookDetails = new IssueBookDetails();
            issueBookDetails.getBookList().add(book);
            issueBookDetails.setUserName(userName);
            LibraryDatabase.getInstance().insertIssuedBooks(issueBookDetails);
        } else {
            issueBookDetails.getBookList().add(book);
        }

        User user = LibraryDatabase.getInstance().getUser(userName);
        LibraryDatabase.getInstance().getUser(userName).setBookCount(user.getBookCount() + 1);
        issuedBookView.showAlert("Book issued successfully");
    }

    public void returnBook(String userName, String bookName) {
        if(LibraryDatabase.getInstance().getUser(userName).getBookCount() == 0) {
            issuedBookView.showAlert("This user has no borrowed books to return");
            issuedBookView.checkReturn(userName);
            return;
        }
        boolean ok = false;
        List<Book> bookList = LibraryDatabase.getInstance().searchBook(bookName.toUpperCase());
        Book book = null;
        for(Book book1 : bookList) {
            if(book1.getName().equalsIgnoreCase(bookName.toUpperCase())) {
                book = book1;
            }
        }

        if(book == null) {
            issuedBookView.showAlert("Book does not exist in the issued book list");
            issuedBookView.checkReturn(userName);
            return;
        }
        IssueBookDetails issueBookDetails = LibraryDatabase.getInstance().getMyBookList(userName);
        if(issueBookDetails != null) {
            List<Book> books = issueBookDetails.getBookList();
            for(Book book2 : books) {
                if(book2.getId() == book.getId()) {
                    book.setAvailableCount(book.getAvailableCount() + 1);
                    User user = LibraryDatabase.getInstance().getUser(issueBookDetails.getUserName());
                    LibraryDatabase.getInstance().getUser(issueBookDetails.getUserName()).setBookCount(user.getBookCount() - 1);

                    if(user.getBookCount() == 0) {
                        LibraryDatabase.getInstance().removeIssuedBook(issueBookDetails);
                    }
                    issuedBookView.showAlert("Book returned successfully");
                    ok = true;
                    break;
                }
            }

        }

        if(issueBookDetails != null) {
            issueBookDetails.getBookList().remove(book);
        }

        if(!ok) {
            issuedBookView.showAlert("Book name does not match");
            issuedBookView.checkReturn(userName);
        }

    }

    public void viewIssuedBooks() {
        List<IssueBookDetails> issuedBookDetails = LibraryDatabase.getInstance().getIssuedBookList();
        if(issuedBookDetails.isEmpty()) {
            issuedBookView.showAlert("No books has been issued.");
        } else {
            issuedBookView.showIssuedBooks(issuedBookDetails);
        }
    }

}
