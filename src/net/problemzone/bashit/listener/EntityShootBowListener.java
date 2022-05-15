package net.problemzone.bashit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityShootBowListener implements Listener {

    @EventHandler
    public void onShoot(EntityShootBowEvent event){
        event.setCancelled(true);
    }
}
