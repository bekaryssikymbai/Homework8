package com.narxoz.rpg.state;

//import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {
    private int turnsRemaining;

    public PoisonedState() {
        this.turnsRemaining = 3;
    }

    @Override
    public String getName() {
        return "Poisoned (" + turnsRemaining + " turns left)";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower / 2; // Poison reduces attack power
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage; // No change to incoming damage
    }

    @Override
    public void onTurnStart(Hero hero) {
        int poisonDamage = 5;
        hero.takeDamage(poisonDamage);
        System.out.println(hero.getName() + " takes " + poisonDamage + " poison damage!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " is no longer poisoned!");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
