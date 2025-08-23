package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CarbonitePunishment extends Punishment {
    public CarbonitePunishment() {
        super("CarbonitePunishment", ItemStack.of(Material.TINTED_GLASS), "Imprison your victim in a cylinder of Carbonite*");
    }

    @Override
    public void execute(Player executor, Player victim) {
        victim.setVelocity(new Vector(0,0,0));

        PotionEffect slowness = PotionEffectType.getById(2).createEffect(60*20,15);
        victim.addPotionEffect(slowness);

        PotionEffect jb = PotionEffectType.getById(8).createEffect(60*20,254);
        victim.addPotionEffect(jb);

        PotionEffect n = PotionEffectType.getById(15).createEffect(60*20,1);
        victim.addPotionEffect(n);

        PotionEffect mf = PotionEffectType.getById(4).createEffect(60*20,2);
        victim.addPotionEffect(mf);

        victim.playSound(victim.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1.0F,0.85F);
        victim.teleport(new Location(
                victim.getWorld(),
                (int) victim.getX(),
                (int) victim.getY(),
                (int) victim.getZ()
        ));

        for (int r = 0; r < 2; r++) {
            for (double i = 0; i < 2*Math.PI; i+=Math.PI/16) {
                victim.getWorld().setType(
                        (int) (victim.getX()+r*Math.sin(i)),
                        (int) victim.getY(),
                        (int) (victim.getZ()+r*Math.cos(i)),
                        Material.TINTED_GLASS
                );

                victim.getWorld().setType(
                        (int) (victim.getX()+r*Math.sin(i)),
                        (int) victim.getY()+1,
                        (int) (victim.getZ()+r*Math.cos(i)),
                        Material.TINTED_GLASS
                );
            }
        }
    }
}
