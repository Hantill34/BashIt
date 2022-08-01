package net.problemzone.bashit.modules.kits;

import net.problemzone.bashit.modules.kits.Kits.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.*;

public class KitManager {


    private final List<Kit> kits = new ArrayList<>();
    public final Map<Player, Kit> playerKitMap = new HashMap<>();
    public static HashMap<UUID, Player> lightnings = new HashMap<UUID, Player>();
    public static HashMap <Block, Player> c4 = new HashMap<Block, Player>();


    public KitManager() {
        //kits.add();
        kits.add(new Pilot());
        kits.add(new Ritter());
        kits.add(new Mage());
        kits.add(new Attentäter());
       // kits.add(new Troll());
    }

    public void equipPlayer(Player player) {
        player.getInventory().clear();
        playerKitMap.get(player).equip(player);
    }

    public void giveKitSelector(Player player) {

        ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta starItemMeta = star.getItemMeta();

        assert starItemMeta != null;
        starItemMeta.setDisplayName(ChatColor.DARK_RED + "Operatorauswahl");
        star.setItemMeta(starItemMeta);

        player.getInventory().setItem(0, star);
        playerKitMap.put(player, new Ritter());
    }

    /*public void removeKitSelector(Player player){
        for(ItemStack item : player.getInventory()){
            if((item.getItemMeta()).getDisplayName().equals(ChatColor.DARK_RED + "Operatorauswahl")){
                item.setAmount(0);
                player.updateInventory();
            }
        }
    }*/

