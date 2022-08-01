package net.problemzone.bashit.modules.itemManager;


import net.problemzone.bashit.scoreboard.FinishScoreboard;
import net.problemzone.bashit.util.Language;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    public static HashMap<Player, Integer> points = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
    public static HashMap<Player, Player> target = new HashMap<Player, Player>();

    private final FinishScoreboard finishScoreboard;

    public PlayerManager(FinishScoreboard finishScoreboard) {
        this.finishScoreboard = finishScoreboard;
    }

    public void wrapUpPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
        player.setGameMode(GameMode.SURVIVAL);
    }

    public void finishPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
        player.setGameMode(GameMode.ADVENTURE);
        Sounds.GAME_WIN.playSoundForPlayer(player);
        finishScoreboard.setFinishScoreboard(player);
        finishScoreboard.updateFinishScoreboard(getLeaderName(), getLeaderPoints(), getPeanuts(player), player);
        player.sendTitle(String.format(Language.WINNER_TITLE.getText(), getLeaderName()), "", 5, 100, 15);
        player.teleport(Objects.requireNonNull(Bukkit.getWorld("Lobby")).getSpawnLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        if (points.isEmpty()) return;
        //getChatLeaderboard(player);
    }

    public String getLeaderName() {
        return Collections.max(PlayerManager.points.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey().getDisplayName();
    }

    public int getLeaderPoints() {
        return Collections.max(PlayerManager.points.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
    }

    /*public void getChatLeaderboard(Player player){

        for (Map.Entry<Player, Integer> pair : points.entrySet()) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "----------]" + ChatColor.YELLOW + " Leaderboard " + ChatColor.DARK_AQUA + "[----------");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(String.format(String.format(ChatColor.GRAY + "(" + ChatColor.YELLOW + "%s" + ChatColor.GRAY + ")" + ChatColor.DARK_GRAY + "   -   " + ChatColor.YELLOW + "%s", pair.getValue(), pair.getKey().getDisplayName())));
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "----------------------------------");
            Bukkit.broadcastMessage("");
        }
    }*/

    public int getPeanuts(Player player) {
        return points.get(player);
    }
}
