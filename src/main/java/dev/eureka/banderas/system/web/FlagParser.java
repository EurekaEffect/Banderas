package dev.eureka.banderas.system.web;

import dev.eureka.banderas.system.flag.Flag;

import java.io.IOException;
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

    private String content;

    private final List<Flag> flags = new ArrayList<>();
    private final List<String> ignored = List.of("Netherlands Antilles");

    private int next;

    public FlagParser() {
        try {
            getContent();
            next = -1;
        } catch (IOException ignored) {}
    }

    /**
     * Parses website for a raw html string.
     */
    private void getContent() throws IOException {
        URLConnection connection = new URL(CODES_URL).openConnection();
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
                String name = contentLines.get(i - 2);
                String code = contentLines.get(i);
                if (ignored.contains(name)) continue;

                Flag flag = new Flag(name, code);

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
     * @return next flag from the list
     */
    public Flag getNext() {
        next++;

        return flags.get(next >= flags.size() - 1 ? next-- : next);
    }

    /**
     * @return random flag from the flag list
     */
    public Flag getAny() {
        Random random = new Random();

        return flags.get(random.nextInt(flags.size() - 1));
    }
}
