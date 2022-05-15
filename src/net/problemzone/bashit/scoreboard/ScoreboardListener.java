package net.problemzone.bashit.scoreboard;

import net.problemzone.bashit.util.Language;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
    }

    @EventHandler
    public void onPlayerDeathDamage(EntityDamageEvent e) {
        if(e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        if (!(player.getHealth() - e.getFinalDamage() <= 0)) return;

        scoreboardManager.increaseDeathCounter(player);
        scoreboardManager.updateScoreboard();
        Sounds.DEATH.playSoundForPlayer(player);

        if(!(e instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;

        if(event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Sounds.KILL.playSoundForPlayer(damager);
            scoreboardManager.increaseKillCounter(damager);
            damager.sendMessage(String.format(Language.KILLER.getFormattedText(), player.getDisplayName()));
            Bukkit.getOnlinePlayers().stream().filter(p -> p != damager).forEach(p -> p.sendMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), damager.getDisplayName())));

        }

        if (event.getDamager() instanceof Arrow){
            Arrow arrow = (Arrow) event.getDamager();
            if(!(arrow.getShooter() instanceof Player)) return;
            Player damager = (Player) arrow.getShooter();
            Sounds.KILL.playSoundForPlayer(damager);
            scoreboardManager.increaseKillCounter(damager);
            damager.sendMessage(String.format(Language.KILLER.getFormattedText(), player.getDisplayName()));
            Bukkit.getOnlinePlayers().stream().filter(p -> p != damager).forEach(p -> p.sendMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), damager.getDisplayName())));

        }

    }
}