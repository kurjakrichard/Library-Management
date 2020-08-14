/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.settings;

import com.sire.librarymanagement.domain.Preferences;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final String CONFIG_FILE = "config.txt";
    Preferences pref = new Preferences();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }

    @FXML
    private void saveSettings(ActionEvent event) {
    }

    @FXML
    private void cancelSettings(ActionEvent event) {
    }

    private void initConfig() {
        Writer writer = null;
        try {
            writer = new FileWriter(CONFIG_FILE);
            Gson gson = new Gson();
            gson.toJson(pref, writer);
        } catch (IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Preferences getPreferences() {
        Preferences pref = new Preferences();
        Gson gson = new Gson();
        try {      
            pref = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (IOException ex) {
            initConfig();
        }
        return pref;
    }

    private void initDefaultValues() {
        Preferences pref = getPreferences();
        days.setText(String.valueOf(pref.getnDaysWithoutFine()));
        fine.setText(String.valueOf(pref.getFinePerDay()));
        username.setText(String.valueOf(pref.getUsername()));
        password.setText(String.valueOf(pref.getPassword()));
    }
}
