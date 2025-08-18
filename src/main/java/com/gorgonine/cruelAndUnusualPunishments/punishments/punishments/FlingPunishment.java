package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class FlingPunishment extends Punishment {
    public FlingPunishment() {
        super("FlingPunishment", ItemStack.of(Material.ARROW),"Launches victim towards a random direction, and then back");
    }

    @Override
    public void execute(Player executor, Player victim) {
        Random random = new Random();

        float randomXVel = random.nextFloat(-40.0F, 40.0F);
        float randomYVel = random.nextFloat(15.0F, 60.0F);
        float randomZVel = random.nextFloat(-40.0F, 40.0F);

        victim.setVelocity(new Vector(randomXVel,randomYVel,randomZVel));
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.setVelocity(new Vector(-randomXVel,-randomYVel,-randomZVel)), 1*20);
    }
}
