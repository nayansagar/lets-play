package com.challenge.hp.factory;

import com.challenge.common.domain.Command;
import com.challenge.common.factory.CommandFactory;
import com.challenge.common.util.RandomNumberUtils;

import java.util.*;

public class HPSpellFactory implements CommandFactory{
    private Map<String, Command> spellByName = new HashMap<>();
    private List<String> keys;

    public HPSpellFactory(){
        spellByName.put("avadakedavra", new Command("Avadakedavra", 1));
        spellByName.put("crucio", new Command("Crucio", 2));
        spellByName.put("imperius", new Command("Imperius", 3));
        spellByName.put("levicorpus", new Command("Levicorpus", 4));
        spellByName.put("confringo", new Command("Confringo", 5));
        spellByName.put("incarcerous", new Command("Incarcerous", 6));
        spellByName.put("alohamora", new Command("Alohamora", 7));
        spellByName.put("accio", new Command("Accio", 8));
        spellByName.put("riddikulus", new Command("Riddikulus", 9));
        spellByName.put("ascendio", new Command("Ascendio", 10));
        spellByName.put("descendo", new Command("Descendo", 11));
        spellByName.put("expulso", new Command("expulso", 12));

        keys = new ArrayList<>(spellByName.keySet());
    }

    @Override
    public Command getSpell(String name){
        return spellByName.get(name);
    }

    @Override
    public List<Command> getAvailableSpells(){
        List<Command> commands = new ArrayList<>();
        spellByName.values().forEach(command -> commands.add(command));
        return commands;
    }

    @Override
    public void addRandomSpells(int size, List<Command> availableSpells){
        int added = 0;
        while (added < size){
            Command spell = spellByName.get(keys.get(RandomNumberUtils.randomInt(keys.size())));
            if(availableSpells.contains(spell)) continue;
            availableSpells.add(spell);
            added++;
        }
        return;
    }

    @Override
    public List<Command> addRandomSpells(int size){
        List<Command> randomSpells = new LinkedList<>();
        addRandomSpells(size, randomSpells);
        return randomSpells;
    }
}
