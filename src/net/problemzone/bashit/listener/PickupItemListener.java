package net.problemzone.bashit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupItemListener implements Listener {

    @EventHandler
    public void onCollect(EntityPickupItemEvent event){
        event.setCancelled(true);
    }
}
