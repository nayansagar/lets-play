package com.challenge.hp.factory;

import com.challenge.hp.domain.Team;
import com.challenge.common.factory.CharacterFactory;
import com.challenge.hp.domain.HPCharacter;
import com.challenge.common.util.RandomNumberUtils;

import java.util.*;

public class HPCharacterFactory implements CharacterFactory{
    private Map<Team, List<HPCharacter>> charactersPerTeam = new EnumMap<Team, List<HPCharacter>>(Team.class);

    public HPCharacterFactory() {
        createCharacters();
    }

    private void createCharacters() {
        charactersPerTeam.put(Team.PLAYER, new LinkedList<>(Arrays.asList(
                new HPCharacter("Harry Potter"),
                new HPCharacter("Ron Weasley"),
                new HPCharacter("Hermione Granger")
        )
        ));
        charactersPerTeam.put(Team.COMPUTER, new LinkedList<>(Arrays.asList(
                new HPCharacter("Bellatrix Lestrange"),
                new HPCharacter("Lucius Malfoy"),
                new HPCharacter("Peter Pettigrew")
        )
        ));
    }

    @Override
    public List<HPCharacter> getPlayerOptions(){
        return charactersPerTeam.get(Team.PLAYER);
    }

    @Override
    public List<HPCharacter> getComputerOptions(){
        return charactersPerTeam.get(Team.COMPUTER);
    }

    @Override
    public HPCharacter getRandomComputerPlayer(){
        if(charactersPerTeam.get(Team.COMPUTER).size() == 0){
            return null;
        }
        int index = RandomNumberUtils.randomInt(charactersPerTeam.get(Team.COMPUTER).size());
        return charactersPerTeam.get(Team.COMPUTER).get(index);
    }

    @Override
    public void reset(){
        createCharacters();
    }
}
