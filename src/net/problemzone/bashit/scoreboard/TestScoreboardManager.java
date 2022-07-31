package net.problemzone.bashit.scoreboard;

import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.GameManager;
import net.problemzone.bashit.modules.itemManager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TestScoreboardManager {

    private static final String TIME = "timeCounter";

    private final PlayerManager playerManager;

    public TestScoreboardManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    private String formatTimeNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }
    private String formatTimeSeconds(int seconds) {
        ChatColor color = seconds < 60 ? ChatColor.RED : ChatColor.YELLOW;
        return color + formatTimeNumber((seconds / 60) % 60) + ChatColor.GRAY +  ":" + color + formatTimeNumber(seconds % 60);
    }

    private BukkitTask scoreboardTime;

    public void setIngameScoreboard(Player player){
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Infos", "dummy", ChatColor.RED + "" + ChatColor.BOLD +"Problem" + ChatColor.WHITE + "" + ChatColor.BOLD + "Zone");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("").setScore(10);

        //Countdown
        objective.getScore(ChatColor.RED + "Rundenende:").setScore(9);

        Team timeCounter = scoreboard.registerNewTeam(TIME);
        timeCounter.addEntry(ChatColor.RED + " " + ChatColor.WHITE);
        timeCounter.setPrefix(formatTimeSeconds(GameManager.START_TIME));
        objective.getScore(ChatColor.RED + " " + ChatColor.WHITE).setScore(8);

        objective.getScore("  ").setScore(7);

        //Leader+Points of the Leader
        objective.getScore(ChatColor.DARK_AQUA + "Leader:").setScore(6);
        //Leaders Name
        Team showLeader = scoreboard.registerNewTeam("leaderName");
        showLeader.addEntry(ChatColor.RED + "  " + ChatColor.WHITE);
        showLeader.setPrefix(ChatColor.YELLOW + "" + player.getDisplayName());
        objective.getScore(ChatColor.RED + "  " + ChatColor.WHITE).setScore(5);

        //Leaders Points
        Team showLPeanuts = scoreboard.registerNewTeam("leaderPeanuts");
        showLPeanuts.addEntry(ChatColor.RED + "   " + ChatColor.WHITE);
        showLPeanuts.setPrefix(ChatColor.YELLOW + "0");
        objective.getScore(ChatColor.RED + "   " + ChatColor.WHITE).setScore(4);

        objective.getScore("   ").setScore(3);

        //Peanuts of Player
        objective.getScore(ChatColor.DARK_AQUA + "Deine Peanuts:").setScore(2);
        Team peanuts = scoreboard.registerNewTeam("peanuts");
        peanuts.addEntry(ChatColor.RED + "    " + ChatColor.WHITE);
        peanuts.setPrefix(ChatColor.GRAY + "(" + ChatColor.YELLOW + "0" + ChatColor.GRAY + ")");
        objective.getScore(ChatColor.RED + "    "+ ChatColor.WHITE).setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void updateIngameScoreboard(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateLeader(playerManager.getLeaderName(), player);
            updateLeaderPoints(playerManager.getLeaderPoints(), player);
            updatePeanuts(playerManager.getPeanuts(player), player);
        }
    }

    public void startTimeCountdown(int seconds) {
        if (scoreboardTime != null && !scoreboardTime.isCancelled()) scoreboardTime.cancel();

        AtomicInteger remaining = new AtomicInteger(seconds);
        scoreboardTime = new BukkitRunnable() {
            @Override
            public void run() {
                if (remaining.get() <= 0) {
                    if (!this.isCancelled()) this.cancel();
                    return;
                }
                updateTime(remaining.getAndDecrement());
            }
        }.runTaskTimer(Main.getJavaPlugin(), 0, 20);
    }

    private void updateTime(int seconds) {
        Bukkit.getOnlinePlayers().forEach(player -> Objects.requireNonNull(player.getScoreboard().getTeam(TIME)).setPrefix(formatTimeSeconds(seconds)));
    }

    private void updateLeader(String leader, Player player){
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("leaderName")).setPrefix(ChatColor.YELLOW + "" + leader);
    }

    private void updateLeaderPoints(int Points, Player player){
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("leaderPeanuts")).setPrefix(ChatColor.GRAY + "(" + ChatColor.YELLOW + "" + Points + ChatColor.GRAY + ")");
    }

    private void updatePeanuts(int Points, Player player){
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("peanuts")).setPrefix(ChatColor.YELLOW + "" + Points);
    }

    public void initPlayer(Player player){
        PlayerManager.points.put(player, 0);
        PlayerManager.kills.put(player, 0);
    }
}
