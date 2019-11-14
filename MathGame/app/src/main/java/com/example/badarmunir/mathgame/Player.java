package com.example.badarmunir.mathgame;

public class Player {
    // used to store player name
    protected String name;
    // used to store player score
    protected int score;

    protected int position;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // its a getter
    public String getName() {
        return name;
    }

    // its a setter
    public void setName(String name) {
        this.name = name;
    }

    // its a getter
    public int getScore() {
        return score;
    }

    // its a setter
    public void setScore(int score) {
        this.score = score;
    }

    // its a getter
    public int getPosition() {
        return position;
    }

    // its a setter
    public void setPosition(int position) {
        this.position = position;
    }
}
