module com.example.systemusercrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires java.sql;

    opens com.example.systemusercrud.controller to javafx.fxml;
    opens com.example.systemusercrud.model to javafx.base;

    exports com.example.systemusercrud.view;

    opens com.example.systemusercrud.view to javafx.fxml;
}
