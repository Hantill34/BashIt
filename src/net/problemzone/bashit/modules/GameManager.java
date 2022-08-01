package net.problemzone.bashit.modules;

import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.itemManager.ItemManager;
import net.problemzone.bashit.modules.itemManager.PlayerManager;
import net.problemzone.bashit.modules.kits.KitManager;
import net.problemzone.bashit.scoreboard.ScoreboardManager;
import net.problemzone.bashit.scoreboard.TestScoreboardManager;
import net.problemzone.bashit.util.Countdown;
import net.problemzone.bashit.util.Language;
import net.problemzone.bashit.util.Sounds;
import net.problemzone.lobbibi.modules.events.GameFinishEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameManager {

    private final static int MAX_PLAYERS = 2;

    public final static int START_TIME = 10; //20
    public final static int FIGHT_TIME = 360; //360
    public final static int FINAL_LOBBY_TIME = 20;

    private static GameState gameState = GameState.WRAPPING_UP;
    private final ItemManager itemManager;
    private final PlayerManager playerManager;
    private final ScoreboardManager scoreboardManager;
    private final KitManager kitManager;
    private final TestScoreboardManager testScoreboardManager;

    private final List<Player> playing = new ArrayList<>();

    public GameManager(ItemManager itemManager, PlayerManager playerManager, ScoreboardManager scoreboardManager, KitManager kitManager, TestScoreboardManager testScoreboardManager) {
        this.itemManager = itemManager;
        this.playerManager = playerManager;
        this.scoreboardManager = scoreboardManager;
        this.kitManager = kitManager;
        this.testScoreboardManager = testScoreboardManager;
    }

    public enum GameState {
        WRAPPING_UP,
        WAITING,

        RUNNING,

        FINISHED
    }

    public void wrapUpGame(){
        if(gameState != GameState.WRAPPING_UP) return;
        gameState = GameState.WAITING;
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(Language.JOIN_MESSAGE.getFormattedText());
        Bukkit.broadcastMessage("");
        Countdown.createChatCountdown(START_TIME, Language.FIGHT_START);
        Bukkit.getOnlinePlayers().forEach(playerManager::wrapUpPlayer);
        Bukkit.getOnlinePlayers().forEach(this::registerPlayer);
       new BukkitRunnable(){
            @Override
            public void run(){
                startGame();
            }
        }.runTaskLater(Main.getJavaPlugin(), START_TIME * 20L);

    }

    public void registerPlayer(Player player){
       // scoreboardManager.setGameScoreboard(player);
        testScoreboardManager.setIngameScoreboard(player);
        //kitManager.removeKitSelector(player);
        kitManager.equipPlayer(player);
    }

    public void startGame() {
        if (gameState != GameState.WAITING) return;
        gameState = GameState.RUNNING;

        scoreboardManager.startTimeCountdown(FIGHT_TIME);
        Bukkit.broadcastMessage(Language.KAMPFPHASE.getFormattedText());

        for(int i = 0; i<10; i++){
            itemManager.generateChest(Objects.requireNonNull(Bukkit.getWorld("BashIt")));
        }
        for(int i = 0; i<5; i++){
            itemManager.generateEmeraldBlock(Objects.requireNonNull(Bukkit.getWorld("BashIt")));
        }

        Countdown.createChatCountdown(FIGHT_TIME, Language.Round_END);
        new BukkitRunnable() {
            @Override
            public void run() {
                finishGame();
            }
        }.runTaskLater(Main.getJavaPlugin(), FIGHT_TIME * 20L);


    }

    public void finishGame() {
        gameState = GameState.FINISHED;
        Bukkit.getOnlinePlayers().forEach(playerManager::finishPlayer);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new GameFinishEvent());
                Bukkit.getOnlinePlayers().forEach(Sounds.GAME_WIN::playSoundForPlayer);
            }

        }.runTaskLater(Main.getJavaPlugin(), 5);
    }

    public static GameState getGameState() { return gameState;}


}

