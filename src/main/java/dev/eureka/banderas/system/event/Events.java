package dev.eureka.banderas.system.event;

import dev.eureka.banderas.App;
import dev.eureka.banderas.system.enums.AppStage;
import dev.eureka.banderas.system.event.events.MouseEvent;
import javafx.scene.Node;

public class Events {
    public static void add(Node node, Runnable onClick, AppStage stage) {
        node.setOnMouseClicked((event) -> onClick.run());

        // Dynamic cursor
        node.setOnMouseEntered((event) -> App.EVENT_BUS.post(new MouseEvent.Enter(node, stage)));
        node.setOnMouseExited((event) -> App.EVENT_BUS.post(new MouseEvent.Left(node, stage)));
    }
}
