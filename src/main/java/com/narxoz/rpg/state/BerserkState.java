package com.narxoz.rpg.state;

//import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {
    private int turnsActive;

    public BerserkState() {
        this.turnsActive = 0;
    }

    @Override
    public String getName() {
        return "Berserk!";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower * 2; // Double damage output
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage + 10; // Take extra damage due to reckless fighting
    }

    @Override
    public void onTurnStart(Hero hero) {
        turnsActive++;
        if (hero.getCurrentHp() < hero.getMaxHp() * 0.3) {
            System.out.println(hero.getName() + " is enraged from low health! BERSERK!");
        }
    }

    @Override
    public void onTurnEnd(Hero hero) {
        // Berserk can end if health recovers
        if (hero.getCurrentHp() > hero.getMaxHp() * 0.5) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " calms down from berserk state.");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}