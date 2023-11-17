package org.example;

import java.io.IOException;

import com.example.MapCreator;
import com.example.IllegalParametersException;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        try {
            game.preparation(args);
            game.start();
        } catch (IOException | NoSuchFieldException | IllegalAccessException | IllegalParametersException e) {
            throw new RuntimeException(e);
        }
    }
}
