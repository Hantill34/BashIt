package net.problemzone.bashit.util;

import org.bukkit.ChatColor;

public enum Language {

    JOIN_MESSAGE(ChatColor.GRAY + "Spielmodus " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "BashIt " + ChatColor.RESET + ChatColor.GRAY + "erfolgreich beigetreten!"),
    JOIN(ChatColor.GREEN + "» " + ChatColor.WHITE + "%s"),

    QUIT_MESSAGE(ChatColor.RED + "« " + ChatColor.GRAY + "%s"),

    PLAYER_DEATH(ChatColor.WHITE + "%s" + ChatColor.GRAY +  " starb. Wie auch immer..."),
    PLAYER_DEATH_BY_PLAYER(ChatColor.WHITE + "%s" + ChatColor.GRAY +  " hat " + ChatColor.WHITE + "%s" + ChatColor.GRAY + " die Ehre genommen."),

    ;

    private static final String SYSTEM_PREFIX = ChatColor.LIGHT_PURPLE + "BashIt " + ChatColor.DARK_GRAY + "» ";
    private final String text;

    Language(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getFormattedText() {
        return SYSTEM_PREFIX + ChatColor.GRAY + text;
    }


}
