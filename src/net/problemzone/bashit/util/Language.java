package net.problemzone.bashit.util;

import org.bukkit.ChatColor;

public enum Language {

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
