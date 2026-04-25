package com.narxoz.rpg.tower;

public class TowerRunResult {
    private int floorsCleared;
    private int totalFloors;
    private int survivingHeroes;
    private int totalHeroes;
    private boolean towerDefeated;

    public TowerRunResult(int floorsCleared, int totalFloors, int survivingHeroes, int totalHeroes, boolean towerDefeated) {
        this.floorsCleared = floorsCleared;
        this.totalFloors = totalFloors;
        this.survivingHeroes = survivingHeroes;
        this.totalHeroes = totalHeroes;
        this.towerDefeated = towerDefeated;
    }

    // Геттеры
    public int getFloorsCleared() { return floorsCleared; }
    public int getTotalFloors() { return totalFloors; }
    public int getSurvivingHeroes() { return survivingHeroes; }
    public int getTotalHeroes() { return totalHeroes; }
    public boolean isTowerDefeated() { return towerDefeated; }

    public void print() {
        System.out.println("\n=== RESULTS ===");
        System.out.println("Floors: " + floorsCleared + "/" + totalFloors);
        System.out.println("Heroes: " + survivingHeroes + "/" + totalHeroes);
        System.out.println("Tower defeated: " + (towerDefeated ? "YES" : "NO"));
    }
}