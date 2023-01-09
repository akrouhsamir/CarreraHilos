module com.example.carrerahilos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.carrerahilos to javafx.fxml;
    exports com.example.carrerahilos;
}