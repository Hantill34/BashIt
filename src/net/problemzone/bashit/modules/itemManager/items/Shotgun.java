package net.problemzone.bashit.modules.itemManager.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Shotgun {

    public static ItemStack createShotgun(){
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Shotgun");
        item.addEnchantment(Enchantment.ARROW_INFINITE,1);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "All the other kids with the pumped up kicks");
        lore.add(ChatColor.GRAY + "You´d better run, out run my gun");
        lore.add("");
        lore.add("");
        lore.add(ChatColor.AQUA + "Left-Click to make the others run faster than your bullet");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
