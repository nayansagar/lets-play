package com.challenge.hp.domain;

import com.challenge.common.domain.Character;
import com.challenge.common.domain.Command;

import java.util.LinkedList;
import java.util.List;

public class HPCharacter implements Character{
    private String name;
    private List<Command> spells = new LinkedList<>();

    public HPCharacter(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Command> getSpells() {
        return spells;
    }

    @Override
    public Command getNextSpell() {
        int index = (int)(Math.random() % (spells.size()-1));
        Command command = spells.get(index);
        spells.remove(command);
        return command;
    }
}
