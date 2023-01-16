package dev.eureka.banderas.system.event;

import dev.eureka.banderas.system.enums.AppStage;
import javafx.scene.Node;

public class MouseEvent {
    public record Enter(Node node, AppStage stage) {
    }

    public record Left(Node node, AppStage stage) {
    }
}
