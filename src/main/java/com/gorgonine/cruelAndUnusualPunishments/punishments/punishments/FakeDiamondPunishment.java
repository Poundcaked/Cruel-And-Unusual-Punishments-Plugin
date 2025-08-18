package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class FakeDiamondPunishment extends Punishment implements Listener {
    public FakeDiamondPunishment() {
        super("FakeDiamondPunishment", new ItemStack(Material.DIAMOND), "Gives the victim a full inventory of disappearing diamonds");
    }

    @Override
    public void execute(Player executor, Player victim) {
        NamespacedKey key = new NamespacedKey(PLUGIN_ID,"artificialitem");

        for (int j = 0; j < victim.getInventory().getSize(); j++) {
            ItemStack diamonds = new ItemStack(Material.DIAMOND,64);
            ItemMeta itemMeta = diamonds.getItemMeta();
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1); //mark the item as disgusting and gross so it can be removed later
            diamonds.setItemMeta(itemMeta);
            victim.getInventory().addItem(diamonds);
        }

        victim.playSound(victim, Sound.BLOCK_NOTE_BLOCK_BELL,1.0F,1.0F);
        victim.playSound(victim, Sound.ITEM_ARMOR_EQUIP_DIAMOND,1.0F,1.0F);
        victim.playSound(victim, Sound.BLOCK_METAL_BREAK,1.0F,1.0F);
        victim.showTitle(Title.title(Component.text("Giveaway Winner Alert!"),Component.text("Inventory filled with Diamonds!").color(TextColor.color(76,252,252))));

        victim.setCanPickupItems(false);



        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> removeAllCursedItems(victim,key), 33);
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.setCanPickupItems(true), 60);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (isLocked(item)) {
            event.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event){
        CraftingInventory inv = event.getInventory();
        ItemStack[] matrix = inv.getMatrix();

        boolean hasTaggedIngredient = false;

        for (ItemStack ingredient : matrix) {
            if (ingredient != null && ingredient.hasItemMeta()) {
                if (isLocked(ingredient)) {
                    hasTaggedIngredient = true;
                    break;
                }
            }
        }

        if (hasTaggedIngredient) {
            ItemStack result = inv.getResult();
            if (result != null) {
                ItemMeta resultMeta = result.getItemMeta();
                resultMeta.getPersistentDataContainer().set(new NamespacedKey(PLUGIN_ID, "artificialitem"), PersistentDataType.BYTE, (byte) 1);
                result.setItemMeta(resultMeta);
                inv.setResult(result);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        ItemStack current = event.getCurrentItem();
        ItemStack cursor = event.getCursor();

        if (event.getClickedInventory() != null
                && event.getClickedInventory().getType() != InventoryType.PLAYER
                || event.getClickedInventory().getType() != InventoryType.CRAFTING

        ) {
            if (isLocked(current) || isLocked(cursor)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        ItemStack dragged = event.getOldCursor();
        if (isLocked(dragged)) {
            for (int slot : event.getRawSlots()) {
                if (slot < event.getInventory().getSize()) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    private boolean isLocked(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(new NamespacedKey(PLUGIN_ID, "artificialitem"), PersistentDataType.BYTE);
    }

    private void removeAllCursedItems(Player victim, NamespacedKey key){
        for (int i = 0; i < victim.getInventory().getSize(); i++) {
            if(victim.getInventory().getItem(i) == null || !victim.getInventory().getItem(i).hasItemMeta()) continue;

            ItemStack itemAtSlot = victim.getInventory().getItem(i);
            if(isLocked(itemAtSlot)){
                if(isLocked(itemAtSlot)){
                    int finalI = i;
                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.getInventory().clear(finalI), i);
                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.playSound(victim,Sound.ENTITY_CHICKEN_EGG,0.7F,1.0F), i);
                }
            }
        }
    }
}
