package net.problemzone.bashit.modules;

import net.problemzone.bashit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class GameListener implements Listener {

    private final GameManager gameManager;

    private int playercount = 0;

    private static final List <Vector> spawnpoints = Arrays.asList(
            new Vector(-1044.5, 47, -1055.5),
            new Vector( -1027.5, 49, -1008.5),
            new Vector( -980.5, 47, -994.5),
            new Vector( -945.5, 45, -1033.5),
            new Vector( -914.5, 45, -1058.5),
            new Vector( -960.5, 49, -1093.5),
            new Vector( -980.5, 43, -1083.5),
            new Vector( -1040.5, 43, -1096.5),
            new Vector( -1013.5, 47, -1065.5),
            new Vector( -947.5, 53, -1090.5)
    );

    public GameListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if(!player.getWorld().getUID().equals(Objects.requireNonNull(Bukkit.getWorld(Main.GAME_WORLD_NAME)).getUID())) return;

        if (gameManager.getGameState() != GameManager.GameState.WAITING) return;
        gameManager.startGame();
        player.teleport(spawnpoints.get(playercount).toLocation(player.getWorld()));
        playercount ++;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        Random random = new Random();

        event.setRespawnLocation(spawnpoints.get(random.nextInt(spawnpoints.size())).toLocation(player.getWorld()));
    }


}
