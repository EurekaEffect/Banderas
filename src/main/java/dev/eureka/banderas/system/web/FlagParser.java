package dev.eureka.banderas.system.web;

import dev.eureka.banderas.system.flag.Flag;
import lombok.SneakyThrows;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlagParser {
    private static final String CODES_URL = "https://countrycode.org";
    private static final String FLAG_URL = "https://flagcdn.com/256x192/";

    private String content;

    private final List<Flag> flags = new ArrayList<>();

    public FlagParser() {
        getContent();
    }

    /**
     * Parses website for a raw html string.
     */
    @SneakyThrows
    private void getContent() {
        URLConnection connection =  new URL(CODES_URL).openConnection();
        Scanner scanner = new Scanner(connection.getInputStream());
        scanner.useDelimiter("\\Z");
        content = scanner.next();

        scanner.close();
    }

    /**
     * Get and add each flag from the html content
     */
    public void collectFlags() {
        flags.clear();

        List<String> contentLines = List.of(content.split("\\r?\\n"));

        for (int i = 0; i < contentLines.size(); i++) {
            Pattern pattern = Pattern.compile("<td>.. / ...</td>");
            Matcher matcher = pattern.matcher(contentLines.get(i));

            if (matcher.find()) {
                Flag flag = new Flag();
                flag.parseAndSetName(contentLines.get(i - 2));
                flag.parseAndSetCode(contentLines.get(i));
                flag.setFlag(String.format("%s%s.png", FLAG_URL, flag.getCode()));

                flags.add(flag);
            }
        }
    }

    /**
     * @return list with flags
     */
    public List<Flag> getFlags() {
        return flags;
    }

    /**
     * @return random flag from the flag list
     */
    public Flag getAny() {
        Random random = new Random();

        return flags.get(random.nextInt(flags.size() - 1));
    }
}
