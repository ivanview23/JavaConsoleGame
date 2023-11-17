package org.example;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.example.ParserArgs.*;
import static com.example.ParserConfiguration.*;
import static org.example.PersonStatus.*;
import com.example.MapCreator;
import com.example.IllegalParametersException;
import com.example.Pair;


public class Game {
    private MapCreator mapCreator;
    private Player player;
    private List<ICharacter> listCharacters;

    public void preparation(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException, IllegalParametersException {
        setArgs(args);
        setConfiguration("application-production.properties");
        this.mapCreator = new MapCreator();
        this.mapCreator.createMap();
        List<Pair<Integer, Integer>> characterCoordinates = mapCreator.getCharactersPosition(getEnemyChar());
        listCharacters = new LinkedList<>();
        CharacterFactory characterFactory = new CharacterFactory();
        for (Pair<Integer, Integer> pair : characterCoordinates) {
            listCharacters.add(characterFactory.createCharacter(mapCreator, pair, "enemy"));
        }
        List<Pair<Integer, Integer>> pair = mapCreator.getCharactersPosition(getPlayerChar());
        player = (Player) characterFactory.createCharacter(mapCreator, pair.get(0), "player");
    }

    public void start() throws NoSuchFieldException, IllegalAccessException, IOException {
        mapCreator.printMap();
        PersonStatus status;
        while (true) {
            status = player.makeStep();
            mapCreator.printMap();
            if (status == PLAYER_DEFEAT || status == PLAYER_VICTORY) {
                System.out.println(status);
                break;
            }
            status = makeSteps(listCharacters);
            mapCreator.printMap();
            if (status == ENEMY_VICTORY ) {
                System.out.println(status);
                break;
            }
        }
    }

    private static PersonStatus makeSteps(List<ICharacter> listCharacters) throws IOException {
        PersonStatus personStatus = PersonStatus.STAYING;
        for (ICharacter character : listCharacters) {
            personStatus = character.makeStep();
            if (personStatus == PLAYER_DEFEAT) {
                return personStatus;
            }
        }
        return personStatus;
    }
}
