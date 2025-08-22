package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class HeatWavePunishment extends Punishment {
    public HeatWavePunishment() {
        super("HeatWavePunishment", ItemStack.of(Material.FIRE_CHARGE), "Blast your victim with a sweltering blaze of heat");
    }

    @Override
    public void execute(Player executor, Player victim) {
        victim.setFireTicks(20*20);

        PotionEffect slowness = PotionEffectType.getById(2).createEffect(30*20,1);
        victim.addPotionEffect(slowness);

        World world = victim.getWorld();

        AtomicInteger timer = new AtomicInteger(30*20);

        Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), task -> {
            timer.getAndDecrement();
            if(timer.get() > 0){
                for (int outer = 0; outer < 5; outer++) {
                    for (double i = 0.0; i < 2.0*Math.PI; i+=Math.PI/16.0) {
                        Location startingLoc = new Location(
                                victim.getWorld(),
                                victim.getX() + outer * Math.sin(i),
                                victim.getY()+10,
                                victim.getZ() + outer * Math.cos(i)
                        );

                        RayTraceResult rayTraceResult = world.rayTraceBlocks(startingLoc, new Vector(0,-1,0),100);
                        if(rayTraceResult != null){
                            world.setType(rayTraceResult.getHitBlock().getLocation().add(0,1,0),Material.FIRE);
                        }
                    }
                }
            }else{
                task.cancel();
            }

        }, 1L, 1L);




    }
}
