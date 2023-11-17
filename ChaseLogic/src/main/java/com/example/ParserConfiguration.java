package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(separators = "=")
public class ParserConfiguration {


    private static ParserConfiguration instance;

    private ParserConfiguration() {
    }

    @Parameter(names = { "enemy.char" })
    private static String enemyChar;
    @Parameter(names = { "player.char" })
    private static String playerChar;
    @Parameter(names = { "wall.char" })
    private static  String wallChar;
    @Parameter(names = { "empty.char" })
    private static String emptyChar;
    @Parameter(names = { "goal.char" })
    private static String  goalChar;
    @Parameter(names = { "enemy.color" })
    private static String  enemyColor;
    @Parameter(names = { "player.color" })
    private static String playerColor;
    @Parameter(names = { "wall.color" })
    private static String wallColor;
    @Parameter(names = { "goal.color" })
    private static String goalColor;
    @Parameter(names = { "empty.color" })
    private static String emptyColor;



    public static ParserConfiguration getInstance() {
        if (instance == null) {
            instance = new ParserConfiguration();
        }
        return instance;
    }


    public static void setConfiguration(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        bufferedReader.close();
        String argsString = stringBuilder.toString().trim();
        String[] args = argsString.isEmpty() ? new String[0] : argsString.split("\\s+");
        JCommander.newBuilder()
                .addObject(ParserConfiguration.getInstance())
                .build()
                .parse(args);
    }

    public static char getEnemyChar() {
        return enemyChar.charAt(0);
    }
    public static  char getGoalChar() {
        return goalChar.charAt(0);
    }
    public static char getPlayerChar() {
        return playerChar.charAt(0);
    }
    public static char getWallChar() {
        return wallChar.charAt(0);
    }
    public static char getEmptyChar() {
        if (emptyChar.isEmpty()) {
            return ' ';
        }
        return emptyChar.charAt(0);
    }
    public static String getEmptyColor() {
        return emptyColor;
    }
    public static String getEnemyColor() {
        return enemyColor;
    }
    public static String getGoalColor() {
        return goalColor;
    }
    public static String getPlayerColor() {
        return playerColor;
    }
    public static String getWallColor() {
        return wallColor;
    }
}
