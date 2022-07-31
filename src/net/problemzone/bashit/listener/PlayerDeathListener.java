package net.problemzone.bashit.listener;

import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import net.problemzone.bashit.Main;
import net.problemzone.bashit.modules.itemManager.PlayerManager;
import net.problemzone.bashit.modules.kits.KitManager;
import net.problemzone.bashit.scoreboard.FinishScoreboard;
import net.problemzone.bashit.scoreboard.TestScoreboardManager;
import net.problemzone.bashit.util.Language;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class PlayerDeathListener implements Listener {

    private final PlayerManager playerManager;
    private final KitManager kitManager;
    private final TestScoreboardManager testScoreboardManager;
    private final FinishScoreboard finishScoreboard;

    public PlayerDeathListener(PlayerManager playerManager, KitManager kitManager, TestScoreboardManager testScoreboardManager, FinishScoreboard finishScoreboard) {
        this.playerManager = playerManager;
        this.kitManager = kitManager;

        this.testScoreboardManager = testScoreboardManager;
        this.finishScoreboard = finishScoreboard;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player player = e.getEntity();
        Player killer = e.getEntity().getKiller();

        e.setDeathMessage(null);
        e.getDrops().clear();

        if (player == null) return;
        kitManager.equipPlayer(player);

        if (Objects.requireNonNull(player.getLastDamageCause()).getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || player.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) || player.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)
                || player.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || player.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.MAGIC) || player.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.FIRE) || player.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.FIRE)) {
            if (player != killer) {
                assert killer != null;
                if (killer.getHealth() != 20) {
                    player.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du wurdest von " + ChatColor.YELLOW + killer.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) (killer.getHealth() + 1.0) * 10.0) / 20.0 + " ❤" + ChatColor.DARK_AQUA + " getötet.");
                    killer.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du hast " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) (killer.getHealth() + 1.0) * 10.0) / 20.0 + " ❤" + ChatColor.DARK_AQUA + " getötet.");
                } else {
                    player.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du wurdest von " + ChatColor.YELLOW + killer.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) killer.getHealth() * 10.0) / 20.0 + " ❤" + ChatColor.DARK_AQUA + " getötet.");
                    killer.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du hast " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) killer.getHealth() * 10.0) / 20.0 + " ❤" + ChatColor.DARK_AQUA + " getötet.");
                }

                PlayerManager.kills.put(player, 0);
                PlayerManager.kills.put(killer, PlayerManager.kills.get(killer) + 1);

                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);
                killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                player.getWorld().playEffect(player.getLocation(), Effect.ENDEREYE_LAUNCH, 1, 4);

                for (PotionEffect effects : killer.getActivePotionEffects()) {
                    if (effects.getType().getName().equalsIgnoreCase(PotionEffectType.REGENERATION.getName())) {
                        killer.removePotionEffect(effects.getType());
                    }
                }

                killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 255));

                killer.setLevel(killer.getLevel() + 1);

//TODO: Give players Kit-specific Items after they achieved a kill

                kitManager.getKitByPlayer(player).refreshItems(player);

                final int points = (int) Math.round((double) PlayerManager.points.get(player) / (double) 40);

                if (points >= 5) {
                    PlayerManager.points.put(killer, PlayerManager.points.get(killer) + points);
                    killer.sendMessage(Language.PREFIX.getText() + ChatColor.GREEN + "+ 5 Peanuts" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GREEN + "+ 5 Coins");
                } else {
                    PlayerManager.points.put(killer, PlayerManager.points.get(killer) + 5);
                    killer.sendMessage(Language.PREFIX.getText() + ChatColor.GREEN + "+ 5 Peanuts" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GREEN + "+ 5 Coins");
                }

                /*if (points >= 5) {
                    //PlayerManager.points.put(player, PlayerManager.points.get(player) - points);
                    player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + "- " + points + " Peanuts");
                } else {
                    if (PlayerManager.points.get(player) >= 5) {
                        //PlayerManager.points.put(player, PlayerManager.points.get(player) - 5);
                        player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + "- " + ChatColor.YELLOW + "5 " + ChatColor.YELLOW + "Punkte");
                    } else {
                        if (PlayerManager.points.get(player) > 1) {
                            player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + "- " + ChatColor.YELLOW + PlayerManager.points.get(player) + " Punkte");
                        } else {
                            if (PlayerManager.points.get(player) != 0) {
                                player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + "- " + ChatColor.YELLOW + PlayerManager.points.get(player) + " Punkt");
                            }
                        }
                        //PlayerManager.points.put(player, 0);
                    }

                }*/

