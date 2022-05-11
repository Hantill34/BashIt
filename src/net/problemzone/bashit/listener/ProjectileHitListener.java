package net.problemzone.bashit.listener;

import net.problemzone.bashit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class ProjectileHitListener implements Listener {

    Main plugin;

    public ProjectileHitListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event){
        if(event.getEntity() instanceof Firework && event.getEntity().getCustomName() != null){
            Firework firework = (Firework) event.getEntity();
            Bukkit.broadcastMessage("hi");
            firework.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, firework.getLocation(), 10);
            Bukkit.broadcastMessage("hi");
            firework.getWorld().playSound(firework.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 5);
            Bukkit.broadcastMessage("hi");
            firework.setVelocity(new Vector(firework.getFacing().getModX(), firework.getFacing().getModY(), firework.getFacing().getModZ()));

            Bukkit.broadcastMessage("hi");

            for(Entity entity : firework.getWorld().getNearbyEntities(firework.getLocation(),500,500,500)){

                Bukkit.broadcastMessage("Entity: " + entity.getName());

                if(entity instanceof LivingEntity){

                    Bukkit.broadcastMessage("Living Entity: " + entity.getName());

                    LivingEntity livingentity = (LivingEntity) entity;

                    if(!livingentity.equals(firework.getShooter())){
                        livingentity.damage(5 );
                    }
                }
            }
        }
    }


}
