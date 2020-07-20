/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.ui.listbook;

import com.sire.librarymanagement.domain.Book;
import com.sire.librarymanagement.model.LibraryManagementModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class BookListController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="FXML variables">
    @FXML
    private AnchorPane bookPane;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, Boolean> availabilityColumn;
//</editor-fold>

    ObservableList<Book> bookList;

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
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void loadData() {
        LibraryManagementModel library = LibraryManagementModel.getInstance();
        bookList = FXCollections.observableArrayList(library.selectAllBook());
        bookTable.getItems().setAll(bookList);
    }

    @FXML
    private void deleteBook(ActionEvent event) {
    }

}
