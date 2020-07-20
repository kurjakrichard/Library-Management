/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.settings;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author sire
 */
public class SettingsController implements Initializable {

    @FXML
    private javafx.scene.control.TextField days;
    @FXML
    private javafx.scene.control.TextField fine;
    @FXML
    private javafx.scene.control.TextField username;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveSettings(ActionEvent event) {
    }

    @FXML
    private void cancelSettings(ActionEvent event) {
    }
    
}
