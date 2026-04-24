package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.*;

public class CombatFloor extends TowerFloor {
    private String floorName;
    private List<Monster> monsters;
    private String description;

    public CombatFloor(String name, String description, List<Monster> monsters) {
        this.floorName = name;
        this.description = description;
        this.monsters = new ArrayList<>(monsters);
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void announce() {
        super.announce();
        System.out.println(description);
        System.out.println("Monsters await: " + monsters.size() + " enemies!");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Preparing for combat...");
        // Reset any floor-specific state
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        List<Hero> survivingHeroes = new ArrayList<>(party);
        List<Monster> remainingMonsters = new ArrayList<>(monsters);

        System.out.println("\n=== COMBAT BEGINS ===");

        while (!survivingHeroes.isEmpty() && !remainingMonsters.isEmpty()) {
            // Heroes' turn
            for (Hero hero : survivingHeroes) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (remainingMonsters.isEmpty()) break;

                Monster target = remainingMonsters.get(0);
                int damage = hero.attack();
                if (damage > 0 && target.takeDamage(damage)) {
                    System.out.println(target.getName() + " defeated!");
                    remainingMonsters.remove(0);
                }

                hero.onTurnEnd();
            }

            if (remainingMonsters.isEmpty()) break;

            // Monsters' turn
            for (Monster monster : remainingMonsters) {
                if (survivingHeroes.isEmpty()) break;

                Hero target = survivingHeroes.get(0);
                int damage = monster.attack();
                target.takeDamage(damage);

                // Special monster effects
                if (monster.getName().contains("Spider") && damage > 0) {
                    target.applyPoison();
                } else if (monster.getName().contains("Golem") && damage > 0) {
                    target.applyStun();
                }

                survivingHeroes.removeIf(hero -> !hero.isAlive());
            }
        }

        boolean cleared = remainingMonsters.isEmpty() && !survivingHeroes.isEmpty();
        System.out.println("\n=== COMBAT ENDS ===");
        System.out.println("Floor cleared: " + cleared);

        return new FloorResult(cleared, "Combat completed", survivingHeroes.size());
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Loot: Each hero gains 10 gold and 20 XP!");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(5); // Small heal after combat
            }
        }
    }
}