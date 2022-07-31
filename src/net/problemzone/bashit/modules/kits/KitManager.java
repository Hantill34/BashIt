package net.problemzone.bashit.modules.kits;

import net.problemzone.bashit.modules.kits.Kits.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class KitManager {


    private final List<Kit> kits = new ArrayList<>();
    public final Map<Player, Kit> playerKitMap = new HashMap<>();
    public static HashMap<UUID, Player> lightnings = new HashMap<UUID, Player>();
    public static HashMap <Block, Player> c4 = new HashMap<Block, Player>();


    public KitManager() {
        //kits.add();
        kits.add(new Pilot());
        kits.add(new Ritter());
        kits.add(new Mage());
        kits.add(new Attentäter());
       // kits.add(new Troll());
    }

    public void equipPlayer(Player player) {
        player.getInventory().clear();
        playerKitMap.get(player).equip(player);
    }

    public void giveKitSelector(Player player) {

        ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta starItemMeta = star.getItemMeta();

        assert starItemMeta != null;
        starItemMeta.setDisplayName(ChatColor.DARK_RED + "Operatorauswahl");
        star.setItemMeta(starItemMeta);

        player.getInventory().setItem(0, star);
        playerKitMap.put(player, new Ritter());
    }

    /*public void removeKitSelector(Player player){
        for(ItemStack item : player.getInventory()){
            if((item.getItemMeta()).getDisplayName().equals(ChatColor.DARK_RED + "Operatorauswahl")){
                item.setAmount(0);
                player.updateInventory();
            }
        }
    }*/

    public void openOperatorInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_RED + "Wähle deinen Operator");
    //2 , 6 , 13 , 20 , 24
        inv.setItem(2, kits.get(0).getItem());
        inv.setItem(6, kits.get(1).getItem());
        inv.setItem(13, kits.get(2).getItem());
        inv.setItem(20, kits.get(3).getItem());
        //inv.setItem(24, kits.get(4).getItem());
        inv.setItem(24, new ItemStack(Material.BARRIER));

        player.openInventory(inv);
    }

    public Kit getKitByName(String name) {
        for (Kit kit : kits) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return kit;
            }
        }
        return kits.get(0);
    }

    public List<Kit> getKits() {
        return kits;
    }

    public void putPlayerInMap(Player player, Kit kit) {
        playerKitMap.put(player, kit);
    }

    public Kit getKitByPlayer(Player player) {
        return playerKitMap.get(player);
    }

    public Kit getPlayerByKit(Kit kit){
        return playerKitMap.get(kit);
    }

}
