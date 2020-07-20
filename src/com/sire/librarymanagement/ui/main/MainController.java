/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.ui.main;

import com.sire.librarymanagement.domain.Book;
import com.sire.librarymanagement.domain.Issue;
import com.sire.librarymanagement.domain.Member;
import com.sire.librarymanagement.model.LibraryManagementModel;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author sire
 */
public class MainController implements Initializable {

    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    @FXML
    private Text memberName;
    @FXML
    private Text memberContact;
    @FXML
    private TextField bookIDInput;
    @FXML
    private TextField memberIDInput;
    @FXML
    private TextField bookID;
    @FXML
    private ListView<String> issueDataList;

    LibraryManagementModel library;
    ObservableList<String> issueData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        library = LibraryManagementModel.getInstance();
    }

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/com/sire/librarymanagement/ui/addmember/AddMember.fxml", "Új felhasználó felvitele");
    }

    @FXML
    private void loadMember(ActionEvent event) {
        loadWindow("/com/sire/librarymanagement/ui/listmember/MemberList.fxml", "Felhasználók");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/com/sire/librarymanagement/ui/addbook/AddBook.fxml", "Új könyv felvitele");
    }

    @FXML
    private void loadBooks(ActionEvent event) {
        loadWindow("/com/sire/librarymanagement/ui/listbook/BookList.fxml", "Könyvek");
    }

    public void loadWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        try {
            int id = Integer.valueOf(bookIDInput.getText());
            Book book = library.findBook(id);
            if (book != null) {
                bookName.setText(book.getTitle());
                bookAuthor.setText(book.getAuthor());
                String status = book.isStatus() ? "Elérhető" : "Nem elérhető";
                bookStatus.setText(status);
            } else {
                clearBookCache("Nincs ilyen könyv!", "", "");
            }
        } catch (Exception e) {
            clearBookCache("Cím", "Szerző", "Státusz");
            alert(Alert.AlertType.ERROR, "Kérlek, add meg az azonosítószámot!");
        }
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        try {
            int id = Integer.valueOf(memberIDInput.getText());
            Member member = library.findMember(id);
            if (member != null) {
                memberName.setText(member.getName());
                memberContact.setText(member.getPhonenumber());
            } else {
                clearMemberCache("Nincs ilyen felhasználó!", "");
            }
        } catch (Exception e) {
            clearMemberCache("Felhasználó", "Kapcsolat");
            alert(Alert.AlertType.ERROR, "Kérlek, add meg az azonosítószámot!");
        }
    }

    private void clearBookCache(String name, String author, String status) {
        bookName.setText(name);
        bookAuthor.setText(author);
        bookStatus.setText(status);
    }

    private void clearMemberCache(String name, String contact) {
        memberName.setText(name);
        memberContact.setText(contact);
    }

    private void alert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void clearAll(Event event) {
        bookIDInput.clear();
        memberIDInput.clear();
        clearBookCache("", "", "");
        clearMemberCache("", "");
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        try {
            int bookId = Integer.valueOf(bookIDInput.getText());
            int memberID = Integer.valueOf(memberIDInput.getText());
            Issue newIssue = new Issue(bookId, memberID);
            if (bookStatus.getText().equals("Nem elérhető")) {
                alert(Alert.AlertType.WARNING, "A könyv nem kölcsönözhető!");
            } else {
                String message = "Biztos kiveszed " + bookAuthor.getText() + "-nak\n" + bookName.getText() + " című könyvét?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Kölcsönzés megerősítése");
                alert.setHeaderText(null);
                alert.setContentText(message);

                Optional<ButtonType> response = alert.showAndWait();
                if (response.get() == ButtonType.OK) {
                    if (library.addIssue(newIssue)) {
                        library.updateStatus(false, bookId);
                        alert(Alert.AlertType.INFORMATION, "A kölcsönzés sikeres!");
                    } else {
                        alert(Alert.AlertType.ERROR, "A kölcsönzés sikertelen!");
                    }
                } else {
                    alert(Alert.AlertType.ERROR, "A kölcsönzés sikertelen!");
                }
                clearBookCache("", "", "");
                clearMemberCache("", "");
                bookIDInput.setText("");
                memberIDInput.setText("");
            }
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR, "Kérlek addj meg könyvet és felhasználót!");
        }
    }

    @FXML
    private void loadBookInfo2(ActionEvent event) {
        issueData.clear();
        issueDataList.getItems().clear();
        try {
            int bookId = Integer.valueOf(bookID.getText());

            Issue selectedIssue = library.findIssue(bookId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            issueData.add("A kölcsönzés ideje: " + selectedIssue.getIssueDate().format(formatter));
            issueData.add("A megújítások száma: " + String.valueOf(selectedIssue.getRenewCount()));

            Book selectedBook = library.findBook(bookId);
            issueData.add("A könyv adatai:");
            issueData.add("Azonosítószám: " + String.valueOf(selectedBook.getId()));
            issueData.add("Cím: " + String.valueOf(selectedBook.getTitle()));
            issueData.add("Szerző: " + String.valueOf(selectedBook.getAuthor()));
            issueData.add("Kiadó: " + String.valueOf(selectedBook.getPublisher()));
            issueData.add("ISBN: " + String.valueOf(selectedBook.getIsbn()));
            String status = selectedBook.isStatus() ? "Elérhető" : "Nem elérhető";
            issueData.add("Státusz: " + status);

            Member selectedMember = library.findMember(selectedIssue.getMemberID());
            issueData.add("A felhasználó adatai:");
            issueData.add("Azonosítószám: " + String.valueOf(selectedMember.getId()));
            issueData.add("Név: " + String.valueOf(selectedMember.getName()));
            issueData.add("Telefonszám: " + String.valueOf(selectedMember.getPhonenumber()));
            issueData.add("Email: " + String.valueOf(selectedMember.getEmail()));

            issueDataList.getItems().setAll(issueData);

        } catch (Exception e) {
            bookID.clear();
            alert(Alert.AlertType.ERROR, "A könyv nem létezik, vagy még nincs kölcsönözve!");
        }

    }

    @FXML
    private void renew(ActionEvent event) {
        int bookId = Integer.valueOf(bookID.getText());
        library.updateIssue(bookId);
    }

    @FXML
    private void loadSubmission(ActionEvent event) {
        int bookId = Integer.valueOf(bookID.getText());
        Book selectedBook = library.findBook(bookId);
        if (selectedBook.isStatus()) {
            alert(Alert.AlertType.ERROR, "A könyv még nincs kivéve!");
            bookID.clear();
        } else {
            String message = "Biztos visszaadod " + selectedBook.getAuthor() + "-nak\n" + selectedBook.getTitle() + " című könyvét?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Visszavétel megerősítése");
            alert.setHeaderText(null);
            alert.setContentText(message);

            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                library.deleteIssue(bookId);
                library.updateStatus(true, bookId);
                alert(Alert.AlertType.INFORMATION, "A könyv sikeresen leadva!");
            } else {
                alert(Alert.AlertType.ERROR, "A visszaadás sikertelen!");
            }

            issueData.clear();
            issueDataList.getItems().clear();
            bookID.clear();
        }
    }
}
