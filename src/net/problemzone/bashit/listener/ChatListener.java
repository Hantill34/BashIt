package net.problemzone.bashit.listener;

import net.problemzone.bashit.modules.GameManager;
import net.problemzone.bashit.util.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class ChatListener implements Listener {

    private final GameManager gameManager;


    public ChatListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.sendMessage("");
        player.sendMessage(Language.JOIN_MESSAGE.getFormattedText());
        player.sendMessage("");

        if(gameManager.getGameState() == GameManager.GameState.RUNNING){
            event.setJoinMessage(String.format(Language.JOIN.getText(), player.getName()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(gameManager.getGameState() == GameManager.GameState.RUNNING){
            event.setQuitMessage(String.format(Language.QUIT_MESSAGE.getText(), player.getName()));
        }
    }



    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if(Objects.requireNonNull(player).getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
            if(killer != null){
                event.setDeathMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), killer.getName(), player.getName()));
            }else {
                event.setDeathMessage(String.format(Language.PLAYER_DEATH.getFormattedText(), player.getName()));
            }
        }else{
            event.setDeathMessage(String.format(Language.PLAYER_DEATH.getFormattedText(), player.getName()));
        }
    }




}
