package net.problemzone.bashit.listener;

import net.problemzone.bashit.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class PlayerInteractListener implements Listener {

    private final Main plugin;

    public PlayerInteractListener(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler()
    public void onPlayerInteractRLauncher(PlayerInteractEvent event) {

        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)){

           if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) return;

           if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() != null
            && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Boom, Boom, Boom, Boom")) {

                Player player = event.getPlayer();

                player.getInventory().removeItem(player.getInventory().getItemInMainHand());

                Location eye = player.getEyeLocation();
                Firework firework = (Firework) Objects.requireNonNull(eye.getWorld()).spawnEntity(eye, EntityType.FIREWORK);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.setPower(0);
                firework.setFireworkMeta(meta);
                firework.setShotAtAngle(true);
                firework.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
                firework.setVelocity(eye.getDirection().normalize().multiply(2));
                firework.setBounce(false);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 5 ,1);
                firework.setShooter(player);

                new BukkitRunnable(){
                    public void run(){
                        if(firework.isDead()){
                            fireworkExplosion(firework, player);
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0L, 5L);
                event.setCancelled(true);
            }
        }
    }

    public static void fireworkExplosion(Firework firework, Player player){
        firework.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, firework.getLocation(), 7); //10
        firework.getWorld().playSound(firework.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 5);

        for(Entity entity : firework.getNearbyEntities(10,10,10)){
            if(entity instanceof LivingEntity){
                LivingEntity livingentity = (LivingEntity) entity;

                if(!livingentity.equals(player)){
                    livingentity.damage(12);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractMG(PlayerInteractEvent event){
        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)){

            if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) return;

            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if(item.getItemMeta() == null) return;
            if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Wumme")){

                ItemMeta wumme = item.getItemMeta();
                wumme.setDisplayName(ChatColor.RED + "SHOOTING");
                item.setItemMeta(wumme);

                new BukkitRunnable(){

                    int timer = 10; //25

                    @Override
                    public void run(){
                        if(event.getPlayer().getInventory().containsAtLeast(new ItemStack(Material.ARROW), 1)){
                            Location eye = event.getPlayer().getEyeLocation();
                            Location loc = eye.add(eye.getDirection().multiply(1.2));
                            Arrow arrow = (Arrow) Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, EntityType.ARROW);
                            arrow.setVelocity(loc.getDirection().normalize().multiply(2));
                            arrow.setShooter(event.getPlayer());
                        }
                        timer --;
                        if(timer == 0){
                            event.getPlayer().getInventory().removeItem(item);
                            event.getPlayer().getInventory().removeItem(new ItemStack(Material.ARROW));
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0L, 4L);
            }
        }
    }


}



