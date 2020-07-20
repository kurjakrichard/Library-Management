/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.ui.addmember;

import com.sire.librarymanagement.domain.Member;
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
public class AddMemberController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private javafx.scene.control.TextField name;
    @FXML
    private javafx.scene.control.TextField phonenumber;
    @FXML
    private javafx.scene.control.TextField email;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    LibraryManagementModel library;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        library = LibraryManagementModel.getInstance();
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
        name.setText("");
        phonenumber.setText("");
        email.setText("");
    }

    @FXML
    private void addMemberAction(ActionEvent event) {
        Member newMember = new Member(name.getText(), phonenumber.getText(), email.getText());
        if (newMember.getName().isEmpty() || newMember.getPhonenumber().isEmpty() || newMember.getEmail().isEmpty()) {
            alert(Alert.AlertType.ERROR, "Kérlek tölts ki minden mezőt!");
            return;
        }
        library.addMember(newMember);
        clearInputBookFields();
        alert(Alert.AlertType.INFORMATION, "A felhasználó sikeresen mentve!");
    }

}
