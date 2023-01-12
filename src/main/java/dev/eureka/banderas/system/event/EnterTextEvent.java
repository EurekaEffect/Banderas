package dev.eureka.banderas.system.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterTextEvent {
    private String text;

    public EnterTextEvent(String text) {
        this.text = text;
    }
}
