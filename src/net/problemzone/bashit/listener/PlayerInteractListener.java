package net.problemzone.bashit.listener;

import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.itemManager.PlayerManager;
import net.problemzone.bashit.modules.kits.KitManager;
import net.problemzone.bashit.util.Language;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final Main plugin;

    public PlayerInteractListener(Main plugin) {
        this.plugin = plugin;
    }

    public static void fireworkExplosion(Firework firework, Player player) {
        firework.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, firework.getLocation(), 7); //10
        firework.getWorld().playSound(firework.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 5);

        for (Entity entity : firework.getNearbyEntities(10, 10, 10)) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!livingentity.equals(player)) {
                    livingentity.damage(12);
                }
            }
        }
    }

    @EventHandler()
    public void onPlayerInteractRLauncher(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {

            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) return;

            if(player.getInventory().getItemInMainHand().getType() == Material.LEVER){
                event.setCancelled(true);
                if(player.getInventory().getItemInMainHand().getAmount() == 1){
                    player.getInventory().setItemInMainHand(null);
                }else{
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                }
                player.updateInventory();

                ArrayList <Block> c4 = new ArrayList<Block>();

                for(Block blocks : KitManager.c4.keySet()){
                    if(KitManager.c4.get(blocks) == player){
                        if(blocks.getType() == Material.OAK_BUTTON){
                            c4.add(blocks);
                        }
                    }
                }

                for(Block blocks : c4){
                    if(blocks.getType() == Material.OAK_BUTTON){
                        for(Player players : Bukkit.getOnlinePlayers()){
                            players.playEffect(blocks.getLocation().add(0.5, 0.5, 0.5), Effect.SMOKE, (float) 0.1);
                            players.playSound(blocks.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                        }
                        for(Player players : Bukkit.getOnlinePlayers()){
                            if(players.getWorld() == blocks.getWorld()){
                                if(players.getLocation().distance(blocks.getLocation()) <= 5){
                                    if(KitManager.c4.get(blocks).isOnline()){
                                        PlayerManager.target.put(players, KitManager.c4.get(blocks));
                                    }
                                }
                            }
                        }
                        ((TNTPrimed) blocks.getWorld().spawn(blocks.getLocation().add(0.5, 0.5, 0.5), TNTPrimed.class)).setFuseTicks(0);
                        blocks.setType(Material.AIR);
                    }
                    KitManager.c4.remove(blocks);
                }
            }


            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() != null
                    && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Boom, Boom, Boom, Boom")) {

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
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 5, 1);
                firework.setShooter(player);

                new BukkitRunnable() {
                    public void run() {
                        if (firework.isDead()) {
                            fireworkExplosion(firework, player);
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0L, 5L);
                event.setCancelled(true);
            }

            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if (item.getItemMeta() == null) return;
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Wumme")) {

                ItemMeta wumme = item.getItemMeta();
                wumme.setDisplayName(ChatColor.RED + "SHOOTING");
                item.setItemMeta(wumme);

                new BukkitRunnable() {

                    int timer = 10; //25

                    @Override
                    public void run() {
                        if (event.getPlayer().getInventory().containsAtLeast(new ItemStack(Material.ARROW), 1)) {
                            Location eye = event.getPlayer().getEyeLocation();
                            Location loc = eye.add(eye.getDirection().multiply(1.2));
                            Arrow arrow = (Arrow) Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, EntityType.ARROW);
                            arrow.setVelocity(loc.getDirection().normalize().multiply(2));
                            arrow.setShooter(event.getPlayer());
                        }
                        timer--;
                        if (timer == 0) {
                            event.getPlayer().getInventory().removeItem(item);
                            event.getPlayer().getInventory().removeItem(new ItemStack(Material.ARROW));
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0L, 4L);
            }

            if (player.getInventory().getItemInMainHand().getType() == Material.PUMPKIN_SEEDS) {
                event.setCancelled(true);
                if (player.getInventory().getItemInMainHand().getAmount() == 1) {
                    player.getInventory().setItemInMainHand(null);
                } else {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                }
                player.updateInventory();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);

                    if (players != player) {
                        players.setFireTicks(60);
                        LightningStrike ls = players.getWorld().spigot().strikeLightning(players.getLocation(), true);

                        final UUID uuid = ls.getUniqueId();

                        KitManager.lightnings.put(uuid, player);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), new Runnable() {

                            @Override
                            public void run() {
                                KitManager.lightnings.remove(uuid);
                            }
                        }, 20);
                    }
                }

            }


            if (player.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
                ItemStack FSE = new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
                ItemStack BP = new ItemStack(Material.BLAZE_POWDER, 1);

                ItemMeta FSEMeta = FSE.getItemMeta();
                assert FSEMeta != null;
                FSEMeta.setDisplayName("Munition » Feuerball");
                FSE.setItemMeta(FSEMeta);

                ItemMeta BPMeta = BP.getItemMeta();
                assert BPMeta != null;
                BPMeta.setDisplayName("Munition » Blitz");
                BP.setItemMeta(BPMeta);

                if (!player.isSneaking()) {
                    if (player.getInventory().contains(Material.FERMENTED_SPIDER_EYE)) {
                        player.getInventory().removeItem(FSE);
                        player.updateInventory();
                        player.launchProjectile(Fireball.class);

                        for (Player players : Bukkit.getOnlinePlayers()) {
                            Sounds.FIREBALL_SHOOT.playSoundForPlayer(players);
                        }

                    } else {
                        Sounds.FIREBALL_EMPTY.playSoundForPlayer(player);
                        player.sendMessage(Language.NO_AMMUNITION.getFormattedText());
                    }


                } else {
                    if (player.getInventory().contains(Material.BLAZE_POWDER)) {
                        player.getInventory().removeItem(BP);
                        player.updateInventory();

                        LightningStrike ls = player.getWorld().spigot().strikeLightning(player.getTargetBlock(null, 16).getLocation(), true);

                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.playSound(player.getTargetBlock(null, 16).getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                        }

                        final UUID uuid = ls.getUniqueId();

                        KitManager.lightnings.put(uuid, player);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), () -> KitManager.lightnings.remove(uuid), 20);
                    } else {
                        Sounds.FIREBALL_EMPTY.playSoundForPlayer(player);
                        player.sendMessage(Language.NO_AMMUNITION.getFormattedText());
                    }
                }
            }
        }
    }
}





