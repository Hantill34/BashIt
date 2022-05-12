package net.problemzone.bashit.modules.itemManager.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RocketLauncher {

    public static ItemStack createRocketlauncher(){
        ItemStack item = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ChatColor.RED + "Rocketlauncher");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Boom, Boom, Boom, Boom");
        lore.add(ChatColor.GRAY + "I want you in my Room!");
        lore.add("");
        lore.add(ChatColor.GRAY+ "Damage: " + ChatColor.RED + "12");
        lore.add(ChatColor.GRAY + "Item Ability:");
        lore.add("");
        lore.add(ChatColor.AQUA + "=>  Right-Click to spend a night together");
        lore.add("");
        lore.add(ChatColor.AQUA + "Only one Usage!");
        meta.setLore(lore);

        AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 70, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

}
