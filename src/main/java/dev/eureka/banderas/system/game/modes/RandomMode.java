package dev.eureka.banderas.system.game.modes;

import dev.eureka.banderas.controllers.AppController;
import dev.eureka.banderas.system.enums.GameType;
import dev.eureka.banderas.system.game.AbstractGameMode;

public class RandomMode extends AbstractGameMode {
    public RandomMode(AppController app) {
        super(app);
    }

    @Override
    public void run() {
        runWithFlags();
    }

    @Override
    public GameType getType() {
        return GameType.RANDOM;
    }
}
