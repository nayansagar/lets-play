package com.challenge.hp.game;

import com.challenge.common.domain.Command;
import com.challenge.common.domain.Game;
import com.challenge.common.factory.CharacterFactory;
import com.challenge.common.factory.CommandFactory;
import com.challenge.common.util.ResourceUtils;
import com.challenge.hp.domain.HPCharacter;

import java.util.List;
import java.util.Scanner;

import static com.challenge.common.util.Constants.Background.*;
import static com.challenge.common.util.Constants.TILDE_SEPARATOR;
import static com.challenge.common.util.Constants.Text.ANSI_BLACK;
import static com.challenge.common.util.Constants.Text.ANSI_RESET;

public class HPGame extends Game{

    public static final int SPELLS_PER_PLAYER = 3;

    HPCharacter player;

    HPCharacter computerPlayer;

    public HPGame(CharacterFactory characterFactory, CommandFactory commandFactory) {
        this.characterFactory = characterFactory;
        this.commandFactory = commandFactory;
    }

    @Override
    public boolean start(Scanner scanner) {
        System.out.println("Welcome to the Harry Potter game!");
        System.out.println(TILDE_SEPARATOR);
        System.out.println("Objective of this game is to defeat three opponent characters by casting stronger spells than theirs." +
                "You get to choose your character and three spells for your character at the beginning, and every time you defeat" +
                " an opponent, you get an additional spell (you don't get to choose this - what's a game without a bit of luck ;))." +
                "Explore the game to figure out the strengths of the spells!");
        System.out.println(TILDE_SEPARATOR);
        if(player == null){
            player = playerSelection(scanner);
            spellSelection(player, scanner);
        }
        int score = 0;
        int total = characterFactory.getComputerOptions().size();
        while (!player.getSpells().isEmpty()){
            if(computerPlayer == null){
                computerPlayer = assignComputerPlayer();
            }
            if(computerPlayer == null) break;
            System.out.println("Your opponent : " + computerPlayer.getName() + ", Spells : " + computerPlayer.getSpells());
            System.out.println("You           : " + player.getName() + ", Spells : " + player.getSpells());
            score = startRound(scanner, player, score, total, computerPlayer);
            if(score == -1){
                return true;
            }
            computerPlayer = null;
        }
        player = null;
        printResultAndExit(scanner, score, total);
        return false;
    }

    private int startRound(Scanner scanner, HPCharacter player, int score, int total, HPCharacter computerPlayer) {
        while (!player.getSpells().isEmpty()){
            System.out.println("Press \"ENTER\" to start, \"s\" to save and exit the game");
            System.out.print(TILDE_SEPARATOR);
            String input = scanner.nextLine();
            if("s".equalsIgnoreCase(input)){
                return -1;
            }
            Command computerSpell = computerSpell(computerPlayer);
            Command playerSpell = playerSpell(scanner, player, commandFactory);
            printSpellWithAsciiWand(computerPlayer.getName(), computerSpell.getName(), player.getName(), playerSpell.getName());
            if(playerSpell.compareTo(computerSpell) < 0){
                System.out.println(ANSI_RED_BACKGROUND+"You lost!"+ ANSI_RESET);
                System.out.println("######################## SCORE - "+score+"/"+ total+" ########################");
                break;
            }else if(playerSpell.compareTo(computerSpell) > 0){
                commandFactory.addRandomSpells(1, player.getSpells());
                System.out.println(ANSI_GREEN_BACKGROUND + ANSI_BLACK + "You won! you got a new spell" + ANSI_RESET);
                characterFactory.getComputerOptions().remove(computerPlayer);
                score++;
                System.out.println("######################## SCORE - "+score+"/"+ total+" ########################");
                break;
            }else {
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_BLACK +"Equal spells, nobody dies!" + ANSI_RESET);
                System.out.println("######################## SCORE - "+score+"/"+ total+" ########################");
            }
        }
        return score;
    }

    private void spellSelection(HPCharacter player, Scanner scanner) {
        List<Command> availableSpells = commandFactory.getAvailableSpells();
        for(int i=0; i<availableSpells.size(); i++){
            System.out.println(i+1+". "+availableSpells.get(i).getName());
        }
        System.out.println(TILDE_SEPARATOR);
        System.out.println("Select "+SPELLS_PER_PLAYER+" spells for "+player.getName()+" (Enter the number)");
        System.out.println(TILDE_SEPARATOR);
        while (player.getSpells().size() < SPELLS_PER_PLAYER){
            System.out.print("[" + (player.getSpells().size() + 1) + " / " + SPELLS_PER_PLAYER + " >");
            int selection = scanner.nextInt(); scanner.nextLine();
            if(selection < 1 || selection > availableSpells.size()){
                System.out.println("You only have "+availableSpells.size()+" options!");
                continue;
            }
            Command spell = availableSpells.get(selection - 1);
            player.getSpells().add(spell);
        }
        System.out.println(TILDE_SEPARATOR);
    }

    private HPCharacter assignComputerPlayer() {
        HPCharacter computerPlayer = characterFactory.getRandomComputerPlayer();
        if(computerPlayer == null) return null;
        if(computerPlayer.getSpells().isEmpty()){
            commandFactory.addRandomSpells(3).forEach(computerPlayer.getSpells()::add);
        }
        return computerPlayer;
    }

    private void printResultAndExit(Scanner scanner, int score, int total) {
        if(score < total){
            System.out.println("You lost the game! You could not defeat all of your opponents");
        }else {
            System.out.println("You won the game!");
        }
        System.out.println("Press \"ENTER\" to exit Harry Potter game");
        scanner.nextLine();
    }

    private Command playerSpell(Scanner scanner, HPCharacter player, CommandFactory commandFactory) {
        System.out.print("Cast your spell " + player.getSpells() + " >");
        String spell = scanner.next();
        scanner.nextLine();
        Command playerSpell = commandFactory.getSpell(spell.toLowerCase());
        if(!player.getSpells().contains(playerSpell)){
            System.out.println("Not your spell!!");
            return playerSpell(scanner, player, commandFactory);
        }
        player.getSpells().remove(playerSpell);
        return playerSpell;
    }

    private Command computerSpell(HPCharacter computerPlayer) {
        Command computerSpell = computerPlayer.getNextSpell();
        System.out.println(computerPlayer.getName() + "'s turn > " + computerSpell.getName());
        return computerSpell;
    }

    private void printSpellWithAsciiWand(String computerName, String computerSpell, String playerName, String playerSpell) {
        String spellText = ResourceUtils.getResourceAsString("hp/left_wand", "");
        spellText = spellText.replace("_COMPUTER_NAME_", computerName);
        spellText = spellText.replace("_COMPUTER_SPELL_", computerSpell);
        spellText = spellText.replace("_PLAYER_NAME_", playerName);
        spellText = spellText.replace("_PLAYER_SPELL_", playerSpell);

        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + spellText + ANSI_RESET);
    }

    private HPCharacter playerSelection(Scanner scanner) {
        List<HPCharacter> playerCharacters = characterFactory.getPlayerOptions();
        for(int i=0; i<playerCharacters.size(); i++){
            HPCharacter character = playerCharacters.get(i);
            System.out.println(i+1+". "+ character.getName());
        }
        System.out.println(TILDE_SEPARATOR);
        System.out.print("Enter the number to pick your character>");
        int selection = scanner.nextInt();
        scanner.nextLine();
        if(selection > playerCharacters.size()){
            System.out.println("You have only "+playerCharacters.size()+" options!");
            return playerSelection(scanner);
        }
        System.out.println(TILDE_SEPARATOR);
        return playerCharacters.get(selection-1);
    }
}
