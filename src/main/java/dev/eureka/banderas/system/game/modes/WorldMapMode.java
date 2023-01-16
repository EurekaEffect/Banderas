package dev.eureka.banderas.system.game.modes;

import dev.eureka.banderas.controllers.AppController;
import dev.eureka.banderas.system.enums.GameType;
import dev.eureka.banderas.system.game.AbstractGameMode;

public class WorldMapMode extends AbstractGameMode {
    public WorldMapMode(AppController app) {
        super(app);
    }

    @Override
    public void run() {
        // TODO: 16.01.2023
    }

    @Override
    public GameType getType() {
        return GameType.WORLD_MAP;
    }
}
