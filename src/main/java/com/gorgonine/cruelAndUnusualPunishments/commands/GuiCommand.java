package com.gorgonine.cruelAndUnusualPunishments.commands;

import com.gorgonine.cruelAndUnusualPunishments.gui.GuiManager;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class GuiCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            if (args.length >= 2) {
                Punishment p = Punishments.getPunishmentFromName(args[0]);

                Player victim = Bukkit.getPlayer(args[1]);
                p.execute(victim);

                return true;
            }else{
                sender.sendMessage("Brah is a server that used too little arguments");
                return false;
            }
        }

        if(args.length == 0){
            GuiManager.getPunishmentListGui().show((HumanEntity) sender);
            return true;
        }else if (args.length >= 2){
            Punishment p = Punishments.getPunishmentFromName(args[0]);

            Player victim = Bukkit.getPlayer(args[1]);
            p.execute((Player) sender, victim);

            return true;
        }else{
            sender.sendMessage("Improper usage of command 😂");
            return false;
        }

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(args.length == 1){
            return Punishments.getPunishmentNames();
        } else if (args.length == 2) {
            return null;
        }else{
            return Arrays.asList("");
        }
    }
}
