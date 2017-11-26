package com.challenge.common.factory;

import com.challenge.common.domain.Command;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface CommandFactory extends Serializable{
    Command getSpell(String name);

    List<Command> getAvailableSpells();

    void addRandomSpells(int size, List<Command> availableSpells);

    List<Command> addRandomSpells(int size);
}
