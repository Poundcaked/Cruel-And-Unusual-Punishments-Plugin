package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class TeleportationPunishment extends Punishment {
    public TeleportationPunishment() {
        super("TeleportationPunishment", ItemStack.of(Material.ENDER_PEARL), "Simulate hundreds of ender pearls being thrown by your victim");
    }

    @Override
    public void execute(Player executor, Player victim) {
        Random random = new Random();
        World world = victim.getWorld();
        for (int i = 0; i < random.nextInt(25,125); i++) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> {
                EnderPearl enderPearl = (EnderPearl) world.spawnEntity(victim.getLocation().add(0.0,3.0,0.0),EntityType.ENDER_PEARL, CreatureSpawnEvent.SpawnReason.ENDER_PEARL);
                victim.playSound(victim.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW,1.0F,1.0F);
                enderPearl.setVelocity(
                        new Vector(
                                random.nextDouble(-1.87,1.87),
                                random.nextDouble(1.0,2.25),
                                random.nextDouble(-1.87,1.87)
                        )
                );
                enderPearl.setShooter(victim.getPlayer());
            }, i);
        }
    }
}
