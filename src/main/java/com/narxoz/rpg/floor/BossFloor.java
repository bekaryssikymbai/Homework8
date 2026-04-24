package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.*;

public class BossFloor extends TowerFloor {
    private String floorName;
    private Monster boss;
    private String description;

    public BossFloor(String name, String description, Monster boss) {
        this.floorName = name;
        this.description = description;
        this.boss = boss;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void announce() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║     ⚔️  BOSS FLOOR!  ⚔️       ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println(description);
        System.out.println("✦ " + boss.getName() + " blocks your path! ✦");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("Heroes prepare for the ultimate challenge...");
        // Boss buffs itself
        System.out.println(boss.getName() + " lets out a terrifying roar!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        List<Hero> survivingHeroes = new ArrayList<>(party);
        Monster currentBoss = boss;
        int bossMaxHp = boss.getMaxHp(); // Assuming Monster has getMaxHp()

        System.out.println("\n=== BOSS FIGHT BEGINS ===");
        System.out.println("BOSS HP: " + boss.getCurrentHp() + "/" + bossMaxHp);

        int turnCount = 0;
        while (!survivingHeroes.isEmpty() && currentBoss.isAlive()) {
            turnCount++;
            System.out.println("\n--- Turn " + turnCount + " ---");

            // Heroes' turn
            for (Hero hero : survivingHeroes) {
                if (!hero.isAlive()) continue;
                if (!currentBoss.isAlive()) break;

                hero.onTurnStart();
                int damage = hero.attack();
                if (damage > 0) {
                    boolean bossDefeated = currentBoss.takeDamage(damage);
                    System.out.println(currentBoss.getName() + " HP: " + currentBoss.getCurrentHp() + "/" + bossMaxHp);
                    if (bossDefeated) break;
                }
                hero.onTurnEnd();
            }

            if (!currentBoss.isAlive()) break;

            // Boss' turn
            for (Hero hero : survivingHeroes) {
                if (!hero.isAlive()) continue;
                int damage = currentBoss.attack();
                hero.takeDamage(damage);

                // Boss special abilities
                if (turnCount % 3 == 0) {
                    System.out.println(currentBoss.getName() + " uses STOMP!");
                    hero.takeDamage(damage / 2);
                    hero.applyStun();
                }
            }

            survivingHeroes.removeIf(hero -> !hero.isAlive());
        }

        boolean cleared = currentBoss.isAlive() == false && !survivingHeroes.isEmpty();
        System.out.println("\n=== BOSS FIGHT ENDS ===");

        if (cleared) {
            System.out.println("✦ VICTORY! The boss is defeated! ✦");
        } else {
            System.out.println("💀 DEFEAT... The boss was too powerful... 💀");
        }

        return new FloorResult(cleared, cleared ? "Boss defeated" : "Boss victorious",
                (int) survivingHeroes.stream().filter(Hero::isAlive).count());
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("★★★★★ LEGENDARY LOOT! ★★★★★");
            System.out.println("Each hero gains:");
            System.out.println("  - 100 XP");
            System.out.println("  - 50 Gold");
            System.out.println("  - A legendary artifact!");

            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(50);
                }
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("The boss's chamber crumbles as you claim your victory...");
    }
}