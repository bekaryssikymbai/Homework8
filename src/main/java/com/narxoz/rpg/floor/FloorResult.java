package com.narxoz.rpg.floor;

public class FloorResult {
    private boolean cleared;
    private String message;
    private int survivors;

    public FloorResult(boolean cleared, String message, int survivors) {
        this.cleared = cleared;
        this.message = message;
        this.survivors = survivors;
    }

    public boolean isCleared() { return cleared; }
    public String getMessage() { return message; }
    public int getSurvivors() { return survivors; }
}