package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class BunnyHopPunishment extends Punishment {
    public BunnyHopPunishment() {
        super("BunnyHopPunishment", ItemStack.of(Material.RABBIT_FOOT),"Forces the victim to jump 300 times");
    }

    @Override
    public void execute(Player executor, Player victim) {
        for (int i = 0; i < 300; i++) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.setVelocity(new Vector(0.0F, 0.42F, 0.0F)), 13*i);
        }
    }
}
