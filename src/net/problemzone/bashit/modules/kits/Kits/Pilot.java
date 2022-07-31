package net.problemzone.bashit.modules.kits.Kits;

import net.problemzone.bashit.modules.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pilot extends Kit {


    public Pilot() {
        super(ChatColor.AQUA + "Pilot", 0, Material.FEATHER);
    }

    public static ItemStack createWeapon(){
        ItemStack weapon = new ItemStack(Material.STONE_AXE, 1);
        ItemMeta meta = weapon.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Notausgang");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        weapon.setItemMeta(meta);

        return weapon;
    }

    public static ItemStack createJetpack(){
        ItemStack jet = new ItemStack(Material.FIREWORK_ROCKET, 2);
        ItemMeta jetItemMeta = jet.getItemMeta();
        assert jetItemMeta != null;
        jetItemMeta.setDisplayName(ChatColor.AQUA + "Jetpack");

        List <String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "-> Right Click to fly like AA Flight 11");

        jetItemMeta.setLore(lore);
        jet.setItemMeta(jetItemMeta);
        return jet;
    }
    public static ItemStack createHelmet() {
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmItemMeta = (LeatherArmorMeta) helm.getItemMeta();
        assert helmItemMeta != null;
        helmItemMeta.setDisplayName(ChatColor.AQUA + "Uniform");
        helmItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        helmItemMeta.setColor(Color.BLUE);
        helmItemMeta.setUnbreakable(true);

        helm.setItemMeta(helmItemMeta);
        return helm;
    }
    public static ItemStack createChestplate(){
        ItemStack plate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta plateItemMeta = plate.getItemMeta();
        assert plateItemMeta != null;
        plateItemMeta.setDisplayName(ChatColor.AQUA + "Uniform");
        plateItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        plateItemMeta.setUnbreakable(true);

        plate.setItemMeta(plateItemMeta);
        return plate;
    }
    public static ItemStack createLeggins() {
        ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta legginsItemMeta = (LeatherArmorMeta) leggins.getItemMeta();
        assert legginsItemMeta != null;
        legginsItemMeta.setDisplayName(ChatColor.AQUA + "Uniform");
        legginsItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        legginsItemMeta.setColor(Color.BLUE);
        legginsItemMeta.setUnbreakable(true);

        leggins.setItemMeta(legginsItemMeta);
        return leggins;
    }
    public static ItemStack createBoots() {
        ItemStack helm = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta helmItemMeta = (LeatherArmorMeta) helm.getItemMeta();
        assert helmItemMeta != null;
        helmItemMeta.setDisplayName(ChatColor.AQUA + "Uniform");
        helmItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        helmItemMeta.setColor(Color.BLUE);
        helmItemMeta.setUnbreakable(true);

        helm.setItemMeta(helmItemMeta);
        return helm;
    }
    public static ItemStack Helm = createHelmet();
    public static ItemStack Plate = createChestplate();
    public static ItemStack Leggins = createLeggins();
    public static ItemStack Boots = createBoots();
    public static ItemStack Jetpack = createJetpack();
    public static ItemStack Weapon = createWeapon();
    public static HashMap<Player, Double> cooldowns;

    public static void setupCooldown(){
        cooldowns = new HashMap<>();
    }

    public static void setCooldown(Player player, int seconds){
        double delay = System.currentTimeMillis() + (seconds * 1000L);
        if(checkCooldown(player)){
            cooldowns.put(player, delay);
        }
    }

    public static boolean checkCooldown(Player player) {
        return !cooldowns.containsKey(player) || cooldowns.get(player) <= (System.currentTimeMillis());
    }

    @Override
    public void equip(Player player){

        player.getInventory().addItem(Weapon);
        player.getInventory().addItem(Jetpack);

        player.getInventory().setHelmet(Helm);
        player.getInventory().setChestplate(Plate);
        player.getInventory().setLeggings(Leggins);
        player.getInventory().setBoots(Boots);
    }

    @Override
    public void refreshItems(Player p) {
        p.getInventory().addItem(Jetpack);
    }
}