//TODO: MySQL Coins&Stats
                /*Bukkit.getScheduler().runTaskAsynchronously(Main.getJavaPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        MySQL.addDeath(p);
                        MySQL.addKill(k);
                        if (points >= 5) {
                            MySQL.removePoints(p, points);
                            MySQL.addPoints(k, points);
                        } else {
                            if (MySQL.getPoints(p) >= 5) {
                                MySQL.removePoints(p, 5);
                                MySQL.addPoints(k, 5);
                            } else {
                                MySQL.setPoints(p, 0);
                                MySQL.addPoints(k, 5);
                            }
                        }
                        Coins.addCoins(k, 5);
                    }
                });*/

                if (PlayerManager.kills.get(killer) == 5) {
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 0));
                    Firework fw = (Firework) killer.getWorld().spawnEntity(killer.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + killer.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(killer) + ChatColor.DARK_AQUA + "er Killstreak.");
                }
                if (PlayerManager.kills.get(killer) == 10) {
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 1));
                    Firework fw = (Firework) killer.getWorld().spawnEntity(killer.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + killer.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(killer) + ChatColor.DARK_AQUA + "er Killstreak.");
                }
                if (PlayerManager.kills.get(killer) == 15) {
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 0));
                    Firework fw = (Firework) killer.getWorld().spawnEntity(killer.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + killer.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(killer) + ChatColor.DARK_AQUA + "er Killstreak.");
                }
                if (PlayerManager.kills.get(killer) == 20) {
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 1));
                    Firework fw = (Firework) killer.getWorld().spawnEntity(killer.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + killer.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(killer) + ChatColor.DARK_AQUA + "er Killstreak.");
                }

//TODO: Bonuscoins


            } else {
                player.sendMessage(Language.PLAYER_SUICIDE.getFormattedText());
                PlayerManager.kills.put(player, 0);

                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);

                player.getWorld().playEffect(player.getLocation(), Effect.ENDEREYE_LAUNCH, 1, 4);

            }

//TODO: MySQL Add Death

                /*Bukkit.getScheduler().runTaskAsynchronously(Main.getJavaPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        MySQL.addDeath(player);
                    }
                });*/

        } else {
            if (PlayerManager.target.get(player) != null && PlayerManager.kills.containsKey(PlayerManager.target.get(player))) {
                Player k2 = PlayerManager.target.get(player);

                if (k2.getHealth() != 20.0) {
                    player.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du wurdest von " + ChatColor.YELLOW + k2.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) (k2.getHealth() + 1.0) * 10.0) / 20.0 + " ❤ " + ChatColor.DARK_AQUA + "getötet.");
                    k2.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du hast " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) (k2.getHealth() + 1.0) * 10.0) / 20.0 + " ❤ " + ChatColor.DARK_AQUA + "getötet.");
                } else {
                    player.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du wurdest von " + ChatColor.YELLOW + k2.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) k2.getHealth() * 10.0) / 20.0 + " ❤ " + ChatColor.DARK_AQUA + "getötet.");
                    k2.sendMessage(Language.PREFIX.getText() + ChatColor.DARK_AQUA + "Du hast " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.DARK_AQUA + " mit " + ChatColor.YELLOW + (double) Math.round((int) k2.getHealth() * 10.0) / 20.0 + " ❤ " + ChatColor.DARK_AQUA + "getötet.");
                }

                PlayerManager.kills.put(player, 0);
                PlayerManager.kills.put(k2, PlayerManager.kills.get(k2) + 1);

                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);
                k2.playSound(k2.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                player.getWorld().playEffect(player.getLocation(), Effect.ENDEREYE_LAUNCH, 1, 4);

                for (PotionEffect effects : k2.getActivePotionEffects()) {
                    if (effects.getType().getName().equalsIgnoreCase(PotionEffectType.REGENERATION.getName())) {
                        k2.removePotionEffect(effects.getType());
                    }
                }
                k2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 255));

                k2.setLevel(k2.getLevel() + 1);

//TODO: Add K2 Kit-Specific Items

                kitManager.getKitByPlayer(player).refreshItems(player);

                final int points = (int) Math.round((double) PlayerManager.points.get(player) / (double) 40);

                if (points >= 5) {
                    PlayerManager.points.put(k2, PlayerManager.points.get(k2) + points);
                    k2.sendMessage(Language.PREFIX.getText() + ChatColor.GREEN + "+ " + ChatColor.YELLOW + "5" + ChatColor.GREEN + " Peanuts " + ChatColor.DARK_GRAY + "┃" + ChatColor.GREEN + " + " + ChatColor.YELLOW + "5 Coins");
                } else {
                    PlayerManager.points.put(k2, PlayerManager.points.get(k2) + 5);
                    k2.sendMessage(Language.PREFIX.getText() + ChatColor.GREEN + "+ " + ChatColor.YELLOW + "5" + ChatColor.GREEN + " Peanuts " + ChatColor.DARK_GRAY + "┃" + ChatColor.GREEN + " + " + ChatColor.YELLOW + "5 Coins");
                }

                /*if (points >= 5) {
                    //PlayerManager.points.put(player, PlayerManager.points.get(player) - points);
                    player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + " - " + points + ChatColor.GREEN + " Peanuts");
                } else {
                    if (PlayerManager.points.get(player) >= 5) {
                        //PlayerManager.points.put(player, PlayerManager.points.get(player) - 5);
                        player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + " - " + Color.YELLOW + "5" + ChatColor.GREEN + "Punkte");
                    } else {
                        if (PlayerManager.points.get(player) > 1) {
                            player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + "- " + ChatColor.YELLOW + PlayerManager.points.get(player) + ChatColor.GREEN + " Punkte");
                        } else {
                            if (PlayerManager.points.get(player) != 0) {
                                player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + "- " + ChatColor.YELLOW + PlayerManager.points.get(player) + ChatColor.GREEN + " Punkt");
                            }
                        }
                       // PlayerManager.points.put(player, 0);
                    }
                }*/

