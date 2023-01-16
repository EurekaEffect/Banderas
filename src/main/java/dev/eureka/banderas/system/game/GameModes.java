package dev.eureka.banderas.system.game;

import dev.eureka.banderas.controllers.AppController;
import dev.eureka.banderas.system.enums.GameType;
import dev.eureka.banderas.system.game.modes.CasualMode;
import dev.eureka.banderas.system.game.modes.RandomMode;
import dev.eureka.banderas.system.game.modes.WorldMapMode;

public class GameModes {
    public static AbstractGameMode get(AppController app, GameType type) {
        return switch (type) {
            case WORLD_MAP -> new WorldMapMode(app);
            case CASUAL -> new CasualMode(app);
            case RANDOM -> new RandomMode(app);
        };
    }
}
