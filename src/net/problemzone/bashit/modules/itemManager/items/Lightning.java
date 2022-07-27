package net.problemzone.bashit.modules.itemManager.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Lightning {

    public static ItemStack createLightning(){
        ItemStack PS = new ItemStack(Material.PUMPKIN_SEEDS, 1);

        ItemMeta PSmeta = PS.getItemMeta();
        assert PSmeta != null;
        PSmeta.setDisplayName(ChatColor.YELLOW + "Lightning");
        PS.setItemMeta(PSmeta);

        return PS;
    }
}