//TODO: MySQL
                /*Bukkit.getScheduler().runTaskAsynchronously(BashIt.plugin, new Runnable() {

                    @Override
                    public void run() {
                        MySQL.addDeath(p);
                        MySQL.addKill(k2);
                        if (points >= 5) {
                            MySQL.removePoints(p, points);
                            MySQL.addPoints(k2, points);
                        } else {
                            if (MySQL.getPoints(p) >= 5) {
                                MySQL.removePoints(p, 5);
                                MySQL.addPoints(k2, 5);
                            } else {
                                MySQL.setPoints(p, 0);
                                MySQL.addPoints(k2, 5);
                            }
                        }
                        Coins.addCoins(k2, 5);
                    }
                });*/

                if (PlayerManager.kills.get(k2) == 5) {
                    k2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0));
                    k2.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 0));
                    Firework fw = (Firework) k2.getWorld().spawnEntity(k2.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + k2.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(k2) + ChatColor.DARK_AQUA + "er Killstreak.");
                }
                if (PlayerManager.kills.get(k2) == 10) {
                    k2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
                    k2.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 1));
                    Firework fw = (Firework) k2.getWorld().spawnEntity(k2.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + k2.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(k2) + ChatColor.DARK_AQUA + "er Killstreak.");
                }
                if (PlayerManager.kills.get(k2) == 15) {
                    k2.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 0));
                    Firework fw = (Firework) k2.getWorld().spawnEntity(k2.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + k2.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(k2) + ChatColor.AQUA + "er Killstreak.");
                }
                if (PlayerManager.kills.get(k2) == 20) {
                    k2.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 1));
                    Firework fw = (Firework) k2.getWorld().spawnEntity(k2.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwmeta = fw.getFireworkMeta();
                    FireworkEffect.Builder fwbuilder = FireworkEffect.builder();
                    fwbuilder.withTrail();
                    fwbuilder.withFlicker();
                    fwbuilder.withFade(Color.ORANGE);
                    fwbuilder.withColor(Color.YELLOW);
                    fwbuilder.withColor(Color.RED);
                    fwbuilder.with(FireworkEffect.Type.BALL_LARGE);
                    fwmeta.addEffects(fwbuilder.build());
                    fwmeta.setPower(1);
                    fw.setFireworkMeta(fwmeta);
                    Bukkit.broadcastMessage(Language.PREFIX.getText() + k2.getDisplayName() + ChatColor.DARK_AQUA + " hat eine " + ChatColor.YELLOW + PlayerManager.kills.get(k2) + ChatColor.DARK_AQUA + "er §3Killstreak.");
                }

//TODO: Add Bonus Coins
                /*if (BashIt.kills.get(k2) == 5 | BashIt.kills.get(k2) == 10 | BashIt.kills.get(k2) == 15 | BashIt.kills.get(k2) == 20 | BashIt.kills.get(k2) == 25 | BashIt.kills.get(k2) == 30 | BashIt.kills.get(k2) == 35 | BashIt.kills.get(k2) == 40 | BashIt.kills.get(k2) == 45 | BashIt.kills.get(k2) == 50) {
                    final int bonus = 10;

                    k2.sendMessage(BashIt.prefix + "§a+ §e" + bonus + " Coins");

                    Bukkit.getScheduler().runTaskAsynchronously(BashIt.plugin, new Runnable() {

                        @Override
                        public void run() {
                            Coins.addCoins(k2, bonus);
                        }
                    });
                } */


            } else {
                player.sendMessage(Language.PREFIX.getText() + ChatColor.RED + "Du bist gestorben.");

                PlayerManager.kills.put(player, 0);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);
                player.getWorld().playEffect(player.getLocation(), Effect.ENDEREYE_LAUNCH, 1, 4);

//TODO: Add MySQL Death
                /*Bukkit.getScheduler().runTaskAsynchronously(BashIt.plugin, new Runnable() {

					@Override
					public void run() {
						MySQL.addDeath(p);
					}
				});*/


            }
        }

        PlayerManager.target.put(PlayerManager.target.get(player), null);
        PlayerManager.target.put(player, null);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getJavaPlugin(), new Runnable() {

            @Override
            public void run() {
                if (player.isOnline()) {
                    ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                }
            }
        }, 5);
        testScoreboardManager.updateIngameScoreboard();
    }
}
