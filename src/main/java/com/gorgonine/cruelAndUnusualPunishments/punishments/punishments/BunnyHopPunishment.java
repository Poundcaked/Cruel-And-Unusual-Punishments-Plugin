package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class BunnyHopPunishment extends Punishment {
    public BunnyHopPunishment() {
        super("BunnyHopPunishment", ItemStack.of(Material.RABBIT_FOOT),"Forces the victim to jump 300 times");
    }

    public boolean isGrounded(Player player) {
        Location loc = player.getLocation();
        World world = player.getWorld();

        Block blockBelow = world.getBlockAt(loc.subtract(0, 0.1, 0));

        return blockBelow.getType().isSolid();

    }

    @Override
    public void execute(Player executor, Player victim) {
        AtomicInteger tick = new AtomicInteger();
        Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), task -> {
            tick.getAndIncrement();

            if(tick.get() < 60*60){
                if(isGrounded(victim)){
                    victim.setVelocity(new Vector(victim.getVelocity().getX(), 0.42F, victim.getVelocity().getZ()));
                }
            }else{
                task.cancel();
            }

        }, 1L, 1L);
    }
}
