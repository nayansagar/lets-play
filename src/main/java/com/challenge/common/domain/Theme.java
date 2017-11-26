package com.challenge.common.domain;

import com.challenge.common.util.ResourceUtils;

import java.io.IOException;
import java.io.Serializable;

public class Theme implements Serializable{

    private String name;
    private String banner;
    private String soundTrack;
    private Game game;

    public Theme(String name, String banner, String soundTrack, Game game) {
        this.name = name;
        this.banner = ResourceUtils.getResourceAsString(banner, this.name);
        this.soundTrack = soundTrack;
        this.game = game;
    }

    public String getName(){
        return name;
    }

    public String getBanner(){
        return banner;
    }

    public String getSoundTrack(){
        return soundTrack;
    }

    public Game getGame() {
        return game;
    }
}
