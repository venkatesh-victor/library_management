package com.zsgs.librarymanagement.book;

import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.datalayer.LibraryDatabase;

import java.util.List;

public class BookModel {

    private BookView bookView;

    public BookModel(BookView bookView) {
        this.bookView = bookView;
    }

    public void setupBook(String bookName, String author, String publication,
                          String edition, String journal, long bookId,
                          int availableCount, int volume) {
        if(LibraryDatabase.getInstance().getBook(bookId) == null) {
            Book book = new Book();
            book.setName(bookName.toUpperCase());
            book.setId(bookId);
            book.setAuthor(author);
            book.setPublication(publication);
            book.setEdition(edition);
            book.setJournal(journal);
            book.setAvailableCount(availableCount);
            book.setVolume(volume);
            createBook(book);
        } else {
            bookView.showAlert("The book with the given id alredy exists.");
            bookView.addBook();
        }
    }

    private void createBook(Book book) {
        LibraryDatabase.getInstance().insertBook(book);
        bookView.showAlert("Book added successfully.");
        bookView.init();
    }

    public void findBook(String bookName) {
        List<Book> bookList = LibraryDatabase.getInstance().searchBook(bookName);
        if(bookList.size() == 0) {
            bookView.showAlert("No books exist");
        } else {
            bookView.showBook(bookList);
        }
    }

    public void updateBookEdition(long bookId, String edition) {
        if(LibraryDatabase.getInstance().getBook(bookId) == null) {
            bookView.showAlert("Book does not exist");
            bookView.updateBook();
        } else {
            LibraryDatabase.getInstance().updateBookEdition(bookId, edition);
            bookView.showAlert("Book edition updated successfully");
        }
    }

    public void updateBookVolume(long bookId, int volume) {
        if(LibraryDatabase.getInstance().getBook(bookId) == null) {
            bookView.showAlert("Book does not exist");
            bookView.updateBook();
        } else {
            LibraryDatabase.getInstance().updateBookVolume(bookId, volume);
            bookView.showAlert("Book volume updated successfully");
        }
    }

    public void updateBookAvailableCount(long bookId, int availableCount) {
        if(LibraryDatabase.getInstance().getBook(bookId) == null) {
            bookView.showAlert("Book does not exist");
            bookView.updateBook();
        } else {
            LibraryDatabase.getInstance().updateBookAvailableCount(bookId, availableCount);
            bookView.showAlert("Book availabe count update successfully");
        }
    }

    public void viewBook() {
        List<Book> bookList = LibraryDatabase.getInstance().viewBooks();
        if(bookList.size() == 0) {
            bookView.showAlert("No books exist");
        } else {
            bookView.showBook(bookList);
        }
    }

    public void saveBook() {
        LibraryDatabase.getInstance().saveBook();
    }

}
