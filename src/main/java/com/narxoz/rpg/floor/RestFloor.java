package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.NormalState;
import java.util.*;

public class RestFloor extends TowerFloor {
    private String floorName;

    public RestFloor(String name) {
        this.floorName = name;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void announce() {
        super.announce();
        System.out.println("A peaceful sanctuary - time to rest!");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Heroes find a comfortable spot to rest.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n=== RESTING ===");

        int heroesHealed = 0;
        int statesCleared = 0;

        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(25);
                heroesHealed++;

                // Clear negative states and return to normal
                if (!(hero.getState() instanceof NormalState)) {
                    hero.setState(new NormalState());
                    statesCleared++;
                }
            }
        }

        System.out.println("Rest complete! " + heroesHealed + " heroes healed.");
        if (statesCleared > 0) {
            System.out.println(statesCleared + " negative states cleared!");
        }

        return new FloorResult(true, "Restful night", heroesHealed);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        // Rest floors don't give loot - overriding hook
        System.out.println("No loot - rest is its own reward.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        // Override hook - rest floors never give loot
        return false;
    }
}