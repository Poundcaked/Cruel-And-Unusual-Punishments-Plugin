package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class RotateRandomlyPunishment extends Punishment {
    public RotateRandomlyPunishment() {
        super("RotateRandomlyPunishment", new ItemStack(Material.COMPASS),"Forces the victim to rotate in a bunch of random directions");
    }

    @Override
    public void execute(Player executor, Player victim) {
        Random random = new Random();
        for (int i = 0; i < random.nextInt(49,100); i++) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.setRotation(random.nextFloat(0.0F,360.0F) ,random.nextFloat(-90.0F,90.0F)), i);
        }
    }
}
