package net.problemzone.bashit.scoreboard;

import net.problemzone.bashit.util.Language;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
        Sounds.DEATH.playSoundForPlayer(player);

        if(!(e instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;

        if(event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Sounds.KILL.playSoundForPlayer(damager);
            scoreboardManager.increaseKillCounter(damager);
            scoreboardManager.increasePoints(damager);
            scoreboardManager.decreasePoints(player);
            damager.sendMessage(String.format(Language.KILLER.getFormattedText(), player.getDisplayName()));
            damager.sendMessage(String.format(Language.GET_POINTS.getFormattedText()));
            player.sendMessage(String.format(Language.REMOVE_POINTS.getFormattedText()));
            Bukkit.getOnlinePlayers().stream().filter(p -> p != damager).forEach(p -> p.sendMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), damager.getDisplayName(), player.getDisplayName())));
        }

        if (event.getDamager() instanceof Arrow){
            Arrow arrow = (Arrow) event.getDamager();
            if(!(arrow.getShooter() instanceof Player)) return;
            Player damager = (Player) arrow.getShooter();
            Sounds.KILL.playSoundForPlayer(damager);
            scoreboardManager.increaseKillCounter(damager);
            scoreboardManager.increasePoints(damager);
            scoreboardManager.decreasePoints(player);
            damager.sendMessage(String.format(Language.GET_POINTS.getFormattedText()));
            player.sendMessage(String.format(Language.REMOVE_POINTS.getFormattedText()));
            damager.sendMessage(String.format(Language.KILLER.getFormattedText(), player.getDisplayName()));
            Bukkit.getOnlinePlayers().stream().filter(p -> p != damager).forEach(p -> p.sendMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), damager.getDisplayName(), player.getDisplayName())));
        }

        if(event.getDamager() instanceof Firework){
            Firework firework = (Firework)  event.getDamager();
            if(!(((Player) firework.getShooter() instanceof  Player))){
                Player damager = (Player) firework.getShooter();
                Sounds.KILL.playSoundForPlayer(damager);
                scoreboardManager.increaseKillCounter(damager);
                scoreboardManager.increasePoints(damager);
                scoreboardManager.decreasePoints(player);
                damager.sendMessage(String.format(Language.GET_POINTS.getFormattedText()));
                player.sendMessage(String.format(Language.REMOVE_POINTS.getFormattedText()));
                damager.sendMessage(String.format(Language.KILLER.getFormattedText(), player.getDisplayName()));
                Bukkit.getOnlinePlayers().stream().filter(p -> p != damager).forEach(p -> p.sendMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), damager.getDisplayName(), player.getDisplayName())));

            }
        }
        scoreboardManager.updateScoreboard();

    }
}
