package net.problemzone.bashit.modules.kits.Kits;

import net.problemzone.bashit.modules.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;


public class Attentäter extends Kit {

    public Attentäter() {
        super(ChatColor.RED + "Attentäter", 0, Material.SHEARS);
    }


    @Override
    public void equip(Player player){
        ItemStack S = new ItemStack(Material.SHEARS, 1);
        ItemStack FPI = new ItemStack(Material.FLOWER_POT, 2);

        ItemMeta SMeta = S.getItemMeta();
        assert SMeta != null;
        SMeta.setDisplayName("Messer");
        SMeta.setUnbreakable(true);
        SMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, false);
        S.setItemMeta(SMeta);

        ItemMeta FPIMeta = FPI.getItemMeta();
        assert FPIMeta != null;
        FPIMeta.setDisplayName("Nuke");
        FPI.setItemMeta(FPIMeta);

        ItemStack LC = new ItemStack(Material.LEATHER_CHESTPLATE, 1);

        LeatherArmorMeta LCMeta = (LeatherArmorMeta) LC.getItemMeta();
        assert LCMeta != null;
        LCMeta.setColor(Color.GRAY);
        LCMeta.setDisplayName("Bombengürtel");
        LCMeta.setUnbreakable(true);
        LCMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        LC.setItemMeta(LCMeta);

        player.getInventory().addItem(S);
        player.getInventory().addItem(FPI);

        player.getInventory().setChestplate(LC);
    }
}
