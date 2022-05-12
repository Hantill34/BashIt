package net.problemzone.bashit.modules.itemManager.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MG {

    public static ItemStack createMG(){
        ItemStack item = new ItemStack(Material.BOW, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Wumme");
        item.addEnchantment(Enchantment.ARROW_INFINITE,1);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Give me the");
        lore.add(ChatColor.GRAY + "Ratn datn datn");
        lore.add("");
        lore.add("");
        lore.add(ChatColor.AQUA + "=>  Hold Left-Click to shave your Enemies");

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;

    }

}
