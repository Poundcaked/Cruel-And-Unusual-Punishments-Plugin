package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class AnvilPunishment extends Punishment {
    public AnvilPunishment() {
        super("AnvilPunishment", ItemStack.of(Material.ANVIL), "Crush your victim using the power of 49 anvils");
    }

    @Override
    public void execute(Player executor, Player victim) {
        World world = victim.getWorld();
        victim.setRotation(victim.getYaw(),-90.0F);
        victim.setVelocity(new Vector(0,0,0));
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                Location location = victim.getLocation().add(i,15,j);

                world.setType(location,Material.ANVIL);

                Bukkit.getScheduler().runTaskLater(
                        Bukkit.getPluginManager().getPlugin(PLUGIN_ID),
                        () -> {
                            RayTraceResult result = world.rayTraceBlocks(
                                    location,
                                    new Vector(0, -1, 0),
                                    50
                            );

                            if (result != null && result.getHitBlock() != null) {
                                Block hit = result.getHitBlock();
                                if (hit.getType() == Material.ANVIL || hit.getType() == Material.CHIPPED_ANVIL || hit.getType() == Material.DAMAGED_ANVIL) {
                                    hit.setType(Material.AIR);
                                }
                            }
                        },
                        50
                );
            }
        }
    }
}
