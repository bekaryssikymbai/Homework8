package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {
    private int turnsRemaining = 3;

    @Override
    public String getName() {
        return "Poisoned (" + turnsRemaining + " turns)";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower / 2;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        hero.takeDamage(5);
        System.out.println(hero.getName() + " takes poison damage!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}