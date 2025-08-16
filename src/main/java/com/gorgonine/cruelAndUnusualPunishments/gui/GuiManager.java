package com.gorgonine.cruelAndUnusualPunishments.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class GuiManager {
    private static final ChestGui punishmentListGui = new ChestGui(3, "Punishments");
    private static final ChestGui playerListGui = new ChestGui(3, "Pick a Player");

    public static final  PaginatedPane punishmentPages = new PaginatedPane(0, 0, 9, 6);
    public static final  PaginatedPane playerListPages = new PaginatedPane(0, 0, 9, Bukkit.getMaxPlayers()/9);

    private static final OutlinePane punishmentPane = new OutlinePane(0, 0, 9,3);
    private static final OutlinePane playerPane = new OutlinePane(0, 0, 9,3);

    private static final Map<UUID, Punishment> chosenPunishments = new HashMap<>();

    public static void init(){
        punishmentListGui.setOnGlobalClick(event -> event.setCancelled(true));
        playerListGui.setOnGlobalClick(event -> event.setCancelled(true));

        punishmentListGui.addPane(punishmentPages);
        playerListGui.addPane(playerListPages);

        punishmentPages.addPage(punishmentPane);
        playerListPages.addPage(playerPane);

        for(Punishment p : Punishments.getPunishments()){
            ItemStack punishment = new ItemStack(p.getItemStack());
            ItemMeta punishmentItemMeta = punishment.getItemMeta();
            punishmentItemMeta.setDisplayName(p.getName());
            punishment.setItemMeta(punishmentItemMeta);

            punishmentPane.addItem(new GuiItem(punishment, event -> {
                chosenPunishments.put(event.getWhoClicked().getUniqueId(), p);
                refreshPlayerGui();
                playerListGui.show(event.getWhoClicked());
            }));
            punishmentListGui.update();
        }
    }

    public static ChestGui getPunishmentListGui(){
        return punishmentListGui;
    }

    public static void refreshPlayerGui(){
        playerPane.clear();
        for(Player player : Bukkit.getOnlinePlayers()){
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
            skullMeta.setDisplayName(player.getName());
            skull.setItemMeta(skullMeta);

            playerPane.addItem(new GuiItem(skull, event -> {
                Punishment chosen = chosenPunishments.get(event.getWhoClicked().getUniqueId());
                if(chosen != null){
                    event.getWhoClicked().closeInventory();
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "punish " + chosen.getName() + " " + player.getPlayer().getName());

                    event.getWhoClicked().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Punished "+player.getName()+" with "+chosen.getName());
                }
            }));
            playerListGui.update();
        }
    }

}
