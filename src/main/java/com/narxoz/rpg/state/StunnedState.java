package com.narxoz.rpg.state;

//import com.narxoz.rpg.combatant.Hero;

public class StunnedState implements HeroState {
    private int turnsRemaining;

    public StunnedState() {
        this.turnsRemaining = 1;
    }

    public StunnedState(int turns) {
        this.turnsRemaining = turns;
    }

    @Override
    public String getName() {
        return "Stunned (" + turnsRemaining + " turns)";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return 0; // Can't attack while stunned
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage; // Take normal damage
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is stunned and cannot act!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " recovers from stun!");
        }
    }

    @Override
    public boolean canAct() {
        return false; // Stunned heroes cannot act
    }
}