module Library.Management {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires com.google.gson;

    opens com.sire.librarymanagement;
    opens com.sire.librarymanagement.domain;
    opens com.sire.librarymanagement.model;
    opens com.sire.librarymanagement.settings;
    opens com.sire.librarymanagement.ui.addbook;
    opens com.sire.librarymanagement.ui.addmember;
    opens com.sire.librarymanagement.ui.listbook;
    opens com.sire.librarymanagement.ui.listmember;
}