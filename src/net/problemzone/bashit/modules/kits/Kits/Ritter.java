package net.problemzone.bashit.modules.kits.Kits;

import net.problemzone.bashit.modules.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Ritter extends Kit {
    public Ritter() {
        super(ChatColor.YELLOW + "Ritter", 0, Material.IRON_CHESTPLATE);
    }

    /*public static ItemStack item(){
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta Imeta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Beinhaltet:");
        lore.add(ChatColor.GREEN + " - " + ChatColor.WHITE + "Iron Sword");
        lore.add(ChatColor.GREEN + " - " + ChatColor.WHITE + "Fishing Rod");
        lore.add(ChatColor.GREEN + " - " + ChatColor.WHITE + "Chainmail Helmet");
        lore.add(ChatColor.GREEN + " - " + ChatColor.WHITE + "Iron Chestplate");
        lore.add(ChatColor.GREEN + " - " + ChatColor.WHITE + "Chainmail Leggins");
        lore.add(ChatColor.GREEN + " - " + ChatColor.WHITE + "Chainmail Boots");

        assert Imeta != null;
        Imeta.setLore(lore);
        item.setItemMeta(Imeta);

        return item;
    }*/

    @Override
    public void equip(Player player){
        ItemStack IS = new ItemStack(Material.IRON_SWORD, 1);
        ItemStack FR = new ItemStack(Material.FISHING_ROD, 1);
        ItemStack H = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemStack P = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack L = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemStack B = new ItemStack(Material.CHAINMAIL_BOOTS);

        ItemMeta ISmeta = IS.getItemMeta();
        ItemMeta FRmeta = IS.getItemMeta();
        ItemMeta Hmeta = IS.getItemMeta();
        ItemMeta Pmeta = IS.getItemMeta();
        ItemMeta Lmeta = IS.getItemMeta();
        ItemMeta Bmeta = IS.getItemMeta();

        assert ISmeta != null;
        ISmeta.setUnbreakable(true);
        assert FRmeta != null;
        FRmeta.setUnbreakable(true);
        assert Hmeta != null;
        Hmeta.setUnbreakable(true);
        assert Pmeta != null;
        Pmeta.setUnbreakable(true);
        assert Lmeta != null;
        Lmeta.setUnbreakable(true);
        assert Bmeta != null;
        Bmeta.setUnbreakable(true);

        IS.setItemMeta(ISmeta);
        FR.setItemMeta(FRmeta);
        H.setItemMeta(Hmeta);
        P.setItemMeta(Pmeta);
        L.setItemMeta(Lmeta);
        B.setItemMeta(Bmeta);

        player.getInventory().addItem(IS);
        player.getInventory().addItem(FR);
        player.getInventory().setHelmet(H);
        player.getInventory().setLeggings(L);
        player.getInventory().setChestplate(P);
        player.getInventory().setBoots(B);
    }

    @Override
    public void refreshItems(Player p) {

    }

}
