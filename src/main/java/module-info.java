module dev.eureka.banderas {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.google.common;

    opens dev.eureka.banderas to javafx.fxml;
    exports dev.eureka.banderas;
    exports dev.eureka.banderas.controllers;
    exports dev.eureka.banderas.system.event;
    opens dev.eureka.banderas.controllers to javafx.fxml;
}