package net.problemzone.bashit.listener;


import net.problemzone.bashit.modules.itemManager.ItemManager;
import net.problemzone.bashit.modules.itemManager.items.MG;
import net.problemzone.bashit.modules.itemManager.items.RocketLauncher;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;

public class ItemListener implements Listener {

    private final ItemManager itemManager;

    Random random = new Random();


    public ItemListener(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onChestClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        if (Objects.requireNonNull(event.getClickedBlock()).getType() != Material.CHEST) return;
        event.getClickedBlock().setType(Material.AIR);

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
            player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
        }


        event.setCancelled(true);
        itemManager.generateChest(event.getClickedBlock().getWorld());
    }

    @EventHandler
    public void onEmeraldClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
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

    /*@EventHandler
    public void onExplosion(EntityExplodeEvent event) {

        Main.getJavaPlugin().getLogger().info("Schaden");

        Location explosionLocation = event.getLocation();
        Arrow arrow = (Arrow) event.getEntity();
        Player player = (Player) arrow.getShooter();

        if (player == null) return;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!(item.getItemMeta() instanceof Damageable)) return;

        ItemMeta meta = item.getItemMeta();
        String name = meta.getDisplayName();

        if (name.equals(ChatColor.RED + "Bombenbogen")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Location playerLocation = p.getLocation();
                if (explosionLocation.distance(playerLocation) <= 1) {
                    p.setHealth(0.5);
                } else if (explosionLocation.distance(playerLocation) <= 3) {
                    p.setHealth(6);
                } else if (explosionLocation.distance(playerLocation) <= 5) {
                    p.setHealth(8);
                }
            }
        }*/



}