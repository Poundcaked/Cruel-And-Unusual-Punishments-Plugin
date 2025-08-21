package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class SpillInventoryPunishment extends Punishment {
    public SpillInventoryPunishment() {
        super("SpillInventoryPunishment", ItemStack.of(Material.COD_BUCKET), "Make your victim have butterfingers");
    }

    @Override
    public void execute(Player executor, Player victim) {
        for (int i = 0; i < victim.getInventory().getSize(); i++) {
            if(victim.getInventory().getItem(i) != null){
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.dropItem(finalI,victim.getInventory().getItem(finalI).getAmount()), i);
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.playSound(victim.getLocation(), Sound.ENTITY_CHICKEN_EGG,0.15F,1F), i);
            }
        }
    }
}
