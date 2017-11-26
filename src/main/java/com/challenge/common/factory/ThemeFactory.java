package com.challenge.common.factory;

import com.challenge.common.domain.Theme;
import com.challenge.common.serialization.GameSerializer;
import com.challenge.hp.factory.HPCharacterFactory;
import com.challenge.hp.factory.HPSpellFactory;
import com.challenge.hp.game.HPGame;

import java.util.ArrayList;
import java.util.List;

public class ThemeFactory {

    private List<Theme> availableThemes = new ArrayList<>();

    public ThemeFactory(){
        availableThemes.add(new Theme(
            "Harry Potter",
            "hp/harry_potter",
            "hp/hp_theme.mp3",
            new HPGame(new HPCharacterFactory(), new HPSpellFactory()))
        );
    }

    public List<Theme> getAvailableThemes() {
        return availableThemes;
    }
}
