package com.challenge;

import com.challenge.common.domain.Command;
import com.challenge.common.domain.Theme;
import com.challenge.common.factory.CharacterFactory;
import com.challenge.common.factory.CommandFactory;
import com.challenge.common.factory.ThemeFactory;
import com.challenge.common.serialization.GameSerializer;
import com.challenge.hp.domain.HPCharacter;
import com.challenge.hp.factory.HPSpellFactory;
import com.challenge.hp.game.HPGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class ConsoleGameTest {

    private ConsoleGame consoleGame;

    private Theme theme;

    @Mock
    private CharacterFactory characterFactory;

    @Mock
    private CommandFactory commandFactory;

    @Mock
    ThemeFactory themeFactory;

    @Mock
    GameSerializer gameSerializer;

    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        initGameInstance();
        theme = new Theme("Harry Potter", "hp/harry_potter", "hp/hp_theme.mp3", new HPGame(characterFactory, commandFactory));
        when(gameSerializer.resume()).thenReturn(null);
        when(themeFactory.getAvailableThemes()).thenReturn(Arrays.asList(theme));
        when(characterFactory.getPlayerOptions()).thenReturn(Arrays.asList(new HPCharacter("Harry Potter")));
        when(characterFactory.getComputerOptions()).thenReturn(Arrays.asList(new HPCharacter("Lucius Malfoy")));
        when(characterFactory.getRandomComputerPlayer()).thenReturn(new HPCharacter("Lucius Malfoy"));
        when(commandFactory.addRandomSpells(3)).thenReturn(Arrays.asList(new Command("Accio", 8), new Command("Ascendio", 10), new Command("Crucio", 2)));
        initCommandFactoryMock();
    }

    private void initCommandFactoryMock() {
        when(commandFactory.getAvailableSpells()).thenReturn(new HPSpellFactory().getAvailableSpells());
        when(commandFactory.getSpell("alohamora")).thenReturn(new Command("alohamora", 7));
        when(commandFactory.getSpell("accio")).thenReturn(new Command("accio", 8));
        when(commandFactory.getSpell("confringo")).thenReturn(new Command("confringo", 5));
        when(commandFactory.getSpell("ascendio")).thenReturn(new Command("Ascendio", 10));
        when(commandFactory.getSpell("crucio")).thenReturn(new Command("Crucio", 2));
    }

    private void initGameInstance() {
        consoleGame = new ConsoleGame(){
            protected GameSerializer getGameSerializer() {
                return gameSerializer;
            }

            protected ThemeFactory getThemeFactory() {
                return themeFactory;
            }
        };
    }

    public ByteArrayOutputStream setUpOutput(){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bo);
        System.setOut(out);
        return bo;
    }

    private InputStream setUpInput(String fileName) throws FileNotFoundException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
        System.setIn(in);
        return in;
    }

    @Test
    public void test_1() throws FileNotFoundException {
        InputStream in = setUpInput("test_1");
        ByteArrayOutputStream out = setUpOutput();
        consoleGame.begin(in);
        Assert.assertTrue(out.toString().contains("You won! you got a new spell"));
        Assert.assertTrue(out.toString().contains("You won the game!"));
    }

    @Test
    public void test_2() throws FileNotFoundException {
        InputStream in = setUpInput("test_2");
        ByteArrayOutputStream out = setUpOutput();
        consoleGame.begin(in);
        Assert.assertTrue(out.toString().contains("Equal spells, nobody dies!"));
        Assert.assertTrue(out.toString().contains("You lost the game! You could not defeat all of your opponents"));
    }

    @Test
    public void test_3_save() throws IOException {
        InputStream in = setUpInput("test_3");
        ByteArrayOutputStream out = setUpOutput();
        consoleGame.begin(in);
        Mockito.verify(gameSerializer).save(theme);
        Assert.assertTrue(out.toString().contains("Game saved!"));
    }
}
