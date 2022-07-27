package net.problemzone.bashit.listener;

import net.problemzone.bashit.modules.itemManager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EntityShootBowListener implements Listener {

    @EventHandler
    public void onShoot(EntityShootBowEvent event){

        Player player = (Player) event.getEntity();
        ItemStack bow = event.getBow();

        assert bow != null;
        Objects.requireNonNull(bow.getItemMeta()).getDisplayName();
        if(bow.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Sniper")){
            event.setCancelled(true);
            player.getInventory().setItemInMainHand(null);
            if(player.getInventory().contains(Material.ARROW)){
                player.getInventory().removeItem(new ItemStack(Material.ARROW));
            }
            player.updateInventory();
            for(Player players : Bukkit.getOnlinePlayers()){
                players.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
            }
            Vector v = player.getLocation().getDirection().multiply(128);
            player.launchProjectile(Arrow.class).setVelocity(v);

            player.removePotionEffect(PotionEffectType.SLOW);
            ItemManager.zoomin.remove(player);
        }
        event.setCancelled(true);
    }
}
