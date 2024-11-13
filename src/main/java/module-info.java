module com.eiman.ejs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eiman.ejs to javafx.fxml;
    exports com.eiman.ejs;
    exports com.eiman.ejs.controller;
    opens com.eiman.ejs.controller to javafx.fxml;
}