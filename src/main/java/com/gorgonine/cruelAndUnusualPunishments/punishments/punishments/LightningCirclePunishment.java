package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LightningCirclePunishment extends Punishment {
    public LightningCirclePunishment() {
        super("LightningCirclePunishment", ItemStack.of(Material.LIGHTNING_ROD), "Spawn a shrinking circle of lightning to kill your victim");
    }

    @Override
    public void execute(Player executor, Player victim) {
            for (int k = 10; k >= 0; k-=1) {
                    for (double j = 0; j < 2*Math.PI; j+= Math.PI/16) {
                        victim.getWorld().spawnEntity(
                                new Location(
                                        victim.getWorld(),
                                        victim.getX() + k * Math.sin(j),
                                        victim.getY(),
                                        victim.getZ() + k * Math.cos(j)
                                ),
                                EntityType.LIGHTNING_BOLT
                        );
                    }
            }
            victim.setHealth(0.0);

    }
}
