package com.gorgonine.cruelAndUnusualPunishments.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class GuiManager {

    private static final Map<UUID, Punishment> chosenPunishments = new HashMap<>();
    private static final ChestGui punishmentListGui = new ChestGui(3, "Punishments");
    private static final ChestGui playerListGui = new ChestGui(3, "Pick a Player");

    public static final  PaginatedPane punishmentPages = new PaginatedPane(0, 0, 9, 6);
    public static final  PaginatedPane playerListPages = new PaginatedPane(0, 0, 9, Math.min(6, (Bukkit.getMaxPlayers() + 8) / 9));

    private static final OutlinePane punishmentPane = new OutlinePane(0, 0, 9,3);
    private static final OutlinePane playerPane = new OutlinePane(0, 0, 9,3);

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

            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.text(p.getDesc()).decorate(TextDecoration.BOLD).color(TextColor.color(60, 104, 250)));

            punishmentItemMeta.lore(lore);
            punishment.setItemMeta(punishmentItemMeta);

            punishmentPane.addItem(new GuiItem(punishment, event -> {
                chosenPunishments.put(event.getWhoClicked().getUniqueId(), p);
                refreshPlayerGui((Player) event.getWhoClicked());
                playerListGui.show(event.getWhoClicked());
            }));
            punishmentListGui.update();
        }
    }

    public static ChestGui getPunishmentListGui(){
        return punishmentListGui;
    }

    public static void refreshPlayerGui(Player playerWhoInitiated){
        playerPane.clear();

        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);

        Arrays.sort(players, Comparator.comparing(Player::getName)); //sort player names alphabetically

        for(Player player : players){
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
            skullMeta.setDisplayName(player.getName());
            if(player.getUniqueId().equals(playerWhoInitiated.getUniqueId())){
                skullMeta.setLore(List.of("You!"));
            }
            skull.setItemMeta(skullMeta);

            playerPane.addItem(new GuiItem(skull, event -> {
                Punishment chosen = chosenPunishments.get(event.getWhoClicked().getUniqueId());
                if(chosen != null){
                    event.getWhoClicked().closeInventory();
                    //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "punish " + chosen.getName() + " " + player.getPlayer().getName());

                    chosen.execute((Player) event.getWhoClicked(),player);
                    event.getWhoClicked().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Punished "+player.getName()+" with "+chosen.getName());
                }
            }));
            playerListGui.update();
        }
    }

}
