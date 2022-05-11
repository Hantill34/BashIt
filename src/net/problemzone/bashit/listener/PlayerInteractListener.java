package net.problemzone.bashit.listener;

import net.problemzone.bashit.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class PlayerInteractListener implements Listener {

    private final Main plugin;

    public PlayerInteractListener(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPLayerInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() != null
            && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Boom, Boom, Boom, Boom")) {

                Player player = event.getPlayer();

                Location eye = player.getEyeLocation();
                Firework firework = (Firework) Objects.requireNonNull(eye.getWorld()).spawnEntity(eye, EntityType.FIREWORK);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.setPower(2);
                firework.setFireworkMeta(meta);
                firework.setShotAtAngle(true);
                firework.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
                firework.setVelocity(eye.getDirection().normalize().multiply(2));
                firework.setBounce(true);
                firework.setShooter(player);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 5 ,1);

                new BukkitRunnable(){
                    public void run(){
                        if(firework.isDead()){
                            firework.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, firework.getLocation(), 10);
                            firework.getWorld().playSound(firework.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 5);

                            for(Entity entity : firework.getNearbyEntities(5,5,5)){
                                if(entity instanceof LivingEntity){
                                    LivingEntity livingentity = (LivingEntity) entity;

                                    if(!livingentity.equals(player)){
                                        livingentity.damage(5);
                                    }
                                }
                            }

                            cancel();
                        }
                    }
                }.runTaskTimer((Plugin) plugin, 0L, 1L);
                event.setCancelled(true);
            }
        }
    }



}

