package net.problemzone.bashit.listener;

import net.problemzone.bashit.modules.itemManager.PlayerManager;
import net.problemzone.bashit.modules.kits.KitManager;
import net.problemzone.bashit.scoreboard.FinishScoreboard;
import net.problemzone.bashit.scoreboard.TestScoreboardManager;
import net.problemzone.bashit.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    private final TestScoreboardManager testScoreboardManager;
    private final FinishScoreboard finishScoreboard;
    private final PlayerManager playerManager;

    public EntityDamageByEntityListener(TestScoreboardManager testScoreboardManager, FinishScoreboard finishScoreboard, PlayerManager playerManager) {
        this.testScoreboardManager = testScoreboardManager;
        this.finishScoreboard = finishScoreboard;
        this.playerManager = playerManager;
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(event.getDamager() instanceof Player){
                Player d = (Player) event.getDamager();

                if(player != d){
                    PlayerManager.target.put(player, d);
                }

            }

            if (event.getDamager() instanceof LightningStrike) {

                LightningStrike ls = (LightningStrike) event.getDamager();
                Player shooter = KitManager.lightnings.get(ls.getUniqueId());

                if (player != shooter) {
                    PlayerManager.target.put(player, shooter);
                }
            }


            if (event.getDamager() instanceof Projectile) {
                Projectile pr = (Projectile) event.getDamager();
                Player ps = (Player) pr.getShooter();

                if (player != ps) {
                    PlayerManager.target.put(player, ps);
                }

                if (pr instanceof Arrow) {
                    if (pr.getLocation().getY() - player.getLocation().getY() > 1.65) {
                        assert ps != null;
                        ps.sendMessage(Language.PREFIX + "" + ChatColor.GREEN + "Du hast einen" + ChatColor.RED + " Headshot" + ChatColor.GREEN + " bei " + player.getName() + ChatColor.GREEN + " erzielt.");
                        player.sendMessage(Language.PREFIX + "" + ChatColor.GREEN + ps.getName() + ChatColor.GREEN + " hat einen" + ChatColor.RED + " Headshot" + ChatColor.GREEN + " bei dir erzielt.");

                        ps.playSound(ps.getLocation(), Sound.ENTITY_ARROW_HIT, 1, 3);
                        ps.playSound(ps.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 3);

                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);

                        event.setDamage(event.getDamage() * 2);
                    }
                }
            }
        }
        testScoreboardManager.updateIngameScoreboard();
    }
}