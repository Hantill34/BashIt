package net.problemzone.bashit.listener;

import net.problemzone.bashit.modules.itemManager.PlayerManager;
import net.problemzone.bashit.modules.kits.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final KitManager kitManager;

    public DeathListener(PlayerManager playerManager) {
        this.kitManager = new KitManager();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        if (player == null) return;

        kitManager.equipPlayer(player);

    }

}
