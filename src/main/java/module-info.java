module com.yeocak.proje3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.yeocak.proje3 to javafx.fxml;
    exports com.yeocak.proje3;
    exports com.yeocak.proje3.ui;
    exports com.yeocak.proje3.model;
    opens com.yeocak.proje3.ui to javafx.fxml;
    exports com.yeocak.proje3.utils;
    opens com.yeocak.proje3.utils to javafx.fxml;
}