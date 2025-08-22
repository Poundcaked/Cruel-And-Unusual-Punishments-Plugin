package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.google.common.collect.Lists;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class ConstantNoisePunishment extends Punishment {
    private Random random = new Random();
    Sound[] sounds = Lists.newArrayList(Registry.SOUNDS).toArray(new Sound[0]);
    public ConstantNoisePunishment() {
        super("ConstantNoisePunishment", ItemStack.of(Material.JUKEBOX), "Play lots of annoying noises to your victim");
    }

    @Override
    public void execute(Player executor, Player victim) {
        for (int i = 0; i < random.nextInt(80,160); i++) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.playSound(victim, sounds[random.nextInt(0,sounds.length)],1.0F, random.nextFloat(0.45F,2.1F)), i);
        }
    }
}
