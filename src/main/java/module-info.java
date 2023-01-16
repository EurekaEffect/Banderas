module dev.eureka.banderas {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;

    opens dev.eureka.banderas to javafx.fxml;
    exports dev.eureka.banderas;
    exports dev.eureka.banderas.controllers;
    exports dev.eureka.banderas.system.event;
    exports dev.eureka.banderas.system.enums;
    exports dev.eureka.banderas.system.flag;
    exports dev.eureka.banderas.system.web;
    opens dev.eureka.banderas.controllers to javafx.fxml;
    exports dev.eureka.banderas.system.event.events;
}