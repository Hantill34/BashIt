package net.problemzone.bashit.modules.kits;

import net.problemzone.bashit.modules.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class KitManager {

    private final GameManager gameManager;

    private final List <Kit> kits = new ArrayList<>();

    private final Map <Player, Kit> playerKitMap = new HashMap<>();


    public KitManager(GameManager gameManager){
        //kits.add();
        this.gameManager = gameManager;
    }

    public void giveKitSelector(Player player){

        ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta starItemMeta = star.getItemMeta();

        assert starItemMeta != null;
        starItemMeta.setDisplayName(ChatColor.DARK_RED + "Operatorauswahl");
        star.setItemMeta(starItemMeta);

        player.getInventory().setItem(0, star);
    }

    /*public void removeOperatorSelector(Player player){
        for(ItemStack item : player.getInventory()){
            if(Objects.requireNonNull(item.getItemMeta()).getDisplayName().equals(ChatColor.DARK_RED + "Operatorauswahl")){
                item.setAmount(0);
                player.updateInventory();
            }
        }
    }*/

    public void openOperatorInventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 18, ChatColor.DARK_RED + "Wähle deinen Operator");

        inv.setItem(0, new ItemStack(Material.IRON_CHESTPLATE));

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

    public Kit getKitByPlayer(Player player)
    {
        return playerKitMap.get(player);
    }

}
