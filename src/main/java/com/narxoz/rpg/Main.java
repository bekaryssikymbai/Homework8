package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.*;
import com.narxoz.rpg.tower.TowerRunner;
import com.narxoz.rpg.tower.TowerRunResult;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     THE HAUNTED TOWER - ASCENSION      ║");
        System.out.println("╚════════════════════════════════════════╝");

        Hero warrior = new Hero("Sir Reginald", 120, 25);
        Hero mage = new Hero("Merlin the Wise", 80, 35);
        Hero rogue = new Hero("Shadow Stalker", 90, 28);

        rogue.applyPoison();

        List<Hero> party = Arrays.asList(warrior, mage, rogue);

        List<TowerFloor> floors = Arrays.asList(
                new CombatFloor("Goblin Den", Arrays.asList(new Monster("Goblin", 40, 12))),
                new RestFloor("Sanctuary"),
                new BossFloor("Dragon Peak", new Monster("Dragon", 150, 30))
        );

        TowerRunner runner = new TowerRunner(floors, party);
        TowerRunResult result = runner.runTower();

        // Используем print() вместо геттеров
        result.print();
    }
}