package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import java.util.*;

public class PuzzleFloor extends TowerFloor {
    private String floorName;
    private String puzzle;
    private String solution;

    public PuzzleFloor(String name, String puzzle, String solution) {
        this.floorName = name;
        this.puzzle = puzzle;
        this.solution = solution;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void announce() {
        super.announce();
        System.out.println("A mystical puzzle blocks your path!");
        System.out.println(puzzle);
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Heroes ponder the puzzle...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\n=== SOLVING PUZZLE ===");

        // Simulate puzzle solving based on party composition
        int intelligenceScore = 0;
        for (Hero hero : party) {
            if (hero.isAlive()) {
                // Heroes with berserk are worse at puzzles
                if (hero.getState().getName().contains("Berserk")) {
                    intelligenceScore += 1;
                } else {
                    intelligenceScore += 3;
                }
            }
        }

        boolean solved = intelligenceScore >= 5;

        if (solved) {
            System.out.println("The heroes solve the puzzle! " + solution);
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(10); // Puzzle rewards with healing
                }
            }
        } else {
            System.out.println("The puzzle proves too difficult! The heroes take damage from magical backlash.");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.takeDamage(10);
                }
            }
        }

        boolean cleared = solved;
        return new FloorResult(cleared, solved ? "Puzzle solved" : "Puzzle failed",
                (int) party.stream().filter(Hero::isAlive).count());
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Loot: A tome of wisdom! Each hero gains 15 XP.");
        } else {
            System.out.println("No loot from failed puzzle.");
        }
    }
}