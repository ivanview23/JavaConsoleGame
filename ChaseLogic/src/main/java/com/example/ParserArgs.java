package com.example;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(separators = "=")
public class ParserArgs {

    private static ParserArgs instance;

    @Parameter(names = { "--enemiesCount" })
    private static int enemiesCount;

    @Parameter(names = { "--size" })
    private static int size;

    @Parameter(names = { "--wallsCount" })
    private static int wallsCount;

    @Parameter(names = { "--profile" })
    private static String profile;


    private ParserArgs() {
    }

    public static ParserArgs getInstance() {
        if (instance == null) {
            instance = new ParserArgs();
        }
        return instance;
    }

    public static void setArgs(String[] args) throws IllegalParametersException {
        JCommander.newBuilder()
                .addObject(ParserArgs.getInstance())
                .build()
                .parse(args);
        checkingArgs();
    }

    private static void checkingArgs() throws IllegalParametersException {
        if (enemiesCount + wallsCount + 2 > Math.pow(size, 2) * 0.3) {
            throw new IllegalParametersException();
        }
    }
    public static int getSize() {
        return size;
    }

    public static int getEnemiesCount() {
        return enemiesCount;
    }

    public static int getWallsCount() {
        return wallsCount;
    }

    public static String getProfile() {
        return profile;
    }

}


