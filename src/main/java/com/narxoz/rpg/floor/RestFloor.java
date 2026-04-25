package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.NormalState;
import java.util.*;

public class RestFloor extends TowerFloor {
    private String name;

    public RestFloor(String name) {
        this.name = name;
    }

    @Override
    protected String getFloorName() { return name; }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Resting...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        for (Hero h : party) {
            if (h.isAlive()) {
                h.heal(30);
                h.setState(new NormalState());
            }
        }
        return new FloorResult(true, "Rested", (int) party.stream().filter(Hero::isAlive).count());
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {}

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }
}