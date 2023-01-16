package dev.eureka.banderas.system.flag;

import javafx.scene.image.Image;

public class Flag {
    private static final String FLAG_URL = "https://flagcdn.com/256x192/%s.png";

    private String name;
    private String code;
    private Image flag;

    public Flag(String parseName, String parseCode) {
        parseAndSetName(parseName);
        parseAndSetCode(parseCode);
        setFlag();
    }

    private void parseAndSetName(String name) {
        name = name.trim();
        name = name.substring(0, name.lastIndexOf("</a>")); //
        name = name.substring(name.lastIndexOf(">") + 1);

        this.name = name;
    }

    private void parseAndSetCode(String code) {
        code = code.trim();
        code = code.toLowerCase();
        code = code.substring(4, 6);

        this.code = code;
    }

    private void setFlag() {
        this.flag = new Image(String.format(FLAG_URL, code));
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Image getFlag() {
        return flag;
    }
}
