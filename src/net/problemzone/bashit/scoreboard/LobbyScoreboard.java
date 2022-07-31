package net.problemzone.bashit.scoreboard;

import net.problemzone.bashit.modules.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class LobbyScoreboard {

    private final KitManager kitManager;

    public LobbyScoreboard(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    public void setLobbyScoreboard(Player player){
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Infos", "dummy", ChatColor.RED + "" + ChatColor.BOLD +"Problem" + ChatColor.WHITE + "" + ChatColor.BOLD + "Zone");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("").setScore(6);

        objective.getScore(ChatColor.DARK_AQUA + "Spielmodus:").setScore(5);

        Team spielmodus = scoreboard.registerNewTeam("spielmodus");
        spielmodus.addEntry(ChatColor.RED + " " + ChatColor.WHITE);
        spielmodus.setPrefix(ChatColor.YELLOW + "BashIt");
        objective.getScore(ChatColor.RED + " " + ChatColor.WHITE).setScore(4);

        objective.getScore( "  ").setScore(3);

        objective.getScore(ChatColor.DARK_AQUA + "Kit:").setScore(2);

        Team kit = scoreboard.registerNewTeam("kit");
        kit.addEntry(ChatColor.RED + "   " + ChatColor.WHITE);
        kit.setPrefix(ChatColor.YELLOW + "Ritter");
        objective.getScore(ChatColor.RED + "   " + ChatColor.WHITE).setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void updateLobbyScoreboard(){
        for(Player player : Bukkit.getOnlinePlayers()){
            updateKit(player);
        }
    }

    public void updateKit(Player player){
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("kit")).setPrefix(ChatColor.GOLD + "" + kitManager.getKitByPlayer(player).getName());
    }
}
