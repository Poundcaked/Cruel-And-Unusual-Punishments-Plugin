package com.gorgonine.cruelAndUnusualPunishments.commands;

import com.gorgonine.cruelAndUnusualPunishments.gui.GuiManager;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
            if(Punishments.getPunishmentFromName(args[0]) != null){
                Punishment p = Punishments.getPunishmentFromName(args[0]);
                if(Bukkit.getPlayer(args[1]) != null){
                    Player victim = Bukkit.getPlayer(args[1]);
                    p.execute((Player) sender, victim);

                    return true;
                }else{
                    sender.sendMessage("Improper usage of command, invalid player name! 🙄");
                }
            }else{
                sender.sendMessage("Improper usage of command, invalid punishment name! 🙄");
            }
        }else{
            sender.sendMessage("Improper usage of command, too little arguments! 😂");
            return false;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(args.length == 1){
            ArrayList<String> punishmentNames = Punishments.getPunishmentNames();
            ArrayList<String> filteredPunishmentNames = new ArrayList<String>();
            for (int i = 0; i < punishmentNames.size(); i++) {
                if(punishmentNames.get(i).toLowerCase().startsWith(args[0].toLowerCase())){
                    filteredPunishmentNames.add(punishmentNames.get(i));
                }
            }
            return filteredPunishmentNames;
        } else if (args.length == 2) {
            return null;
        }else{
            return Arrays.asList("");
        }
    }


}
