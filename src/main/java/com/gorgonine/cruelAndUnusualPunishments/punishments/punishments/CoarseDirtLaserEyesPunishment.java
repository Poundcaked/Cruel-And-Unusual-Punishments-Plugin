package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class CoarseDirtLaserEyesPunishment extends Punishment {
    public CoarseDirtLaserEyesPunishment() {
        super("CoarseDirtLaserEyesPunishment", ItemStack.of(Material.COARSE_DIRT), "Turn your victim into the world's worst superhero");
    }

    public void spawnParticleAlongLine(Location start, Location end, Particle particle, int pointsPerLine, int particleCount, double offsetX, double offsetY, double offsetZ, double extra, @Nullable Double data, boolean forceDisplay, @Nullable Predicate<Location> operationPerPoint) {
        double d = start.distance(end) / pointsPerLine;
        for (int i = 0; i < pointsPerLine; i++) {
            Location l = start.clone();
            Vector direction = end.toVector().subtract(start.toVector()).normalize();
            Vector v = direction.multiply(i * d);
            l.add(v.getX(), v.getY(), v.getZ());
            if (operationPerPoint == null) {
                start.getWorld().spawnParticle(particle, l, particleCount, offsetX, offsetY, offsetZ, extra, data, forceDisplay);
                continue;
            }
            if (operationPerPoint.test(l)) {
                start.getWorld().spawnParticle(particle, l, particleCount, offsetX, offsetY, offsetZ, extra, data, forceDisplay);
            }
        }
    }

    @Override
    public void execute(Player executor, Player victim) {
        AtomicInteger tick = new AtomicInteger();
        victim.playSound(victim.getLocation(),Sound.BLOCK_BEACON_DEACTIVATE,1.0F,1.0F);

        Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), task -> {
            if(tick.get() < (60*20)){
                tick.getAndIncrement();

                World world = victim.getWorld();
                RayTraceResult eyeTrace = world.rayTraceBlocks(victim.getEyeLocation(), victim.getEyeLocation().getDirection(),100);

                if(eyeTrace != null){
                    if(!eyeTrace.getHitBlock().getBlockData().getMaterial().equals(Material.COARSE_DIRT)){
                        spawnParticleAlongLine(victim.getEyeLocation().add(victim.getEyeLocation().getDirection().multiply(2)), eyeTrace.getHitBlock().getLocation(),Particle.HAPPY_VILLAGER,35,2,0D,0D,0D,0D,null,false, l -> l.getBlock().isPassable());
                        world.setType(eyeTrace.getHitBlock().getLocation(),Material.COARSE_DIRT);
                        world.playSound(eyeTrace.getHitBlock().getLocation(),Sound.BLOCK_GRASS_BREAK,1.0F,1.0F);
                    }
                }
            }else{
                task.cancel();
            }
        }, 1L, 1L);
    }
}
