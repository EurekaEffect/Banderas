package dev.eureka.banderas.system.flag;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Flag {
    private String name;
    private String code;
    private Image flag;

    public void parseAndSetName(String name) {
        name = name.trim();
        name = name.substring(0, name.lastIndexOf("</a>")); //
        name = name.substring(name.lastIndexOf(">") + 1);

        this.name = name;
    }

    public void parseAndSetCode(String code) {
        code = code.trim();
        code = code.toLowerCase();
        code = code.substring(4, 6);

        this.code = code;
    }

    public void setFlag(String url) {
        this.flag = new Image(url);
    }
}
