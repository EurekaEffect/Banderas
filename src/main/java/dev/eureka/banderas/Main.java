package dev.eureka.banderas;

import dev.eureka.banderas.system.web.URLParser;
import lombok.SneakyThrows;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        App.main(args);
    }
}
