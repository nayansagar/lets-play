package com.challenge.common.factory;

import com.challenge.hp.domain.HPCharacter;

import java.io.Serializable;
import java.util.List;

public interface CharacterFactory extends Serializable{
    List<HPCharacter> getPlayerOptions();

    List<HPCharacter> getComputerOptions();

    HPCharacter getRandomComputerPlayer();

    void reset();
}
