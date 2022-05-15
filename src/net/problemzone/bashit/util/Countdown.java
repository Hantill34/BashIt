package net.problemzone.bashit.util;

import net.problemzone.bashit.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Countdown {

    private static final Set<Integer> EXACT_CHAT_CALLS = new HashSet<>(Arrays.asList(60, 30, 20, 10, 5, 3, 2, 1));

    private static BukkitTask chatCountdown;

    public static void createChatCountdown(int seconds, Language text) {

        cancelChatCountdown();

        AtomicInteger remaining = new AtomicInteger(seconds);
        chatCountdown = new BukkitRunnable() {
            @Override
            public void run() {
                if (remaining.get() <= 0) {
                    Bukkit.getOnlinePlayers().forEach(Sounds.CLICK_TIMER_END::playSoundForPlayer);
                    if (!this.isCancelled()) this.cancel();
                    return;
                }
                if (EXACT_CHAT_CALLS.contains(remaining.get())) {
                    Bukkit.broadcastMessage(String.format(text.getFormattedText(), remaining.get()));
                    Bukkit.getOnlinePlayers().forEach(remaining.get() <= 3 ? Sounds.CLICK_TIMER_LAST_3::playSoundForPlayer : Sounds.CLICK::playSoundForPlayer);
                }
                if (remaining.get() <= 3) {
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle("", ChatColor.GREEN + "" + remaining.get(), 0, 20, 0));
                }
                remaining.getAndDecrement();
            }
        }.runTaskTimer(Main.getJavaPlugin(), 0, 20);
    }

    public static void cancelChatCountdown() {
        if (chatCountdown == null) return;
        if (!chatCountdown.isCancelled()) chatCountdown.cancel();
    }
}
