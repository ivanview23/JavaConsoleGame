package org.example;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.example.PersonStatus.*;
import static com.example.ParserConfiguration.*;
import static com.example.ParserArgs.*;

import com.example.MapCreator;
import com.example.Pair;
import com.example.PathFinder;

public class Player implements ICharacter {

    private MapCreator mapCreator;
    private Pair<Integer, Integer> pos;

    private Pair<Integer, Integer> finish;
    private List<Character> listCanPass;
    private PathFinder pathFinder;
    private List<Pair<Integer, Integer>> possibleWays;


    public Player(MapCreator mapCreator, Pair<Integer, Integer> pos) {
        this.mapCreator = mapCreator;
        this.pos = pos;
        listCanPass = new LinkedList<>();
        listCanPass.add(Character.valueOf(getWallChar()));
        listCanPass.add(Character.valueOf(getEnemyChar()));
        this.pathFinder = new PathFinder(mapCreator);
        this.finish = getFinish();
    }

    @Override
    public PersonStatus makeStep() throws IOException {
        this.possibleWays = this.pathFinder.findPossibleWays(this.pos, this.listCanPass);
        if (this.possibleWays.isEmpty()) {
            System.out.println("Некуда бежать");
            return PLAYER_DEFEAT;
        }
        PersonStatus status = STAYING;
        while (true) {
            char direction = (char) System.in.read();
            status = getStatus(direction);
            if (status == PLAYER_DEFEAT || status == MOVING) break;
        }
        if (this.pos.equals(this.finish)) {
            status = PLAYER_VICTORY;
        }
        return status;
    }

    private PersonStatus isPassage(int offsetX, int offsetY) {
        int newX = pos.getLeft() + offsetX;
        int newY = pos.getRight() + offsetY;
        Pair<Integer, Integer> newPos = new Pair<>(newX, newY);
        if (possibleWays.contains(newPos)) {
            mapCreator.moveObject(pos, newPos);
            this.pos = newPos;
            return MOVING;
        }
        return STAYING;
    }

    private PersonStatus getStatus(char direction) {
        switch (direction) {
            case 'A':
            case 'a':
                if (isPassage(0, -1) == MOVING) {
                    return MOVING;
                }
                break;
            case 'W':
            case 'w':
                if (isPassage(-1, 0) == MOVING) {
                    return MOVING;
                }
                break;
            case 's':
            case 'S':
                if (isPassage(1, 0) == MOVING) {
                    return MOVING;
                }
                break;
            case 'd':
            case 'D':
                if (isPassage(0, 1) == MOVING) {
                    return MOVING;
                }
                break;
            case '9':
                System.out.println("Сдался");
                return PLAYER_DEFEAT;
            default:

                break;
        }
        return STAYING;
    }

    private Pair<Integer, Integer> getFinish() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mapCreator.getMap()[i][j] == getGoalChar()) {
                    return new Pair<>(i, j);
                }
            }

        }
        return new Pair<>(0, 0);
    }
}

