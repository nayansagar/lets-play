package com.challenge.common.domain;

import java.io.Serializable;
import java.util.List;

public interface Character extends Serializable{
    String getName();

    List<Command> getSpells();

    Command getNextSpell();
}
