package com.challenge.common.domain;

import java.io.Serializable;

public class Command implements Comparable<Command>, Serializable{
    private String name;
    private int power;

    public Command(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    @Override
    public int hashCode(){
        return power;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Command){
            Command other = (Command) o;
            if(name.equalsIgnoreCase(other.getName()) && power == other.getPower()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return name;
    }

    public int compareTo(Command o) {
        if(this.power < o.power){
            return 1;
        }else if(this.power > o.power){
            return -1;
        }else {
            return 0;
        }
    }
}
