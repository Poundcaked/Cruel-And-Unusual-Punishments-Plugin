package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class ConstantArrowPunishment extends Punishment {
    public ConstantArrowPunishment() {
        super("ConstantArrowPunishment", ItemStack.of(Material.BOW), "For 3 minutes straight, keep making your victim think they've been shot");
    }

    @Override
    public void execute(Player executor, Player victim) {
        Random random = new Random();
        for (int i = 0; i < 60; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> {
                victim.sendHurtAnimation(random.nextFloat(-90,90));
                victim.damage(1);
                victim.playSound(victim, Sound.ENTITY_ARROW_HIT,1.0F,1.0F);
                victim.setArrowsInBody(finalI);
                victim.knockback(1.0F, random.nextDouble(-1.0,1.0), random.nextDouble(-1.0,1.0));
            }, i*3*20);
        }
    }
}
