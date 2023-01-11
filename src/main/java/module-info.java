module dev.eureka.banderas {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.eureka.banderas to javafx.fxml;
    exports dev.eureka.banderas;
}