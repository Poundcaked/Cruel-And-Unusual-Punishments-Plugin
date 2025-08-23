package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class KrakenPunishment extends Punishment {
    public KrakenPunishment() {
        super("KrakenPunishment", ItemStack.of(Material.SQUID_SPAWN_EGG), "Summon the might of the sea towards your victim");
    }

    @Override
    public void execute(Player executor, Player victim) {
        World world = victim.getWorld();
        Random random = new Random();
        world.setStorm(true);
        world.setThundering(true);
        for (int i = 0; i < random.nextInt(60,80)*20; i++) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> world.spawnEntity(new Location(world, victim.getX() + random.nextInt(-32,32), victim.getY() + random.nextInt(32), victim.getZ() + random.nextInt(-32,32)), EntityType.SQUID).setRotation(random.nextFloat(-180.0F,180.0F), random.nextFloat(-90.0F,90.0F)), i);
        }


        for (int i = 0; i < 60; i++) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> world.spawnEntity(new Location(world, victim.getX() + random.nextInt(-32,32), victim.getY(), victim.getZ() + random.nextInt(-32,32)), EntityType.LIGHTNING_BOLT), i*20+ random.nextInt(-10*20,10*20));
        }

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> world.setStorm(false), 85*20);
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> world.setThundering(false), 85*20);
    }
}
