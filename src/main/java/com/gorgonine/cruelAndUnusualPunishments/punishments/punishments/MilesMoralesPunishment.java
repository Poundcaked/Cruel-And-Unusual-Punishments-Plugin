package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class MilesMoralesPunishment extends Punishment {
    public MilesMoralesPunishment() {
        super("MilesMoralesPunishment", ItemStack.of(Material.COBWEB), "Force your victim to recreate the \"What's up Danger\" scene from Into The Spiderverse");
    }

    @Override
    public void execute(Player executor, Player victim) {
        victim.showTitle(
                Title.title(
                        Component.text("It's just a leap of faith, "+victim.getName())
                                .color(TextColor.color(35,35,35)),
                        Component.text("That's all it is")
                                .color(TextColor.color(255,0,0))
                                .decorate(TextDecoration.BOLD)));



        victim.teleport(victim.getLocation().add(0,200,0));
        victim.setVelocity(new Vector(0.0F, 1.001F,0.0F));

        AtomicInteger i = new AtomicInteger();
        Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), task -> {
            i.getAndIncrement();
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED,20*20,i.get()/8 ,true,false,false);
            if (victim.isOnGround() || !victim.isOnline()) {
                victim.removePotionEffect(PotionEffectType.SPEED);
                task.cancel();
                return;
            }

            victim.addPotionEffect(potionEffect);
            victim.setRotation(victim.getYaw(), 90.0F);
            victim.setVelocity(new Vector(0,victim.getVelocity().getY(),0));

        }, 2L, 2L);


        victim.removePotionEffect(PotionEffectType.SPEED);
    }
}
