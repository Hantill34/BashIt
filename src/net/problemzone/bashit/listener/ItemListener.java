package net.problemzone.bashit.listener;


import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.itemManager.ItemManager;
import net.problemzone.bashit.modules.itemManager.items.MG;
import net.problemzone.bashit.modules.itemManager.items.RocketLauncher;
import net.problemzone.bashit.modules.itemManager.items.Shotgun;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;

public class ItemListener implements Listener {

    private final ItemManager itemManager;

    Random random = new Random();


    public ItemListener(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChestClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        if (Objects.requireNonNull(event.getClickedBlock()).getType() != Material.CHEST) return;

        //TODO: give player item
        int chance = random.nextInt(3);
        if (chance == 0) {
            player.getInventory().addItem(MG.createMG());
            player.getInventory().addItem(new ItemStack(Material.ARROW));
        }
        if (chance == 1) {
            player.getInventory().addItem(RocketLauncher.createRocketlauncher());
        }
        if (chance == 2) {
            player.getInventory().addItem(Shotgun.createShotgun());
        }

        event.setCancelled(true);

        new BukkitRunnable() {

            @Override
            public void run() {
                event.getClickedBlock().setType(Material.AIR);
            }
        }.runTaskLater(Main.getJavaPlugin(), 2L);


        itemManager.generateChest(event.getClickedBlock().getWorld());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEmeraldClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();

        if (Objects.requireNonNull(event.getClickedBlock()).getType() != Material.EMERALD_BLOCK) return;
        event.getClickedBlock().setType(Material.AIR);

        //TODO: give player a random effect
        int chance = random.nextInt(5);
        if (chance == 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 2));
            Sounds.EFFECT_JUMP.playSoundForPlayer(player);
        }
        if (chance == 1) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 2));
            Sounds.EFFECT_SPEED.playSoundForPlayer(player);
        }
        if (chance == 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2));
            Sounds.EFFECT_BAD_LUCK.playSoundForPlayer(player);
        }
        if (chance == 3) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2));
            Sounds.EFFECT_BAD_LUCK.playSoundForPlayer(player);
        }
        if (chance == 4) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 2));
            Sounds.EFFECT_REGENARATION.playSoundForPlayer(player);
        }


        event.setCancelled(true);
        itemManager.generateEmeraldBlock(event.getClickedBlock().getWorld());

    }

    @EventHandler
    public void onEntityShootBow(PlayerInteractEvent event){
        Player player = (Player) event.getPlayer();
        ItemStack bow = event.getPlayer().getInventory().getItemInMainHand();

        if(bow.getItemMeta() != null && bow.getItemMeta().getDisplayName() != null){
            if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)){


                //Custom Item: Shotgun
                if(bow.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Shotgun")){
                    if(player.getInventory().contains(new ItemStack(Shotgun.createShotgun()))){
                        player.getInventory().removeItem(new ItemStack(Shotgun.createShotgun()));
                    }
                    player.updateInventory();

                    for(Player players : Bukkit.getOnlinePlayers()){
                        players.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
                    }
                    for(int i = 0; i < 16; i++){
                        Vector v = player.getLocation().getDirection().multiply(4);
                        Random random = new Random();
                        v.add(new Vector(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5));
                        player.launchProjectile(Arrow.class).setVelocity(v);

                    }

                }



            }


        }




    }


}