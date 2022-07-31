package net.problemzone.bashit;

import net.problemzone.bashit.listener.*;
import net.problemzone.bashit.modules.GameListener;
import net.problemzone.bashit.modules.GameManager;
import net.problemzone.bashit.modules.itemManager.ItemManager;
import net.problemzone.bashit.modules.itemManager.PlayerManager;
import net.problemzone.bashit.modules.kits.KitManager;
import net.problemzone.bashit.scoreboard.FinishScoreboard;
import net.problemzone.bashit.scoreboard.LobbyScoreboard;
import net.problemzone.bashit.scoreboard.ScoreboardManager;
import net.problemzone.bashit.scoreboard.TestScoreboardManager;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static final String GAME_WORLD_NAME = "BashIt";
    private static JavaPlugin javaPlugin;



    private final ItemManager itemManager = new ItemManager();
     private final FinishScoreboard finishScoreboard = new FinishScoreboard();
     private final PlayerManager playerManager = new PlayerManager(finishScoreboard);
    private final ScoreboardManager scoreboardManager = new ScoreboardManager();
    private final TestScoreboardManager testScoreboardManager = new TestScoreboardManager(playerManager);
    public KitManager kitManager = new KitManager();
    private final LobbyScoreboard lobbyScoreboard = new LobbyScoreboard(kitManager);
    private final GameManager gameManager = new GameManager(itemManager, playerManager, scoreboardManager, kitManager, testScoreboardManager);



    public static JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    @Override
    public void onEnable() {
        getLogger().info("Loading BashIt Plugin.");
        initiatePlugin();

        getLogger().info("Load BashIt Worlds.");
        loadWorlds();

        getLogger().info("Loading BashIt Commands.");
       // registerCommands();

        getLogger().info("Loading BashIt Listeners.");
        registerListeners();

        getLogger().info("BashIt primed and ready.");

    }

    private void initiatePlugin() {
        javaPlugin = this;
    }


    private void loadWorlds() {
        //Set Game rules
        World world = getServer().createWorld(new WorldCreator(GAME_WORLD_NAME));
        Objects.requireNonNull(world).setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        Objects.requireNonNull(world).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        Objects.requireNonNull(world).setGameRule(GameRule.DO_FIRE_TICK, false);
        Objects.requireNonNull(world).setGameRule(GameRule.MOB_GRIEFING, false);

    }



    private void registerListeners() {
        //Event Listeners
        getServer().getPluginManager().registerEvents(new GameListener(gameManager, kitManager), this);
        getServer().getPluginManager().registerEvents(new WorldProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(itemManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new EntityShootBowListener(), this);
        getServer().getPluginManager().registerEvents(new KitListener(kitManager, lobbyScoreboard), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(testScoreboardManager, lobbyScoreboard), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(testScoreboardManager, finishScoreboard, playerManager), this);
        getServer().getPluginManager().registerEvents(new PickupItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(playerManager, kitManager, testScoreboardManager, finishScoreboard),this);
    }

}
