package net.problemzone.bashit.listener;

import net.problemzone.bashit.modules.GameManager;
import net.problemzone.bashit.modules.kits.Kit;
import net.problemzone.bashit.modules.kits.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class KitListener implements Listener {

    private final GameManager gameManager;
    private final KitManager kitManager;

    public KitListener(GameManager gameManager, KitManager kitManager) {
        this.gameManager = gameManager;
        this.kitManager = kitManager;
    }

    @EventHandler
    public void onChooseOpenInv(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if(event.getItem().getType() == Material.NETHER_STAR){
                event.setCancelled(true);
                kitManager.openOperatorInventory(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onOperatorClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null){
            if(event.getView().getTitle().equals(ChatColor.DARK_RED + "Operatorauswahl")){
                Player player = (Player) event.getWhoClicked();
                Kit kit = kitManager.getKitByName(event.getCurrentItem().getItemMeta().getDisplayName());
                kit.equip(player);
                kitManager.putPlayerInMap(player, kit);
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void hasKitSelector(PlayerInteractEvent event){
        Player player = event.getPlayer();

        for (ItemStack item : player.getInventory()){
            if(item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Operatorauswahl")){
                if(item.getAmount() > 1){
                    item.setAmount(item.getAmount() - 1);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void giveSelector(PlayerJoinEvent event){
        Player player = event.getPlayer();
        kitManager.giveKitSelector(player);
    }
}
