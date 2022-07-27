package net.problemzone.bashit.listener;

import net.problemzone.bashit.modules.kits.KitManager;
import net.problemzone.bashit.scoreboard.ScoreboardManager;
import net.problemzone.bashit.util.Language;
import net.problemzone.bashit.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    private final ScoreboardManager scoreboardManager;

    public EntityDamageByEntityListener(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (event.getDamager() instanceof LightningStrike) {

                LightningStrike ls = (LightningStrike) event.getDamager();
                Player shooter = KitManager.lightnings.get(ls.getUniqueId());

                Sounds.KILL.playSoundForPlayer(shooter);
                scoreboardManager.increaseKillCounter(shooter);
                scoreboardManager.increasePoints(shooter);
                scoreboardManager.decreasePoints(player);
                shooter.sendMessage(String.format(Language.GET_POINTS.getFormattedText()));
                player.sendMessage(String.format(Language.REMOVE_POINTS.getFormattedText()));
                shooter.sendMessage(String.format(Language.KILLER.getFormattedText(), player.getDisplayName()));
                Bukkit.getOnlinePlayers().stream().filter(p -> p != shooter).forEach(p -> p.sendMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), shooter.getDisplayName(), player.getDisplayName())));

                if (player != shooter) {
                    KitManager.target.put(player, shooter);
                }

            }
        }


    }
}
