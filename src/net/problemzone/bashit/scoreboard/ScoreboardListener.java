package net.problemzone.bashit.scoreboard;

import net.problemzone.bashit.util.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class ScoreboardListener implements Listener {

    private final ScoreboardManager scoreboardManager;

    public ScoreboardListener(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        scoreboardManager.initPlayer(p);
        scoreboardManager.updateScoreboard();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        event.setDeathMessage(String.format(Language.PLAYER_DEATH.getFormattedText(), event.getEntity().getName()));

        if(Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (event.getEntity().getKiller() != null) {
                scoreboardManager.increaseKillCounter(event.getEntity().getKiller());
                event.setDeathMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), event.getEntity().getName(), event.getEntity().getKiller().getName()));
            }

            scoreboardManager.increaseDeathCounter(event.getEntity());
            scoreboardManager.updateScoreboard();
        }
    }
}
