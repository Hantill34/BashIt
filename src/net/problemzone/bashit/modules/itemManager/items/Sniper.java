package net.problemzone.bashit.modules.itemManager.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Sniper {

    public static ItemStack createSniper(){
        ItemStack item = new ItemStack(Material.BOW, 1);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "Sniper");

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Schniperrr");


        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
