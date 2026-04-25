package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.*;

public class BossFloor extends TowerFloor {
    private String name;
    private Monster boss;

    public BossFloor(String name, Monster boss) {
        this.name = name;
        this.boss = boss;
    }

    @Override
    protected String getFloorName() { return name; }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("BOSS FIGHT! " + boss.getName() + " appears!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        Monster b = boss;

        for (Hero hero : party) {
            if (!hero.isAlive()) continue;
            if (!b.isAlive()) break;

            hero.onTurnStart();
            int damage = hero.attack();
            b.takeDamage(damage);
            hero.onTurnEnd();
        }

        if (b.isAlive()) {
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.takeDamage(b.attack());
                    break;
                }
            }
        }

        boolean cleared = !b.isAlive();
        return new FloorResult(cleared, "Boss", (int) party.stream().filter(Hero::isAlive).count());
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("EPIC LOOT! 100 gold!");
        for (Hero h : party) {
            if (h.isAlive()) h.heal(50);
        }
    }
}