package dev.eureka.banderas.system.web;

import dev.eureka.banderas.system.flag.Flag;
import lombok.SneakyThrows;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {
    private static final String CODES_URL = "https://countrycode.org";

    private String content;

    private final List<Flag> flags = new ArrayList<>();

    public URLParser() {
        getContent();
    }

    @SneakyThrows
    private void getContent() {
        URLConnection connection =  new URL(CODES_URL).openConnection();
        Scanner scanner = new Scanner(connection.getInputStream());
        scanner.useDelimiter("\\Z");
        content = scanner.next();

        scanner.close();
    }

    public void collectFlags() {
        flags.clear();

        List<String> contentLines = List.of(content.split("\\r?\\n"));

        contentLines.forEach((l) -> {
            Pattern pattern = Pattern.compile("<td>.. / ...</td>");
            Matcher matcher = pattern.matcher(l);

            if (matcher.find()) {
                Flag flag = new Flag();
                flag.parseAndSetCode(l);

                flags.add(flag);
            }
        });
    }

    public List<Flag> getFlags() {
        return flags;
    }
}
