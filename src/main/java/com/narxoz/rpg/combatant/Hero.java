package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.state.StunnedState;

public class Hero {
    private String name;
    private int maxHp;
    private int currentHp;
    private int basePower;
    private HeroState state;

    public Hero(String name, int maxHp, int basePower) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.basePower = basePower;
        this.state = new NormalState();
    }

    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public boolean isAlive() { return currentHp > 0; }
    public HeroState getState() { return state; }  // ← ДОБАВИТЬ ЭТОТ ГЕТТЕР

    public void takeDamage(int damage) {
        if (!isAlive()) return;
        int actualDamage = state.modifyIncomingDamage(damage);
        currentHp -= actualDamage;
        if (currentHp <= 0) {
            currentHp = 0;
            System.out.println(name + " has fallen!");
        } else {
            System.out.println(name + " takes " + actualDamage + " damage. HP: " + currentHp);
            if (currentHp < maxHp * 0.3 && !(state instanceof BerserkState)) {
                setState(new BerserkState());
            }
        }
    }

    public void heal(int amount) {
        if (!isAlive()) return;
        currentHp = Math.min(maxHp, currentHp + amount);
        System.out.println(name + " heals " + amount + " HP. Now: " + currentHp);
    }

    public int attack() {
        if (!isAlive() || !state.canAct()) {
            System.out.println(name + " cannot attack!");
            return 0;
        }
        int damage = state.modifyOutgoingDamage(basePower);
        System.out.println(name + " attacks for " + damage + " damage!");
        return damage;
    }

    public void setState(HeroState newState) {
        System.out.println(name + ": " + state.getName() + " -> " + newState.getName());
        this.state = newState;
    }

    public void onTurnStart() { state.onTurnStart(this); }
    public void onTurnEnd() { state.onTurnEnd(this); }
    public void applyPoison() { setState(new PoisonedState()); }
    public void applyStun() { setState(new StunnedState()); }

    public String getStatus() {
        return name + " (HP:" + currentHp + "/" + maxHp + ", " + state.getName() + ")";
    }
}