package dev.eureka.banderas.system.game;

import dev.eureka.banderas.controllers.AppController;
import dev.eureka.banderas.system.enums.GameType;
import dev.eureka.banderas.system.flag.Flag;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.Map;

public abstract class AbstractGameMode {
    protected final AppController app;
    private Flag flag;

    public AbstractGameMode(AppController app) {
        this.app = app;
    }

    public abstract void run();

    public abstract GameType getType();

    protected void runWithFlags() {
        Map<Pane, Boolean> panes = Map.of(
                app.worldMapPane, false,
                app.casualPlayPane, false,
                app.randomPlayPane, false,
                app.flagPane, true,
                app.inputPane, true,
                app.skipPane, true
        );

        app.flagPane.setVisible(true);
        app.inputPane.setVisible(true);
        app.skipPane.setVisible(true);

        Thread thread = new Thread(() -> {
            for (double i = 1.0; i > 0.0; i -= 0.02) {
                sleep(15);

                double opacity = i;
                Platform.runLater(() -> {
                    panes.forEach((pane, reverse) -> pane.setOpacity(reverse ? 1.0 - opacity : opacity));
                });
            }
        });
        thread.start();

        setNextFlag();
    }

    public void setNextFlag() {
        flag = switch (getType()) {
            case CASUAL -> app.parser.getNext();
            case RANDOM -> app.parser.getAny();
            default -> throw new RuntimeException("Flag Mode can be only Casual or Random.");
        };

        app.flagView.setImage(flag.getFlag());
        app.inputField.clear();
    }

    public Flag getFlag() {
        return flag;
    }

    protected void sleep(long ms) {
        try {Thread.sleep(ms);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }
}
