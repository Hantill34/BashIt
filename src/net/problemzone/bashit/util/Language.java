package net.problemzone.bashit.util;

import org.bukkit.ChatColor;

public enum Language {

    PREFIX(ChatColor.LIGHT_PURPLE + "BashIt " + ChatColor.DARK_GRAY + "» "),

    JOIN_MESSAGE(ChatColor.GRAY + "Spielmodus " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "BashIt " + ChatColor.RESET + ChatColor.GRAY + "erfolgreich beigetreten!"),
    JOIN(ChatColor.GREEN + "» " + ChatColor.WHITE + "%s"),
    FIGHT_START("Der Kampf beginnt in: " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sekunden"),

    QUIT_MESSAGE(ChatColor.RED + "« " + ChatColor.GRAY + "%s"),
    KAMPFPHASE(ChatColor.GRAY + "Die " + ChatColor.RED + "Kampfphase " + ChatColor.GRAY + "hat begonnen!"),
    GLOBAL_KILLSTREAK(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + "%s" + ChatColor.RED + " hat eine Killstreak von " + ChatColor.GOLD + "%d " + ChatColor.RED + "erreicht!"),
    KILLER(ChatColor.GRAY + "Du hast " + ChatColor.GOLD + "%s " + ChatColor.GRAY + "getötet!"),
    PLAYER_DEATH(ChatColor.GOLD + "%s" + ChatColor.GRAY +  " starb. Wie auch immer..."),
    PLAYER_DEATH_BY_PLAYER(ChatColor.GOLD + "%s" + ChatColor.GRAY +  " hat " + ChatColor.GOLD + "%s" + ChatColor.GRAY + " die Ehre genommen."),
    WINNER_TITLE(ChatColor.GOLD + "%s " + ChatColor.GRAY + "hat das Spiel gewonnen!"),
    ROUND_CHANGE(ChatColor.RED + "Nächste Runde startet in: " + ChatColor.YELLOW + "%d" + ChatColor.RED + " Sekunden"),
    Round_END("Die Runde endet in: " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sekunden"),
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
