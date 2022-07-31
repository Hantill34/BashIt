package net.problemzone.bashit.modules.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Kit implements Listener {

    private String name;
    private int preis;
    private Material mat;

    public Kit(String name, int price, Material mat) {

        this.name = name;
        this.preis = preis;
        this.mat = mat;

    }

    public String getName()
    {
        return name;
    }

    public int getPreis()
    {
        return preis;
    }

    public ItemStack getItem()
    {
        ItemStack it = new ItemStack(mat);
        ItemMeta m =  it.getItemMeta();
        assert m != null;
        m.setDisplayName(name);
        it.setItemMeta(m);
        return it;
    }

    public abstract void equip(Player p);

    public abstract void refreshItems(Player p);
}
