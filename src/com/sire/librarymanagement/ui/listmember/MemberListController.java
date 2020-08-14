/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.ui.listmember;

import com.sire.librarymanagement.domain.Member;
import com.sire.librarymanagement.model.LibraryManagementModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sire
 */
public class MemberListController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="FXML variables">
    @FXML
    private AnchorPane bookPane;
    @FXML
    private TableView<Member> bookTable;
    @FXML
    private TableColumn<Member, Integer> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> phonenumberColumn;
    @FXML
    private TableColumn<Member, String> emailColumn;
//</editor-fold>

    ObservableList<Member> memberList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();

    }

    private void initCol() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phonenumberColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadData() {
        LibraryManagementModel library = LibraryManagementModel.getInstance();
        memberList = FXCollections.observableArrayList(library.selectAllMember());
        bookTable.getItems().setAll(memberList);

    }

}
