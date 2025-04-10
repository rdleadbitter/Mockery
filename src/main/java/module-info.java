module com.mockery {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.mockery to javafx.fxml;
    exports com.mockery;

    opens com.model to javafx.fxml;
    exports com.model;
}
