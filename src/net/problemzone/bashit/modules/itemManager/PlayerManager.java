package net.problemzone.bashit.modules.itemManager;

import net.problemzone.bashit.util.Language;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerManager {

    public void wrapUpPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
        player.setGameMode(GameMode.ADVENTURE);
        Sounds.GAME_WIN.playSoundForPlayer(player);
    }

    public void equipPlayer(Player player){
        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
    }

}
