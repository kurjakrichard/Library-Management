/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.model;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.sire.librarymanagement.domain.Book;
import com.sire.librarymanagement.domain.Issue;
import com.sire.librarymanagement.domain.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author balza
 */
public final class LibraryManagementModel {

//<editor-fold defaultstate="collapsed" desc="Class variables">
    private static LibraryManagementModel handler = null;
    private final String FILENAME = "library.db";
    private final String URL = "jdbc:sqlite:" + FILENAME;
    private Connection conn = null;
    private final String BOOKTABLE = "CREATE TABLE IF NOT EXISTS books (\n"
            + "    id integer PRIMARY KEY,\n"
            + "    title text NOT NULL,\n"
            + "    author text NOT NULL,\n"
            + "    publisher text,\n"
            + "    isbn text,\n"
            + "    status boolean\n"
            + ");";
    private final String MEMBERTABLE = "CREATE TABLE IF NOT EXISTS members (\n"
            + "    id integer PRIMARY KEY,\n"
            + "    name text NOT NULL,\n"
            + "    phonenumber text NOT NULL,\n"
            + "    email text\n"
            + ");";
    private final String ISSUETABLE = "CREATE TABLE IF NOT EXISTS issues (\n"
            + "    bookID integer PRIMARY KEY,\n"
            + "    memberID integer NOT NULL,\n"
            + "    issueDate text NOT NULL,\n"
            + "    renew_count integer default 0,\n"
            + "    FOREIGN KEY (bookID) REFERENCES books (id)\n"
            + "    FOREIGN KEY (memberID) REFERENCES members (id)\n"
            + ");";
//</editor-fold>

    private LibraryManagementModel() {
        connect(URL);
        createNewTableIfNotExist(BOOKTABLE);
        createNewTableIfNotExist(MEMBERTABLE);
        createNewTableIfNotExist(ISSUETABLE);
    }

    /**
     *Prevent to run more than one database connection
     * @return
     */
    public static LibraryManagementModel getInstance() {
        if (handler == null) {
            handler = new LibraryManagementModel();
        }
        return handler;
    }

    /**
     * Create connection to the database
     */
    private void connect(String url) {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("Hiba történt a kapcsolat létrehozásakor!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the library database
     *
     * @param sql
     */
    public final void createNewTableIfNotExist(String sql) {
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adatbázis tábla létrehozásakor!");
        }
    }

    /**
     * Insert a new row into the book table
     *
     * @param book
     */
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, publisher, isbn, status) VALUES(?,?,?,?,?)";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthor());
                pstmt.setString(3, book.getPublisher());
                pstmt.setString(4, book.getIsbn());
                pstmt.setBoolean(5, book.isStatus());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat felvitelekor!");
        }
    }

    /**
     * Insert a new row into the book table
     *
     * @param member
     */
    public void addMember(Member member) {
        String sql = "INSERT INTO members (name, phonenumber, email) VALUES(?,?,?)";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, member.getName());
                pstmt.setString(2, member.getPhonenumber());
                pstmt.setString(3, member.getEmail());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat felvitelekor!");
        }
    }

    /**
     *Insert a new row into the issues table
     * @param issue
     * @return
     */
    public boolean addIssue(Issue issue) {
        String sql = "INSERT INTO issues (bookID, memberID, issueDate) VALUES(?, ?, ?)";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                pstmt.setInt(1, issue.getBookID());
                pstmt.setInt(2, issue.getMemberID());
                pstmt.setString(3, issue.getIssueDate().format(formatter));
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            System.out.println("Hiba történt az adat felvitelekor!");
            return false;
        }
        return true;
    }

    /**
     * select all rows in the books table
     *
     * @return
     */
    public ArrayList<Book> selectAllBook() {
        String sql = "SELECT * FROM books";
        ArrayList<Book> books = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                Book actualBook = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("publisher"),
                        rs.getString("isbn"), rs.getBoolean("status"));
                books.add(actualBook);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    /**
     * select all rows in the members table
     *
     * @return
     */
    public ArrayList<Member> selectAllMember() {
        String sql = "SELECT * FROM members";
        ArrayList<Member> members = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                Member actualMember = new Member(rs.getInt("id"), rs.getString("name"), rs.getString("phonenumber"), rs.getString("email"));
                members.add(actualMember);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return members;
    }

    /**
     * select a book from books where id 
     *
     * @param id
     * @return
     */
    public Book findBook(int id) {
        String sql = "SELECT * FROM books where id = ?";
        Book selectedBook = null;
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                // loop through the result set
                while (rs.next()) {
                    selectedBook = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("publisher"), rs.getString("isbn"), rs.getBoolean("status"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            return selectedBook = null;
        }
        return selectedBook;
    }

    /**
     * select a member from members where id 
     * @param id
     * @return
     */
    public Member findMember(int id) {
        String sql = "SELECT * FROM members where id = ?";
        Member selectedMember = null;
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                // loop through the result set
                while (rs.next()) {
                    selectedMember = new Member(rs.getInt("id"), rs.getString("name"), rs.getString("phonenumber"), rs.getString("email"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            return selectedMember = null;
        }
        return selectedMember;
    }

    /**
     * select an issue from issues where id 
     * @param id
     * @return
     */
    public Issue findIssue(int id) {
        String sql = "SELECT * FROM issues where bookID = ?";
        Issue selectedIssue = null;
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                // loop through the result set
                while (rs.next()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                    LocalDate issueDate = LocalDate.parse(rs.getString("issueDate"), formatter);
                    selectedIssue = new Issue(rs.getInt("bookID"), rs.getInt("memberID"), issueDate, rs.getInt("renew_count"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            return selectedIssue = null;
        }
        return selectedIssue;
    }

    /**
     *  Update selected book status 
     * @param status
     * @param bookID
     */
    public void updateStatus(boolean status, int bookID) {
        String sql = "UPDATE books SET status = ? WHERE id = ?";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, status);
                pstmt.setInt(2, bookID);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat módosításakor!");
        }
    }

    /**
     * delete selected issue from issues
     * @param bookID
     */
    public void deleteIssue(int bookID) {
        String sql = "DELETE FROM issues WHERE bookID = ?";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, bookID);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat módosításakor!");
        }
    }

    /**
     * update selected issue
     * @param bookId
     */
    public void updateIssue(int bookId) {
        String sql = "UPDATE issues SET issueDate = ?, renew_count = renew_count + 1 WHERE bookID = ?";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                pstmt.setString(1, LocalDate.now().format(formatter));
                pstmt.setInt(2, bookId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat módosításakor!");
        }
    }
}
