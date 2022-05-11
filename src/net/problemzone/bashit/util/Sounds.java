package net.problemzone.bashit.util;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public enum Sounds{
    EFFECT_BAD_LUCK(Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1),
    EFFECT_SPEED(Sound.ENTITY_PLAYER_LEVELUP, 1),
    EFFECT_JUMP(Sound.ENTITY_ARROW_HIT_PLAYER,1),
    EFFECT_REGENARATION(Sound.BLOCK_ANVIL_USE, 1),

    RLAUNCHER_SHOOT(Sound.ENTITY_WITHER_SHOOT, 1),

    CLICK(Sound.UI_BUTTON_CLICK, 1);





    private final Sound sound;
    private final float pitch;

    Sounds(Sound sound, float pitch) {
        this.sound = sound;
        this.pitch = pitch;

    }

    public void playSoundForPlayer(Player player){
        player.playSound(player.getLocation(), sound, SoundCategory.AMBIENT, 1, pitch);
    }
}
