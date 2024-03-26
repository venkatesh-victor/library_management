package com.zsgs.librarymanagement.datalayer;

import com.google.gson.reflect.TypeToken;
import com.zsgs.librarymanagement.model.*;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LibraryDatabase {
    private static LibraryDatabase libraryDatabase;
    private List<Book> bookList = new ArrayList<>();
    private List<Credentials> credentialsList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Library> libraryList = new ArrayList<>();

    private List<IssueBookDetails> issueBookDetails = new ArrayList<>();


    public static LibraryDatabase getInstance() {
        if(libraryDatabase == null) {
            libraryDatabase = new LibraryDatabase();
            libraryDatabase.createCredentials();
        }
        return libraryDatabase;
    }

    public Library getLibrary() {
      if(libraryList.size()==0){
          return null;
      }else{
          return libraryList.get(0);
      }
    }

    public Library insertLibrary(Library library2) {
        libraryList.add(library2);
        return library2;
    }


    public void createCredentials() {
        Credentials credentials = new Credentials();
        credentials.setPassword("admin");
        credentials.setUserName("zsgs");
        if(credentialsList.isEmpty()){
            credentialsList.add(credentials);
        }

    }
    public void addCredentials(Credentials credentials) {
        credentialsList.add(credentials);
    }

    public boolean isUser(String userName) {
        for(Credentials credentials : credentialsList) {
            if(credentials.getUserName().equals(userName)) {
                return true;
            }
        }

        return false;
    }

    public int getRole(String userName) {
        for(Credentials credentials : credentialsList) {
            if(credentials.getUserName().equals(userName)) {
                return credentials.getRole();
            }
        }
        return 0;
    }

    public void insertUserAccount(User user) {
        userList.add(user);
    }

    public User getUser(String userName) {
        for(User user : userList) {
            if(user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    public List<User> getAllUser() {
        return userList;
    }

    public void insertBook(Book book) {
        bookList.add(book);
    }

    public Book getBook(long bookId) {
        for(Book book : bookList) {
            if(book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public List<Book> searchBook(String bookName) {
        List<Book> searchResult = new ArrayList<>();
        for(Book book : bookList) {
            if(book.getName().contains(bookName)) {
                searchResult.add(book);
            }
        }

        return searchResult;
    }

    public List<Book> viewBooks() {
        return bookList;
    }

    public void updateBookEdition(long bookId, String edition) {
        getBook(bookId).setEdition(edition);
    }

    public void updateBookVolume(long bookId, int volume) {
        getBook(bookId).setVolume(volume);
    }

    public void updateBookAvailableCount(long bookId, int availableCount) {
        getBook(bookId).setAvailableCount(availableCount);
    }

    public IssueBookDetails getMyBookList(String userName) {
        for(IssueBookDetails issueBookDetails1 : issueBookDetails) {
            if(issueBookDetails1.getUserName().equals(userName)) {
                return issueBookDetails1;
            }
        }
        return null;
    }

    public boolean passwordMatch(String userName, String password) {
        if(getPassword(userName) != null) {
            return getPassword(userName).equals(password);
        }

        return false;
    }

    private String getPassword(String userName) {
        for(Credentials credentials : credentialsList) {
            if(credentials.getUserName().equals(userName))
                return credentials.getPassword();
        }

        return null;
    }

    public void insertIssuedBooks(IssueBookDetails issueBookDetails) {
        this.issueBookDetails.add(issueBookDetails);
    }

    public List<IssueBookDetails> getIssuedBookList() {
        return this.issueBookDetails;
    }

    public void removeIssuedBook(IssueBookDetails issueBookDetails) {
        this.issueBookDetails.remove(issueBookDetails);
    }

    public void saveUsers() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(userList);
        try(FileWriter writer = new FileWriter("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\user.json")) {
           writer.write(jsonString);
           writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBook() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(bookList);

        try(FileWriter writer = new FileWriter("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\book.json")) {
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveIssueBook() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(issueBookDetails);
        for(IssueBookDetails issueBookDetails1 : issueBookDetails) {
            issueBookDetails1.getUserName();
        }

        try(FileWriter writer = new FileWriter("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\issuebook.json")) {
            writer.write(jsonString);
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCredentials() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(credentialsList);

        try(FileWriter writer = new FileWriter("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\credentials.json")) {
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLibrary() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(libraryList);

        try(FileWriter writer = new FileWriter("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\library.json")) {
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> loadUsers() {
        Gson gson = new Gson();
        List<User> loadedList = new ArrayList<>();

        try {
            File file = new File("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\user.json");
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\user.json")) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }

            String jsonString = sb.toString();
            if(jsonString.isEmpty()) {
                return loadedList;
            }

            Type type = new TypeToken<List<User>>() {}.getType();
            loadedList = gson.fromJson(jsonString, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedList;
    }

    private  List<Book> loadBooks() {
        Gson gson = new Gson();
        List<Book> loadedList = new ArrayList<>();
        try {
            File file = new File("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\book.json");
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\book.json")) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }

            String jsonString = sb.toString();
            if (jsonString.isEmpty()) {
                return loadedList;
            }

            Type type = new TypeToken<List<Book>>() {
            }.getType();
            loadedList = gson.fromJson(jsonString, type);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return loadedList;
    }

    private  List<Credentials> loadCredentials() {
        Gson gson = new Gson();
        List<Credentials> loadedList = new ArrayList<>();
//        System.out.println(123);
        try {
            File file = new File("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\credentials.json");
            if(!file.exists()) {

                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try(FileReader reader = new FileReader("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\credentials.json")) {
            StringBuilder sb = new StringBuilder();
            int c;

            while((c = reader.read()) != -1) {
                sb.append((char) c);
//                System.out.println(true);1

            }

            String jsonString = sb.toString();
            if(jsonString.isEmpty()) {
                return loadedList;
            }
//            System.out.println("ffffff");
            Type type = new TypeToken<List<Credentials>>(){}.getType();
            loadedList = gson.fromJson(jsonString, type);
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println(loadedList.size());

        return loadedList;
    }

    private List<IssueBookDetails> loadIssueBooks() {
        Gson gson = new Gson();
        List<IssueBookDetails> loadedList = new ArrayList<>();

        try {
            File file = new File("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\issuebook.json");
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        try(FileReader reader = new FileReader("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\issuebook.json")) {
            StringBuilder sb = new StringBuilder();
            int c;
            while((c = reader.read()) != -1) {
                sb.append((char) c);
            }

            String jsonString = sb.toString();
            if(jsonString.isEmpty()) {
                return loadedList;
            }

            Type type = new TypeToken<List<IssueBookDetails>>() {}.getType();
            loadedList = gson.fromJson(jsonString, type);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return loadedList;

    }

    private static List<Library> loadLibrary() {
        Gson gson = new Gson();
        List<Library> loadedList = new ArrayList<>();
        try {
            File file = new File("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\library.json");
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("C:\\console\\librarymanagement\\src\\com\\zsgs\\librarymanagement\\datalayer\\library.json")) {
            StringBuilder sb = new StringBuilder();
            int c;
            while((c = reader.read()) != -1) {
                sb.append((char) c);
            }

            String jsonString = sb.toString();
            if(jsonString.isEmpty()) {
                return loadedList;
            }

            Type type = new TypeToken<List<Library>>() {}.getType();
            loadedList = gson.fromJson(jsonString, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedList;
    }
    public void libraryFromFile() {
        this.libraryList = loadLibrary();
    }

    public void bookFromFile() {
        this.bookList = loadBooks();
    }

    public void userFromFile() {
        this.userList = loadUsers();
    }

    public void issueBookFromFile() {
        this.issueBookDetails = loadIssueBooks();
    }

    public void credentialsFromFile() {
        credentialsList = loadCredentials();
    }

    public void saveAll() {
        saveIssueBook();
        saveBook();
        saveCredentials();
        saveUsers();
        saveLibrary();
    }

    public void getAllData() {
        issueBookFromFile();
        bookFromFile();
        userFromFile();
        libraryFromFile();
    }


}
