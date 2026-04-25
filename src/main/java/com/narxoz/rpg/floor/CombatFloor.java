package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.*;

public class CombatFloor extends TowerFloor {
    private String name;
    private List<Monster> monsters;

    public CombatFloor(String name, List<Monster> monsters) {
        this.name = name;
        this.monsters = new ArrayList<>(monsters);
    }

    @Override
    protected String getFloorName() { return name; }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Battle begins!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        List<Monster> remaining = new ArrayList<>(monsters);

        for (Hero hero : party) {
            if (!hero.isAlive()) continue;
            if (remaining.isEmpty()) break;

            hero.onTurnStart();
            Monster target = remaining.get(0);
            int damage = hero.attack();
            target.takeDamage(damage);
            if (!target.isAlive()) remaining.remove(0);
            hero.onTurnEnd();
        }

        for (Monster m : remaining) {
            for (Hero h : party) {
                if (h.isAlive()) {
                    h.takeDamage(m.attack());
                    break;
                }
            }
        }

        boolean cleared = remaining.isEmpty();
        return new FloorResult(cleared, "Combat", (int) party.stream().filter(Hero::isAlive).count());
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Found 10 gold!");
    }
}