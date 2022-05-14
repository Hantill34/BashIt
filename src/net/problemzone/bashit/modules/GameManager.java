package net.problemzone.bashit.modules;

import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.itemManager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class GameManager {

    private final static int MAX_PLAYERS = 2;

    private final static int START_TIME = 20;

    private static GameState gameState = GameState.WAITING;

    private final ItemManager itemManager;

    public GameManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public enum GameState {
        WAITING,

        RUNNING,

        FINISHED
    }

    public void startGame() {
        if (gameState == GameState.WAITING) {
            gameState = GameState.RUNNING;
            Bukkit.broadcastMessage("Timer gestartet!");
            for(int i = 0; i<10; i++){
                itemManager.generateChest(Objects.requireNonNull(Bukkit.getWorld("BashIt")));
            }
            for(int i = 0; i<5; i++){
                itemManager.generateEmeraldBlock(Objects.requireNonNull(Bukkit.getWorld("BashIt")));
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    finishGame();
                }
            }.runTaskLater(Main.getJavaPlugin(), START_TIME * 20L);

        }

    }
    private void finishGame() {
        gameState = GameState.FINISHED;
        Bukkit.broadcastMessage("Finished");

    }

    public GameState getGameState() { return gameState;}


}

