package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class KitOfShamePunishment extends Punishment {
    public KitOfShamePunishment() {
        super("KitOfShamePunishment", ItemStack.of(Material.LEATHER_CHESTPLATE), "Force your victim to wear the world's worst armor set");
    }

    @Override
    public void execute(Player executor, Player victim) {
        victim.dropItem(EquipmentSlot.HEAD,victim.getInventory().getItem(EquipmentSlot.HEAD).getAmount());
        victim.dropItem(EquipmentSlot.CHEST,victim.getInventory().getItem(EquipmentSlot.CHEST).getAmount());
        victim.dropItem(EquipmentSlot.LEGS,victim.getInventory().getItem(EquipmentSlot.LEGS).getAmount());
        victim.dropItem(EquipmentSlot.FEET,victim.getInventory().getItem(EquipmentSlot.FEET).getAmount());

        ItemStack head = new ItemStack(Material.CARVED_PUMPKIN);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) chest.getItemMeta();
        leatherArmorMeta.setColor(Color.fromRGB(80, 99, 47));

        chest.setItemMeta(leatherArmorMeta);
        legs.setItemMeta(leatherArmorMeta);
        boots.setItemMeta(leatherArmorMeta);

        head.addEnchantment(Enchantment.BINDING_CURSE,1);
        chest.addEnchantment(Enchantment.BINDING_CURSE,1);
        legs.addEnchantment(Enchantment.BINDING_CURSE,1);
        boots.addEnchantment(Enchantment.BINDING_CURSE,1);

        chest.addEnchantment(Enchantment.UNBREAKING,3);
        legs.addEnchantment(Enchantment.UNBREAKING,3);
        boots.addEnchantment(Enchantment.UNBREAKING,3);

        victim.getInventory().setArmorContents(new ItemStack[]{boots,legs,chest,head});
        victim.playSound(victim.getLocation(),Sound.ITEM_ARMOR_EQUIP_LEATHER,1.0F,1.0F);
    }
}
