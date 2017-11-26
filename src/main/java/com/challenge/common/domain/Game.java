package com.challenge.common.domain;

import com.challenge.common.factory.CharacterFactory;
import com.challenge.common.factory.CommandFactory;

import java.io.Serializable;
import java.util.Scanner;

public abstract class Game implements Serializable {

    protected CharacterFactory characterFactory;

    protected CommandFactory commandFactory;

    public CharacterFactory getCharacterFactory() {
        return characterFactory;
    }

    public void setCharacterFactory(CharacterFactory characterFactory) {
        this.characterFactory = characterFactory;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public abstract boolean start(Scanner scanner);
}
