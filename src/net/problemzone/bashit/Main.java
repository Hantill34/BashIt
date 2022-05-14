package net.problemzone.bashit;

import net.problemzone.bashit.listener.ChatListener;
import net.problemzone.bashit.listener.PlayerInteractListener;
import net.problemzone.bashit.listener.WorldProtectionListener;
import net.problemzone.bashit.modules.GameListener;
import net.problemzone.bashit.modules.GameManager;
import net.problemzone.bashit.listener.ItemListener;
import net.problemzone.bashit.modules.itemManager.ItemManager;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static final String GAME_WORLD_NAME = "BashIt";
    private static JavaPlugin javaPlugin;

    private final ItemManager itemManager = new ItemManager();

    private final GameManager gameManager = new GameManager(itemManager);


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

    }

    private void registerListeners() {
        //Event Listeners
        getServer().getPluginManager().registerEvents(new GameListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new WorldProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(itemManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(gameManager), this);
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
