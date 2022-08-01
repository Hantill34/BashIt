package net.problemzone.bashit.listener;

import net.problemzone.bashit.modules.kits.Kit;
import net.problemzone.bashit.modules.kits.KitManager;
import net.problemzone.bashit.scoreboard.LobbyScoreboard;
import net.problemzone.bashit.util.Language;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;

public class KitListener implements Listener {


    private final KitManager kitManager;
    private final LobbyScoreboard lobbyScoreboard;
    private final String OPERATORSELECTOR = ChatColor.DARK_RED + "Wähle deinen Operator";

    public KitListener(KitManager kitManager, LobbyScoreboard lobbyScoreboard) {
        this.kitManager = kitManager;
        this.lobbyScoreboard = lobbyScoreboard;
    }

    //Operator Selector Listener
    @EventHandler
    public void giveSelector(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        kitManager.giveKitSelector(player);
    }

    @EventHandler
    public void onChooseOpenInv(PlayerInteractEvent event) {
        if(event.getItem() == null) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if ((event.getItem()).getType() == Material.NETHER_STAR) {
                event.setCancelled(true);
                kitManager.openOperatorInventory(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getView().getTitle().equals(OPERATORSELECTOR)) {
                Player player = (Player) event.getWhoClicked();
                Kit kit = kitManager.getKitByName(Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName());
                kitManager.putPlayerInMap(player, kit);
                event.getView().close();
                event.setCancelled(true);
                lobbyScoreboard.updateLobbyScoreboard();
                player.sendMessage(Language.PREFIX.getText() + ChatColor.GREEN + "Du hast das Kit " + kitManager.getKitByPlayer(player).getName() + ChatColor.GREEN + " ausgewählt.");
                player.playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 4, 1);
            }
        }
    }
    //Kit Listener
    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if(Objects.requireNonNull(event.getItem().getItemMeta()).getDisplayName().equals(ChatColor.AQUA + "Jetpack")){
                event.getPlayer().setVelocity(new Vector(0, 1.5, 0));
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 3 * 20, 0, false, false, false));
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
            }
        }
    }
}
