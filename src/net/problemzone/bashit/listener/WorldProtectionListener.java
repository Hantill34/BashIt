package net.problemzone.bashit.listener;

import net.problemzone.bashit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class WorldProtectionListener implements Listener {

    @EventHandler
    //Cancels Block Breaks
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    //Cancels Block Placing
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);

        Block block = e.getBlock();

        if (e.getBlock().getType() == Material.FIRE) {
            e.setCancelled(false);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), new Runnable() {

                @Override
                public void run() {
                    if (block.getLocation().getBlock().getType() == Material.FIRE) {
                        block.setType(Material.AIR);
                    }
                }
            }, 100);
        }
    }

    @EventHandler
    //Cancels all Block Changes
    public void onEntityBlockChange(EntityChangeBlockEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    //Cancel Hunger
    public void onHungerDrain(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    //Cancel Entity Spawns
    public void onEntitySpawn(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    //Cancel Armor Stands
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    //Cancel Paintings and Item Frames
    public void onHangingEntityBreak(HangingBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    //Cancel Crop Growth
    public void onCropGrowth(BlockGrowEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    //Cancel Arrow collect
    public void onArrowCollect(PlayerPickupArrowEvent event) {
        event.setCancelled(true);
        Arrow arrow = (Arrow) event.getArrow();
        arrow.remove();
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }
}
