package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class MeatGrinderPunishment extends Punishment {
    public MeatGrinderPunishment() {
        super("MeatGrinderPunishment", ItemStack.of(Material.BEEF), "Take your victim on a tour of a ConAgra slaughterhouse facility");
    }


    @Override
    public void execute(Player executor, Player victim) {
        World world = victim.getWorld();
        Location location = new Location(world, victim.getX(), victim.getY(), victim.getZ());


        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 3; x++) {
                for (int z = 0; z < 3; z++) {
                    world.setType(location.getBlock().getLocation().add(x-1,y,z-1),Material.GLASS);
                }
            }
        }

        for (int i = 0; i < 20; i++) {
            world.setType(location.getBlock().getLocation().add(0,i,0),Material.AIR);
        }

        victim.teleport(location.getBlock().getLocation().add(.5,0,.5));

        for (int i = 0; i < 256; i++) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> {
                world.spawnEntity(location.getBlock().getLocation().add(.5, 20,.5), EntityType.COW);
                world.spawnEntity(location.getBlock().getLocation().add(.5, 20,.5), EntityType.SHEEP);
                world.spawnEntity(location.getBlock().getLocation().add(.5, 20,.5), EntityType.PIG);
            }, i);
        }
    }
}
