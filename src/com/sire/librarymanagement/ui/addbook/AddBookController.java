/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.ui.addbook;

import com.sire.librarymanagement.domain.Book;
import com.sire.librarymanagement.model.LibraryManagementModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sire
 */
public class AddBookController implements Initializable {

    @FXML
    private javafx.scene.control.TextField title;
    @FXML
    private javafx.scene.control.TextField author;
    @FXML
    private javafx.scene.control.TextField publisher;
    @FXML
    private javafx.scene.control.TextField isbn;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane mainPane;
    
    LibraryManagementModel library;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        library = LibraryManagementModel.getInstance();
    }

    @FXML
    private void addBookAction(ActionEvent event) {

        Book newBook = new Book(title.getText(), author.getText(), publisher.getText(), isbn.getText());

        if (newBook.getTitle().isEmpty() || newBook.getAuthor().isEmpty() || newBook.getPublisher().isEmpty() || newBook.getIsbn().isEmpty()) {
            alert(Alert.AlertType.ERROR, "Kérlek tölts ki minden mezőt!");
            return;
        }
        library.addBook(newBook);
        clearInputBookFields();
        alert(Alert.AlertType.INFORMATION, "A könyv sikeresen mentve!");
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    private void alert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputBookFields() {
        title.setText("");
        author.setText("");
        publisher.setText("");
        isbn.setText("");
    }

}
