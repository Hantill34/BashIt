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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class Mage extends Kit {
    public Mage() {
        super(ChatColor.LIGHT_PURPLE + "Mage", 0, Material.BLAZE_POWDER);
    }

    @Override
    public void equip(Player player){
        ItemStack K = new ItemStack(Material.STICK, 1);
        ItemStack BR = new ItemStack(Material.BLAZE_ROD, 1);
        ItemStack FSE = new ItemStack(Material.FERMENTED_SPIDER_EYE, 4);
        ItemStack BP = new ItemStack(Material.BLAZE_POWDER, 4);
        ItemStack P1 = new ItemStack(Material.SPLASH_POTION, 1);
        ItemStack P2 = new ItemStack(Material.SPLASH_POTION, 4);

        ArrayList<String> Lore = new ArrayList<String>();

        Lore.add(0, "");
        Lore.add(1, ChatColor.DARK_GRAY + "▸ " + ChatColor.DARK_AQUA + "Linksklick " + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Feuerball");
        Lore.add(2, ChatColor.DARK_GRAY + "▸" + ChatColor.DARK_AQUA + " Schleichen + Linksklick " + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Blitz");

        ItemMeta Kmeta = K.getItemMeta();
        assert Kmeta != null;
        Kmeta.setDisplayName(ChatColor.YELLOW + "Knüppel");
        Kmeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        K.setItemMeta(Kmeta);

        ItemMeta BRMeta = BR.getItemMeta();
        assert BRMeta != null;
        BRMeta.setDisplayName("Zauberstab");
        BRMeta.setLore(Lore);
        BRMeta.addEnchant(Enchantment.LUCK, 2, true);
        BR.setItemMeta(BRMeta);

        PotionMeta P1meta = (PotionMeta) P1.getItemMeta();
        assert P1meta != null;
        P1meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        P1.setItemMeta(P1meta);
        PotionMeta P2meta = (PotionMeta) P2.getItemMeta();
        assert P2meta != null;
        P2meta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, true));
        P2.setItemMeta(P2meta);

        ItemMeta FSEmeta = FSE.getItemMeta();
        assert FSEmeta != null;
        FSEmeta.setDisplayName("Munition » Feuerball");
        FSE.setItemMeta(FSEmeta);

        ItemMeta BPMeta = BP.getItemMeta();
        assert BPMeta != null;
        BPMeta.setDisplayName("Munition » Blitz");
        BP.setItemMeta(BPMeta);

        ItemStack LH = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemStack LC = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemStack LL = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemStack LB = new ItemStack(Material.LEATHER_BOOTS, 1);

        LeatherArmorMeta LHMeta = (LeatherArmorMeta) LH.getItemMeta();
        assert LHMeta != null;
        LHMeta.setColor(Color.PURPLE);
        LHMeta.setUnbreakable(true);
        LHMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
        LH.setItemMeta(LHMeta);
        LeatherArmorMeta LCMeta = (LeatherArmorMeta) LC.getItemMeta();
        assert LCMeta != null;
        LCMeta.setColor(Color.PURPLE);
        LCMeta.setUnbreakable(true);
        LCMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
        LC.setItemMeta(LCMeta);
        LeatherArmorMeta LLMeta = (LeatherArmorMeta) LL.getItemMeta();
        assert LLMeta != null;
        LLMeta.setColor(Color.PURPLE);
        LLMeta.setUnbreakable(true);
        LLMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
        LL.setItemMeta(LLMeta);
        LeatherArmorMeta LBMeta = (LeatherArmorMeta) LB.getItemMeta();
        assert LBMeta != null;
        LBMeta.setColor(Color.PURPLE);
        LBMeta.setUnbreakable(true);
        LBMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
        LB.setItemMeta(LBMeta);

        player.getInventory().addItem(K);
        player.getInventory().addItem(BR);
        player.getInventory().addItem(FSE);
        player.getInventory().addItem(BP);
        player.getInventory().addItem(P1);
        player.getInventory().addItem(P2);

        player.getInventory().setHelmet(LH);
        player.getInventory().setChestplate(LC);
        player.getInventory().setLeggings(LL);
        player.getInventory().setBoots(LB);

    }
}
