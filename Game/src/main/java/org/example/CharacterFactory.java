package org.example;

import com.example.MapCreator;
import com.example.Pair;

public class CharacterFactory {
    public static ICharacter createCharacter(MapCreator mapCreator, Pair<Integer, Integer> pos, String type) {
        switch (type) {
            case "enemy":
                return new Enemy(mapCreator, pos);
            case "player":
                return new Player(mapCreator, pos);
            default:
                return null;
        }
    }
}
