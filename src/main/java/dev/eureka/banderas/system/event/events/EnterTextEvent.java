package dev.eureka.banderas.system.event.events;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public record EnterTextEvent(KeyCode keyCode, TextField textField) {
}
