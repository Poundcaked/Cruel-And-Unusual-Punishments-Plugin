package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class ThirtyEffectsPunishment extends Punishment {
    public ThirtyEffectsPunishment() {
        super("ThirtyEffectsPunishment", ItemStack.of(Material.POTION));
    }

    @Override
    public void execute(Player executor, Player victim) {
        Random random = new Random();

        for (int i = 0; i < 30; i++) {
            PotionEffect potionEffect = PotionEffectType.getById(random.nextInt(1,39+1)).createEffect(20*20,0);

            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> victim.addPotionEffect(potionEffect), 2);
        }
    }
}
