package org.example;

import java.util.LinkedList;
import java.util.List;
import static com.example.ParserConfiguration.*;
import static org.example.PersonStatus.*;

import com.example.MapCreator;
import com.example.Pair;
import com.example.PathFinder;

public class Enemy implements ICharacter {
    private Pair<Integer, Integer> pos;
    private List<Character> listCanPass;
    private PathFinder pathFinder;
    private MapCreator mapCreator;

    public Enemy(MapCreator mapCreator, Pair<Integer, Integer> pos) {
        this.pos = pos;
        pathFinder = new PathFinder(mapCreator);
        this.mapCreator = mapCreator;
        listCanPass = new LinkedList<>();
        listCanPass.add(Character.valueOf(getWallChar()));
        listCanPass.add(Character.valueOf(getGoalChar()));
    }

    @Override
    public PersonStatus makeStep() {
        List<Pair<Integer, Integer>> possibleSteps = pathFinder.findPossibleWays(pos, listCanPass);
        if (possibleSteps.isEmpty()) {
            return ENEMY_VICTORY;
        }

        try {
            Pair<Integer, Integer> destStep = mapCreator.getCharactersPosition(Character.valueOf(getPlayerChar())).get(0);
            if (possibleSteps.contains(destStep)) {
                mapCreator.eatObject(pos, destStep);
                pos = destStep;
                return ENEMY_VICTORY;
            }
            Pair<Integer, Integer> nextStep = pathFinder.getNextStep(pos, getPlayerChar(), listCanPass);
            if (possibleSteps.contains(nextStep)) {
                mapCreator.moveObject(pos, nextStep);
                pos = nextStep;
                return MOVING;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Enemy found player");
            return ENEMY_VICTORY;
        }

        for (Pair<Integer, Integer> pair : possibleSteps) {
            mapCreator.setVisitedMap(pair.getLeft(), pair.getRight());
        }

        return STAYING;
    }
}
