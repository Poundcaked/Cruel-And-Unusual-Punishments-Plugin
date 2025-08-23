package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class OrbitalLaserPunishment extends Punishment {
    public OrbitalLaserPunishment() {
        super("OrbitalLaserPunishment", ItemStack.of(Material.TNT), "Strike your victim with a 500 Pisrat-Watt Laser (WARNING: DESTRUCTIVE)");
    }

    public void spawnParticleAlongLine(Location start, Location end, Particle particle, int pointsPerLine, int particleCount, double offsetX, double offsetY, double offsetZ, double extra, @Nullable Object data, boolean forceDisplay, @Nullable Predicate<Location> operationPerPoint) {
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
        AtomicBoolean exploded = new AtomicBoolean(false);
        victim.playSound(victim.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0F, 0.7F);
        World world = victim.getWorld();

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> {
            victim.playSound(victim.getLocation(),Sound.ENTITY_GENERIC_EXPLODE, 16.0F, 0.25F);
            victim.playSound(victim.getLocation(),Sound.ENTITY_GENERIC_EXPLODE, 15.0F, 0.35F);
            victim.playSound(victim.getLocation(),Sound.ENTITY_GENERIC_EXPLODE, 14.0F, 0.55F);
            for (int i = 0; i < 8; i++) {
                victim.getWorld().createExplosion(victim.getLocation().add(0,i*-(15-(8-i)),0),64F-(i*9F),true,true);
                victim.playSound(victim.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,12.0F,0.7F+(i*0.2F));
            }
            exploded.set(true);

            for (int i = 0; i < 80; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> {
                    for (double j = 0; j < 2 * Math.PI; j+= Math.PI / 16) {
                        RayTraceResult rayTraceResult = world.rayTraceBlocks(
                                new Location(
                                        world,
                                        victim.getLocation().getBlockX() + ((4*(Math.floor((double) finalI /4.0))) *Math.sin(j)),
                                        victim.getLocation().getBlockY() + 30,
                                        victim.getLocation().getBlockZ() + ((4*(Math.floor((double) finalI /4.0)))*Math.cos(j))),
                                new Vector(0, -1 ,0),
                                80,
                                FluidCollisionMode.ALWAYS,
                                true
                        );
                        if(rayTraceResult != null){
                            Location loc = rayTraceResult.getHitBlock().getLocation().clone();

                            world.setType(loc, Material.MAGMA_BLOCK);
                            world.spawnParticle(Particle.EXPLOSION_EMITTER, loc, 3,0.1D,0.1D,0.1D,0,null,true);
                        }

                    }
               }, i);
            }
        }, 5 * 20);

        Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), task -> {
            if(!exploded.get()){
                for (double j = 0; j < 2 * Math.PI; j+= Math.PI/8) {
                    spawnParticleAlongLine(victim.getLocation().add(Math.sin(j),victim.getY() + 110,Math.cos(j)),victim.getLocation().add(Math.sin(j),0,Math.cos(j)),Particle.DUST,400,1,0D,0D,0D, 0D, new Particle.DustOptions(Color.RED,1F), true, null);
                }
            }else{
                task.cancel();
            }
        }, 7L, 7L);


    }
}
