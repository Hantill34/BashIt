package net.problemzone.bashit.util;

import org.bukkit.ChatColor;

public enum Language {

    PREFIX(ChatColor.YELLOW + "Bash" + ChatColor.GOLD + "It" + ChatColor.DARK_GRAY + "» "),


    JOIN(ChatColor.GREEN + "» " + ChatColor.WHITE + "%s"),
    JOIN_MESSAGE(ChatColor.DARK_AQUA + "Spielmodus " + ChatColor.YELLOW + ChatColor.BOLD + "Bash" +ChatColor.GOLD + "It " + ChatColor.RESET + ChatColor.DARK_AQUA + "erfolgreich beigetreten!"),
    QUIT_MESSAGE(ChatColor.RED + "« " + ChatColor.GRAY + "%s"),
    KIT_MESSAGE(ChatColor.GREEN + "Du hast das Kit " + "%s " + "ausgewählt."),


    FIGHT_START(ChatColor.DARK_AQUA + "Kisten spawnen in " + ChatColor.YELLOW + "%d" + ChatColor.DARK_AQUA + " Sekunden"),
    KAMPFPHASE(ChatColor.GREEN + "Kisten sind gespawnt."),



    PLAYER_DEATH(ChatColor.YELLOW + "%s" + ChatColor.RED +  " starb. Wie auch immer..."),
    PLAYER_DEATH_BY_PLAYER(ChatColor.GOLD + "%s" + ChatColor.RED +  " hat " + ChatColor.YELLOW + "%s" + ChatColor.RED + " die Ehre genommen."),
    KILLER(ChatColor.GREEN + "Du hast " + ChatColor.YELLOW + "%s " + ChatColor.GREEN+ "getötet."),
    PLAYER_SUICIDE(ChatColor.RED + "Du hast Selbstmord begangen."),


    GET_POINTS(ChatColor.GREEN + "Du hast " +ChatColor.YELLOW + "+5 Peanuts " + ChatColor.GREEN + "erhalten"),
    REMOVE_POINTS(ChatColor.RED + "Dir wurden " + ChatColor.YELLOW + "-2 Peanuts " + ChatColor.RED + "abgezogen"),


    GLOBAL_KILLSTREAK(ChatColor.DARK_AQUA + "Der Spieler " + ChatColor.YELLOW + "%s" + ChatColor.DARK_AQUA + " hat eine Killstreak von " + ChatColor.YELLOW + "%d " + ChatColor.RED + "erreicht!"),


    NO_AMMUNITION(ChatColor.RED +"Du hast keine Munition mehr."),

    ROUND_CHANGE(ChatColor.RED + "Der Server schließt in: " + ChatColor.YELLOW + "%d" + ChatColor.RED + " Sekunden"),
    Round_END(ChatColor.DARK_AQUA + "Die Runde endet in: " + ChatColor.YELLOW + "%d" + ChatColor.DARK_AQUA + " Sekunden"),





    WINNER_TITLE(ChatColor.YELLOW + "%s " + ChatColor.DARK_AQUA + "hat das Spiel gewonnen!"),


    ;

    private static final String SYSTEM_PREFIX = ChatColor.BOLD + "" + ChatColor.YELLOW + "Bash" + ChatColor.GOLD + "It" + ChatColor.DARK_GRAY + "» ";
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
