package net.problemzone.bashit.listener;

import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.kits.KitManager;
import org.bukkit.*;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        Player p = e.getPlayer();

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
        if (e.getBlockPlaced().getType() == Material.OAK_BUTTON) {
            e.setCancelled(true);
            final Location loc = e.getBlockPlaced().getLocation();
            ItemStack is = new ItemStack(Material.OAK_BUTTON, 1);
            ItemMeta isMeta = is.getItemMeta();
            isMeta.setDisplayName("C4 - Typ II");
            is.setItemMeta(isMeta);
            p.getInventory().removeItem(is);
            p.updateInventory();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), new Runnable() {

                @Override
                public void run() {
                    if (p.isOnline()) {
                        if (p.getWorld().getBlockAt(loc).getType() == Material.AIR) {
                            p.getWorld().getBlockAt(loc).setType(Material.OAK_BUTTON);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.playEffect(e.getBlockPlaced().getLocation().add(0.5, 0.5, 0.5), Effect.SMOKE, (float) 0.1);
                                players.playSound(e.getBlockPlaced().getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                            }
                            KitManager.c4.put(e.getBlockPlaced(), p);
                        } else {
                            ItemStack is = new ItemStack(Material.OAK_BUTTON, 1);
                            ItemMeta isMeta = is.getItemMeta();
                            assert isMeta != null;
                            isMeta.setDisplayName("C4 - Typ II");
                            is.setItemMeta(isMeta);
                            p.getInventory().addItem(is);
                            p.updateInventory();
                        }
                    }
                }
            }, 1);
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
