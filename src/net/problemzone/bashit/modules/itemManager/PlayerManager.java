package net.problemzone.bashit.modules.itemManager;


import net.problemzone.bashit.util.Sounds;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerManager {

    public void wrapUpPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
        player.setGameMode(GameMode.SURVIVAL);
        Sounds.GAME_WIN.playSoundForPlayer(player);
    }

}
