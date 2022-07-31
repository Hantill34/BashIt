package net.problemzone.bashit.modules.kits.Kits;

import net.problemzone.bashit.modules.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Troll extends Kit {
    public Troll() {
        super(ChatColor.GREEN + "Troll", 0, Material.TNT);
    }

    @Override
    public void equip(Player player){
        ItemStack WA = new ItemStack(Material.WOODEN_AXE, 1);
        ItemStack WB = new ItemStack(Material.OAK_BUTTON, 2);
        ItemStack L = new ItemStack(Material.LEVER, 2);
        ItemStack C = new ItemStack(Material.COMPASS, 1);

        ItemMeta ISMeta = WA.getItemMeta();
        assert ISMeta != null;
        ISMeta.setUnbreakable(true);
        WA.setItemMeta(ISMeta);

        ItemMeta WBMeta = WB.getItemMeta();
        assert WBMeta != null;
        WBMeta.setDisplayName("C4 - Typ II");
        WB.setItemMeta(WBMeta);

        ItemMeta LMeta = L.getItemMeta();
        assert LMeta != null;
        LMeta.setDisplayName("C4-Zünder - Typ II");
        LMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        L.setItemMeta(LMeta);

        ItemMeta CMeta = C.getItemMeta();
        assert CMeta != null;
        CMeta.setDisplayName("Tauscher");
        C.setItemMeta(CMeta);

        ItemStack IH2 = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack DC = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemStack IL = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack IB = new ItemStack(Material.IRON_BOOTS, 1);

        ItemMeta DHMeta = IH2.getItemMeta();
        assert DHMeta != null;
        DHMeta.setUnbreakable(true);
        IH2.setItemMeta(DHMeta);
        ItemMeta DCMeta = DC.getItemMeta();
        assert DCMeta != null;
        DCMeta.setUnbreakable(true);
        DC.setItemMeta(DCMeta);
        ItemMeta DLMeta = IL.getItemMeta();
        assert DLMeta != null;
        DLMeta.setUnbreakable(true);
        IL.setItemMeta(DLMeta);
        ItemMeta DBMeta = IB.getItemMeta();
        assert DBMeta != null;
        DBMeta.setUnbreakable(true);
        IB.setItemMeta(DBMeta);

        player.getInventory().addItem(WA);
        player.getInventory().addItem(WB);
        player.getInventory().addItem(L);
        player.getInventory().addItem(C);

        player.getInventory().setHelmet(IH2);
        player.getInventory().setChestplate(DC);
        player.getInventory().setLeggings(IL);
        player.getInventory().setBoots(IB);
    }

    @Override
    public void refreshItems(Player p) {

    }
}
