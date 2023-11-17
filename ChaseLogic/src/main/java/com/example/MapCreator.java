package com.example;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.util.*;
import java.util.List;

import static com.example.ParserConfiguration.*;
import static com.example.ParserArgs.*;

public class MapCreator {
    private final int enemiesCount;
    private final int wallsCount;
    private final int mapSize;
    private static int[][] map;
    private static boolean[][] visited;

    public MapCreator() {
        this.enemiesCount = getEnemiesCount();
        this.wallsCount = getWallsCount();
        this.mapSize = getSize();
        map = new int[mapSize][mapSize];
        visited = new boolean[mapSize][mapSize];
        initMap();
    }

    private void initMap() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = 32;
            }
        }
    }

    public List<Pair<Integer, Integer>> getCharactersPosition(char characterChar) {
        List<Pair<Integer, Integer>> result = new LinkedList<>();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] == characterChar) {
                    result.add(new Pair<>(i, j));
                }
            }
        }
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Character not found");
        }
        return result;
    }

    public void clearVisitedMap() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                visited[i][j] = false;
            }
        }
    }

    public void setVisitedMap(int row, int col) {
        visited[row][col] = true;
    }

    public int[][] getMap() {
        return map;
    }

    private int addPair(Set<Pair<Integer, Integer>> setCell, int x, int y, char character) {
        Pair<Integer, Integer> pair = new Pair<>(x, y);
        if (setCell.contains(pair)) {
            return 0;
        }
        setCell.add(pair);
        map[x][y] = character;
        return 1;
    }

    public void printMap() throws NoSuchFieldException, IllegalAccessException {
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false).build();
        System.out.print("\033[H\033[J");
        System.out.flush();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                Ansi.BColor color = Ansi.BColor.RED;
                if (map[i][j] == getEmptyChar()) {
                    color = Ansi.BColor.valueOf(getEmptyColor());

                } else if (map[i][j] == getWallChar()) {
                    color = Ansi.BColor.valueOf(getWallColor());

                } else if (map[i][j] == getEnemyChar()) {
                    color = Ansi.BColor.valueOf(getEnemyColor());
                } else if (map[i][j] == getPlayerChar()) {
                    color = Ansi.BColor.valueOf(getPlayerColor());
                } else if (map[i][j] == getGoalChar()) {
                    color = Ansi.BColor.valueOf(getGoalColor());
                }
                cp.setBackgroundColor(color);
                cp.print("| " + (char) map[i][j]);
            }
            System.out.println();
        }
    }

    public void createMap() {
        initMap();
        Set<Pair<Integer, Integer>> setCell = new HashSet<>();

        addObjectToMap(setCell, wallsCount, getWallChar());
        addObjectToMap(setCell, enemiesCount, getEnemyChar());
        addObjectToMap(setCell, 1, getGoalChar());
        addObjectToMap(setCell, 1, getPlayerChar());
    }

    public void addObjectToMap(Set<Pair<Integer, Integer>> setCell, int wallsCount, char character) {
        int countAdded = 0;

        while (countAdded < wallsCount) {
            Random random = new Random();
            int x = random.nextInt(mapSize);
            int y = random.nextInt(mapSize);
            countAdded += addPair(setCell, x, y, character);
        }
    }

    public void moveObject(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        int buf = map[to.getLeft()][to.getRight()];
        map[to.getLeft()][to.getRight()] = map[from.getLeft()][from.getRight()];
        map[from.getLeft()][from.getRight()] = buf;
    }

    public void eatObject(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        map[to.getLeft()][to.getRight()] = map[from.getLeft()][from.getRight()];
        map[from.getLeft()][from.getRight()] = getEmptyChar();
    }

    public boolean[][] getVisitedMap() {
        return visited;
    }

}
