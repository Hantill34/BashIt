package net.problemzone.bashit.scoreboard;

import net.problemzone.bashit.modules.itemManager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class FinishScoreboard {

    public void setFinishScoreboard(Player player){
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Infos", "dummy", ChatColor.RED + "" + ChatColor.BOLD +"Problem" + ChatColor.WHITE + "" + ChatColor.BOLD + "Zone");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("").setScore(7);

        objective.getScore(ChatColor.DARK_AQUA + "Sieger:").setScore(6);

        Team sieger = scoreboard.registerNewTeam("sieger");
        sieger.addEntry(ChatColor.RED + " " + ChatColor.WHITE);
        sieger.setPrefix(ChatColor.YELLOW + player.getName());
        objective.getScore(ChatColor.RED + " " + ChatColor.WHITE).setScore(5);

        Team showLPeanuts = scoreboard.registerNewTeam("siegerPoints");
        showLPeanuts.addEntry(ChatColor.RED + "  " + ChatColor.WHITE);
        showLPeanuts.setPrefix(ChatColor.YELLOW + "0");
        objective.getScore(ChatColor.RED + "  " + ChatColor.WHITE).setScore(4);

        objective.getScore( "   ").setScore(3);

        objective.getScore(ChatColor.DARK_AQUA + "Deine Punkte:").setScore(2);

        Team platz = scoreboard.registerNewTeam("platz");
        platz.addEntry(ChatColor.RED + "    " + ChatColor.WHITE);
        platz.setPrefix(ChatColor.YELLOW + "0");
        objective.getScore(ChatColor.RED + "    " + ChatColor.WHITE).setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void updateFinishScoreboard(String leaderName, int points, int playerPoints, Player player){
            updateSieger(leaderName, player);
            updateSiegerPoints(points, player);
            playerPoints(player, playerPoints);
    }

    private void updateSieger(String leader, Player player){
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("sieger")).setPrefix(ChatColor.YELLOW + "" + leader);
    }

    private void updateSiegerPoints(int points, Player player){
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("siegerPoints")).setPrefix(ChatColor.GRAY + "(" + ChatColor.YELLOW + "" + points + ChatColor.GRAY + ")");
    }

    private void playerPoints(Player player, int Points){
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("platz")).setPrefix(ChatColor.GRAY + "(" + ChatColor.YELLOW + "" + Points + ChatColor.GRAY + ")");
    }

}
