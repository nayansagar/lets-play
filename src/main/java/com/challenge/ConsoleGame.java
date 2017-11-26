package com.challenge;

import com.challenge.common.domain.Game;
import com.challenge.common.domain.Theme;
import com.challenge.common.factory.ThemeFactory;
import com.challenge.common.serialization.GameSerializer;
import com.challenge.common.util.Constants;
import com.challenge.common.util.GOGLogger;
import com.challenge.common.util.ResourceUtils;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import static com.challenge.common.util.Constants.Background.ANSI_CYAN_BACKGROUND;
import static com.challenge.common.util.Constants.Text.ANSI_RED;
import static com.challenge.common.util.Constants.Text.ANSI_RESET;

public class ConsoleGame {

    private ThemeFactory themeFactory;

    private GameSerializer gameSerializer;

    public ConsoleGame() {
        this.themeFactory = getThemeFactory();
        this.gameSerializer = getGameSerializer();
    }

    protected GameSerializer getGameSerializer() {
        return new GameSerializer();
    }

    protected ThemeFactory getThemeFactory() {
        return new ThemeFactory();
    }

    public static void main(String[] args) {
        ConsoleGame consoleGame = new ConsoleGame();
        InputStream inputStream = System.in;
        consoleGame.begin(inputStream);
        System.exit(1);
    }

    public void begin(InputStream inputStream) {
        Theme savedTheme = retrieveSavedTheme();
        if(savedTheme != null){
            System.out.print("Do you want to resume saved game? <y/n>");
            Scanner scanner = new Scanner(inputStream);
            String resume = scanner.nextLine();
            if(!"y".equals(resume)){
                savedTheme = null;
                gameSerializer.remove();
            }
        }
        Theme theme = startPlay(savedTheme, inputStream);
        saveGame(theme);
    }

    private Theme retrieveSavedTheme() {
        Theme theme = null;
        try {
            theme = gameSerializer.resume();
        } catch (IOException |ClassNotFoundException e) {
            GOGLogger.getInstance().getLogger().log(Level.SEVERE, "Could not retrieve saved game", e);
        }
        return theme;
    }

    private void saveGame(Theme theme) {
        if(theme != null){
            try {
                gameSerializer.save(theme);
                System.out.println("Game saved!");
            } catch (IOException e) {
                GOGLogger.getInstance().getLogger().log(Level.SEVERE, "Could not save game!");
            }
        }else {
            gameSerializer.remove();
        }
    }

    private Theme startPlay(Theme theme, InputStream inputStream) {
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_RED +
                ResourceUtils.getResourceAsString("game_banner", "\"Welcome to the Game of Games!\"") + ANSI_RESET);
        Scanner scanner = new Scanner(inputStream);
        boolean saveAndExit = false;
        while (!saveAndExit){
            if(theme == null){
                theme = themeSelection(scanner);
            }
            if(theme == null){
                System.out.println("Do you really want to quit? <y/n>");
                String response = scanner.nextLine();
                if("y".equalsIgnoreCase(response)){
                    System.out.println("Bye!");
                    return null;
                }
                continue;
            }
            theme.getGame().getCharacterFactory().reset();
            System.out.println("Enjoy your game - "+theme.getName());
            MediaPlayer soundTrack = playMedia(theme.getSoundTrack());
            System.out.println(Constants.TILDE_SEPARATOR);
            System.out.println(Constants.Background.ANSI_BLACK_BACKGROUND+Constants.Text.ANSI_WHITE+theme.getBanner()+Constants.Text.ANSI_RESET);
            System.out.println(Constants.TILDE_SEPARATOR);
            Game game = theme.getGame();
            saveAndExit = game.start(scanner);
            stopSoundTrack(soundTrack);
            if(saveAndExit){
                return theme;
            }
            theme = null;
        }
        return null;
    }

    private void stopSoundTrack(MediaPlayer soundTrack) {
        if(soundTrack != null){
            soundTrack.stop();
            soundTrack.dispose();
        }
    }

    public MediaPlayer playMedia(String fileName) {
        new JFXPanel();
        Media hit = new Media(ConsoleGame.class.getClassLoader().getResource(fileName).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(99);
        mediaPlayer.play();
        return mediaPlayer;
    }

    private Theme themeSelection(Scanner scanner) {
        System.out.println("THEMES");
        System.out.println("------------------------");
        List<Theme> themes = themeFactory.getAvailableThemes();
        for(int i=0; i<themes.size(); i++){
            Theme theme = themes.get(i);
            System.out.println(i+1+". "+ theme.getName());
        }
        System.out.println(Constants.TILDE_SEPARATOR);
        System.out.print("Enter the number to pick your theme [0 to exit]>");
        int selection = scanner.nextInt();
        while (selection < 0 || selection > themes.size()) {
            System.out.print("You only have "+themes.size()+" options >");
            selection = scanner.nextInt();
        }
        scanner.nextLine();
        if(selection == 0){
            return null;
        }
        System.out.println(Constants.TILDE_SEPARATOR);
        return themes.get(selection-1);
    }
}
