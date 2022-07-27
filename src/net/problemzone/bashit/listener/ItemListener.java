package net.problemzone.bashit.listener;


import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.itemManager.ItemManager;
import net.problemzone.bashit.modules.itemManager.items.*;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
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
        int chance = random.nextInt(7);
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
        if (chance == 3) {
            player.getInventory().addItem(Sniper.createSniper());
            player.getInventory().addItem(new ItemStack(Material.ARROW));
        }
        if(chance == 4){
            player.getInventory().addItem(Lightning.createLightning());
        }
        if(chance == 5){
            player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
        }
        if(chance == 6){
            ItemStack FS = new ItemStack(Material.FLINT_AND_STEEL);
            Damageable FSmeta = (Damageable) FS.getItemMeta();
            assert FSmeta != null;
            FSmeta.setDamage(Material.FLINT_AND_STEEL.getMaxDurability() - 3);
            FS.setItemMeta(FSmeta);

            player.getInventory().addItem(FS);

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
    public void onEntityShootBow(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack bow = event.getPlayer().getInventory().getItemInMainHand();

        if (bow.getItemMeta() != null && bow.getItemMeta().getDisplayName() != null) {
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {

                //Custom Item: Shotgun
                if (bow.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Shotgun")) {
                    if (player.getInventory().contains(new ItemStack(Shotgun.createShotgun()))) {
                        player.getInventory().removeItem(new ItemStack(Shotgun.createShotgun()));
                    }
                    player.updateInventory();

                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
                    }
                    for (int i = 0; i < 16; i++) {
                        Vector v = player.getLocation().getDirection().multiply(4);
                        Random random = new Random();
                        v.add(new Vector(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5));
                        player.launchProjectile(Arrow.class).setVelocity(v);

                    }

                }

            }

            if (event.getAction() ==Action.RIGHT_CLICK_AIR | event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (bow.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Sniper")) {
                    Bukkit.broadcastMessage("ItemListener");
                    if (!ItemManager.zoomin.contains(player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
                        ItemManager.zoomin.add(player);
                    } else {
                        player.removePotionEffect(PotionEffectType.SLOW);
                        ItemManager.zoomin.remove(player);
                    }
                }

                if(player.getInventory().getItemInMainHand().getType() == Material.FLOWER_POT){
                    event.setCancelled(true);
                    if(player.getInventory().getItemInMainHand().getAmount() == 1){
                        player.getInventory().setItemInMainHand(null);
                    }else{
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                    player.updateInventory();
                    final Entity entity = player.getWorld().dropItemNaturally(player.getEyeLocation(), new ItemStack(Material.FLOWER_POT, 1));
                    entity.setVelocity(player.getLocation().getDirection().multiply(1.25));

                    for(Player players : Bukkit.getOnlinePlayers()){
                        players.playSound(entity.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        players.playEffect(entity.getLocation(), Effect.SMOKE, 1);
                    }

                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.playSound(entity.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                players.playEffect(entity.getLocation(), Effect.SMOKE, 1);
                            }
                        }
                    }, 15);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.playSound(entity.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                players.playEffect(entity.getLocation(), Effect.SMOKE, 1);
                            }
                        }
                    }, 30);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            for(Player players : Bukkit.getOnlinePlayers()){
                                if(players.getWorld() == entity.getWorld()){
                                    if(players.getLocation().distance(entity.getLocation()) <= 5){
                                        players.setFireTicks(60);
                                        players.removePotionEffect(PotionEffectType.POISON);
                                        players.removePotionEffect(PotionEffectType.CONFUSION);
                                        players.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
                                        players.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
                                    }
                                }
                            }
                            ((TNTPrimed) entity.getWorld().spawn(entity.getLocation().add(0.5, 0.5, 0.5), TNTPrimed.class)).setFuseTicks(0);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.playEffect(entity.getLocation(), Effect.ANVIL_LAND, 1);
                            }
                            entity.remove();
                        }
                    }, 35);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {

        Player p = e.getPlayer();

        if (ItemManager.zoomin.contains(p)) {
            if (e.getNewSlot() != e.getPreviousSlot()) {
                p.removePotionEffect(PotionEffectType.SLOW);
                ItemManager.zoomin.remove(p);
            }
        }
    }

}