package dev.eureka.banderas.controllers;

import com.google.common.eventbus.Subscribe;
import dev.eureka.banderas.App;
import dev.eureka.banderas.system.enums.AppStage;
import dev.eureka.banderas.system.enums.GameType;
import dev.eureka.banderas.system.event.ClickOnPaneEvent;
import dev.eureka.banderas.system.event.EnterTextEvent;
import dev.eureka.banderas.system.event.Events;
import dev.eureka.banderas.system.event.MouseEvent;
import dev.eureka.banderas.system.flag.Flag;
import dev.eureka.banderas.system.util.Switch;
import dev.eureka.banderas.system.web.FlagParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML private AnchorPane worldMapPane, casualPlayPane, randomPlayPane, flagPane, inputPane, skipPane, grayPane, wrongAnswerPane, confirmPane, topPane, exitPane;
    @FXML private Text worldMapText, casualText, randomText, correctAnswerText, countryText, confirmText;
    @FXML private ImageView flagView;
    @FXML private TextField inputField;

    private AppStage stage;
    private GameType type;

    private FlagParser parser;
    private Flag flag;

    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.EVENT_BUS.register(this);
        stage = AppStage.MENU;

        // TODO: 16.01.2023 do something else, i dont like how it looks
        topPane.setOnMousePressed(e -> {
            if (e.getButton() != MouseButton.PRIMARY) return;

            x = App.appStage.getX() - e.getScreenX();
            y = App.appStage.getY() - e.getScreenY();
        });
        topPane.setOnMouseDragged(e -> {
            if (e.getButton() != MouseButton.PRIMARY) return;

            App.appStage.setX(e.getScreenX() + x);
            App.appStage.setY(e.getScreenY() + y);
        });

        // Gets all flags from the website, may take a while for loading
        parser = new FlagParser();
        parser.collectFlags();

        worldMapText.setFont(getFont(32));
        casualText.setFont(getFont(32));
        randomText.setFont(getFont(32));
        inputField.setFont(getFont(20));
        correctAnswerText.setFont(getFont(26));
        countryText.setFont(getFont(38));
        confirmText.setFont(getFont(32));

        // Set up events
        Events.add(worldMapPane, () -> App.EVENT_BUS.post(new ClickOnPaneEvent(worldMapPane)), AppStage.MENU);
        Events.add(casualPlayPane, () -> App.EVENT_BUS.post(new ClickOnPaneEvent(casualPlayPane)), AppStage.MENU);
        Events.add(randomPlayPane, () -> App.EVENT_BUS.post(new ClickOnPaneEvent(randomPlayPane)), AppStage.MENU);
        Events.add(confirmPane, () -> App.EVENT_BUS.post(new ClickOnPaneEvent(confirmPane)), AppStage.GAME_WRONG_ANSWER);
        Events.add(exitPane, Platform::exit, AppStage.ALWAYS);
        Events.add(skipPane, this::popupWrongPane, AppStage.GAME);

        inputField.setOnKeyPressed((event) -> App.EVENT_BUS.post(new EnterTextEvent(event.getCode(), inputField)));
    }

    @Subscribe
    public void onClick(ClickOnPaneEvent event) {
        new Switch(event.pane())
                .anyCase(this::loadGame, confirmPane)
                .newCase(worldMapPane, () -> type = GameType.WORLD_MAP)
                .newCase(casualPlayPane, () -> type = GameType.CASUAL)
                .newCase(randomPlayPane, () -> type = GameType.RANDOM)
                .newCase(confirmPane, this::closeWrongPane);
    }

    @Subscribe
    public void onEnter(MouseEvent.Enter event) {
        if (event.stage().equals(stage) || event.stage().equals(AppStage.ALWAYS)) {
            event.node().setCursor(Cursor.HAND);
        }
    }

    @Subscribe
    public void onLeft(MouseEvent.Left event) {
        event.node().setCursor(Cursor.DEFAULT);
    }

    @Subscribe
    public void onPress(EnterTextEvent event) {
        if (stage.equals(AppStage.GAME_WRONG_ANSWER)) return;

        if (event.keyCode().equals(KeyCode.ENTER)) {
            String text = event.textField().getText();
            String country = flag.getName();

            if (text.isEmpty()) return;
            text = text.trim();
            text = text.toLowerCase();
            country = country.toLowerCase();

            boolean correct = text.equals(country);
            if (correct) {
                setNextFlag();
            } else {
                popupWrongPane();
            }
        }
    }

    private void loadGame() {
        stage = AppStage.GAME;

        Map<Pane, Boolean> panes = Map.of(
                worldMapPane, false,
                casualPlayPane, false,
                randomPlayPane, false,
                flagPane, true,
                inputPane, true,
                skipPane, true
        );

        flagPane.setVisible(true);
        inputPane.setVisible(true);
        skipPane.setVisible(true);

        Thread thread = new Thread(() -> {
            for (double i = 1.0; i > 0.0; i -= 0.02) {
                try {Thread.sleep(15);} catch (InterruptedException e) {throw new RuntimeException(e);}

                double opacity = i;
                Platform.runLater(() -> {
                    panes.forEach((pane, reverse) -> pane.setOpacity(reverse ? 1.0 - opacity : opacity));
                });
            }
        });
        thread.start();

        setNextFlag();
    }

    private void setNextFlag() {
        flag = switch (type) {
            case WORLD_MAP, CASUAL -> parser.getNext();
            case RANDOM -> parser.getAny();
        };
        flagView.setImage(flag.getFlag());

        inputField.clear();
    }

    private void popupWrongPane() {
        stage = AppStage.GAME_WRONG_ANSWER;

        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                grayPane.setVisible(true);
                wrongAnswerPane.setVisible(true);
                countryText.setText(flag.getName());
            });

            for (double i = 0.0; i < 1.0; i += 0.02) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                double opacity = i;
                Platform.runLater(() -> {
                    grayPane.setOpacity(opacity);
                    wrongAnswerPane.setOpacity(opacity);
                });
            }
        });
        thread.start();
    }

    private void closeWrongPane() {
        stage = AppStage.GAME;

        Thread thread = new Thread(() -> {
            for (double i = 1.0; i > 0.0; i -= 0.02) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                double opacity = i;
                Platform.runLater(() -> {
                    grayPane.setOpacity(opacity);
                    wrongAnswerPane.setOpacity(opacity);
                });
            }

            Platform.runLater(() -> {
                grayPane.setVisible(false);
                wrongAnswerPane.setVisible(false);
                setNextFlag();
            });
        });
        thread.start();
    }

    private Font getFont(int px) {
        try {
            return Font.loadFont(Objects.requireNonNull(App.class.getResource("DaxLine.otf")).openStream(), px);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}