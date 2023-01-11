package dev.eureka.banderas.system.flag;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Flag {
    private String name;
    private String code;

    private String unparsed;

    public void parseAndSetCode(String code) {
        this.unparsed = code;
        this.code = code.trim().substring(4, 6);
    }
}