    public void openOperatorInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_RED + "Wähle deinen Operator");

        ItemStack wartung = new ItemStack(Material.BARRIER);
        ArrayList<String> lWartung = new ArrayList<String>();

        lWartung.add(ChatColor.RED + "Dieses Kit befindet sich zur Zeit in Wartung");
        lWartung.add(ChatColor.RED + "und ist momentan nicht verfügbar.");

        ItemMeta mWartung = wartung.getItemMeta();
        assert mWartung != null;
        mWartung.setDisplayName(ChatColor.BLACK + "");
        mWartung.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        mWartung.setLore(lWartung);
        wartung.setItemMeta(mWartung);

        ItemStack ritter = new ItemStack(Material.IRON_SWORD);
        ItemStack mage = new ItemStack(Material.BLAZE_POWDER);
        ItemStack pilot = new ItemStack(Material.FIREWORK_ROCKET);
        ItemStack attentäter = new ItemStack(Material.SHEARS);

        ArrayList<String> lRitter = new ArrayList<String>();
        ArrayList<String> lMage = new ArrayList<String>();
        ArrayList<String> lPilot = new ArrayList<String>();
        ArrayList<String> lAttentäter = new ArrayList<String>();

        lRitter.add("");
        lRitter.add(ChatColor.YELLOW + "Standart-Kit");
        lRitter.add("");
        lRitter.add(ChatColor.YELLOW + "Enthält:");
        lRitter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Eisenschwert");
        lRitter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Angel");
        lRitter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Kettenhelm");
        lRitter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Eisenbrustplatte");
        lRitter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Kettenhose");
        lRitter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Kettenschuhe");

        lMage.add("");
        lMage.add(ChatColor.YELLOW + "Fähigkeiten:");
        lMage.add(ChatColor.DARK_GRAY + "▸ " + ChatColor.DARK_AQUA + "Zauberstab + Linksklick " + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Feuerball");
        lMage.add(ChatColor.DARK_GRAY + "▸" + ChatColor.DARK_AQUA + " Zauberstab + Schleichen + Linksklick " + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Blitz");
        lMage.add("");
        lMage.add(ChatColor.YELLOW + "Enthält:");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Knüppel" + ChatColor.GRAY + " (Schärfe III)");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Zauberstab");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Splash Potion of Instant Heal");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 2x" + ChatColor.DARK_AQUA + " Splash Potion of Instant Damage");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederhelm" + ChatColor.GRAY + " (Protection II)");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederbrustplatte" +ChatColor.GRAY + " (Protection II)");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederhose" +ChatColor.GRAY + " (Protection II)");
        lMage.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederschuhe" + ChatColor.GRAY + " (Protection II)");

        lPilot.add("");
        lPilot.add(ChatColor.YELLOW + "Fähigkeiten:");
        lPilot.add(ChatColor.DARK_GRAY + "▸ " + ChatColor.DARK_AQUA + "Rechtsklick " + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Katapultiere dich in die Luft");
        lPilot.add("");
        lPilot.add(ChatColor.YELLOW + "Enthält:");
        lPilot.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Eisenaxt" + ChatColor.GRAY + " (Schärfe III)");
        lPilot.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 4x" + ChatColor.DARK_AQUA + " Jetpack");
        lPilot.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Goldener Apfel");
        lPilot.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederhelm");
        lPilot.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Kettenbrustplatte");
        lPilot.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederhose");
        lPilot.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederschuhe");

        lAttentäter.add("");
        lAttentäter.add(ChatColor.YELLOW + "Fähigkeiten:");
        lAttentäter.add(ChatColor.DARK_GRAY + "▸ " + ChatColor.DARK_AQUA + "Rechtsklick + Nuke" + ChatColor.DARK_GRAY + " » " + ChatColor.YELLOW + "Zünde eine Nuke");
        lAttentäter.add("");
        lAttentäter.add(ChatColor.YELLOW + "Enthält:");
        lAttentäter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Messer" + ChatColor.GRAY + " (Schärfe V)");
        lAttentäter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 2x" + ChatColor.DARK_AQUA + " Nuke");
        lAttentäter.add(ChatColor.GRAY + "-" + ChatColor.YELLOW + " 1x" + ChatColor.DARK_AQUA + " Lederbrustplatte" + ChatColor.GRAY + " (Protection IV)");

        ItemMeta mMage = mage.getItemMeta();
        assert mMage != null;
        mMage.setDisplayName(ChatColor.LIGHT_PURPLE + "Mage");
        mMage.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        mMage.setLore(lMage);
        mage.setItemMeta(mMage);

        ItemMeta mRitter = ritter.getItemMeta();
        assert mRitter != null;
        mRitter.setDisplayName(ChatColor.YELLOW + "Ritter");
        mRitter.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        mRitter.setLore(lRitter);
        ritter.setItemMeta(mRitter);

        ItemMeta mPilot = pilot.getItemMeta();
        assert mPilot != null;
        mPilot.setDisplayName(ChatColor.AQUA + "Pilot");
        mPilot.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        mPilot.setLore(lPilot);
        pilot.setItemMeta(mPilot);

        ItemMeta mAttentäter = attentäter.getItemMeta();
        assert mAttentäter != null;
        mAttentäter.setDisplayName(ChatColor.RED + "Attentäter");
        mAttentäter.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        mAttentäter.setLore(lAttentäter);
        attentäter.setItemMeta(mAttentäter);

    //2 , 6 , 13 , 20 , 24
        inv.setItem(2, pilot);
        inv.setItem(6, ritter);
        inv.setItem(13, mage);
        inv.setItem(20, attentäter);
        //inv.setItem(24, kits.get(4).getItem());
        inv.setItem(24, wartung);

        player.openInventory(inv);
    }

    public Kit getKitByName(String name) {
        for (Kit kit : kits) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return kit;
            }
        }
        return kits.get(0);
    }

    public List<Kit> getKits() {
        return kits;
    }

    public void putPlayerInMap(Player player, Kit kit) {
        playerKitMap.put(player, kit);
    }

    public Kit getKitByPlayer(Player player) {
        return playerKitMap.get(player);
    }

    public Kit getPlayerByKit(Kit kit){
        return playerKitMap.get(kit);
    }

}
