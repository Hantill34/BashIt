package net.problemzone.bashit.listener;

import net.problemzone.bashit.scoreboard.LobbyScoreboard;
import net.problemzone.bashit.scoreboard.TestScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final TestScoreboardManager testScoreboardManager;
    private final LobbyScoreboard lobbyScoreboard;

    public PlayerJoinListener(TestScoreboardManager testScoreboardManager, LobbyScoreboard lobbyScoreboard) {
        this.testScoreboardManager = testScoreboardManager;
        this.lobbyScoreboard = lobbyScoreboard;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        testScoreboardManager.initPlayer(player);
        lobbyScoreboard.setLobbyScoreboard(player);
        }
}

