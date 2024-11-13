module com.eiman.ejs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.eiman.ejs.model to javafx.base;
    opens com.eiman.ejs.controller to javafx.fxml;

    exports com.eiman.ejs;
}
