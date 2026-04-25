package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.FloorResult;
import java.util.*;

public class TowerRunner {
    private List<TowerFloor> floors;
    private List<Hero> party;

    public TowerRunner(List<TowerFloor> floors, List<Hero> party) {
        this.floors = floors;
        this.party = party;
    }

    public TowerRunResult runTower() {
        int cleared = 0;

        for (int i = 0; i < floors.size(); i++) {
            System.out.println("\n--- FLOOR " + (i+1) + " ---");
            FloorResult result = floors.get(i).explore(party);
            if (result.isCleared()) {
                cleared++;
                System.out.println("CLEARED!");
            } else {
                System.out.println("FAILED!");
            }
            if (party.stream().noneMatch(Hero::isAlive)) break;
        }

        return new TowerRunResult(cleared, floors.size(),
                (int) party.stream().filter(Hero::isAlive).count(),
                party.size(),
                cleared == floors.size());
    }
}