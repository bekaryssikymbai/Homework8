package com.narxoz.rpg.combatant;

public class Monster {
    private String name;
    private int hp;
    private int damage;

    public Monster(String name, int hp, int damage) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int d) {
        hp -= d;
        if (hp <= 0) System.out.println(name + " dies!");
    }

    public int attack() {
        System.out.println(name + " hits for " + damage + " damage!");
        return damage;
    }
}