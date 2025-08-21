package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HalfHeartPunishment extends Punishment {
    public HalfHeartPunishment() {
        super("HalfHeartPunishment", ItemStack.of(Material.IRON_SWORD), "Set your victim to half a heart");
    }

    @Override
    public void execute(Player executor, Player victim) {
        PotionEffect darkness = PotionEffectType.getById(33).createEffect(17, 0);
        PotionEffect blindness = PotionEffectType.getById(15).createEffect(27, 0);
        PotionEffect nausea = PotionEffectType.getById(9).createEffect(10,0);
        PotionEffect slowness = PotionEffectType.getById(2).createEffect(6*20,1);
        PotionEffect weakness = PotionEffectType.getById(18).createEffect(9*20,2);
        PotionEffect hunger = PotionEffectType.getById(17).createEffect(25*20,0);
        victim.setHealth(1D);
        victim.addPotionEffect(darkness);
        victim.addPotionEffect(blindness);
        victim.addPotionEffect(nausea);
        victim.addPotionEffect(slowness);
        victim.addPotionEffect(weakness);
        victim.addPotionEffect(hunger);
        victim.playSound(victim, Sound.BLOCK_ANVIL_LAND, 0.9F,0.65F);
        victim.playSound(victim, Sound.BLOCK_ANVIL_LAND, 1.0F,1.0F);
        victim.playSound(victim, Sound.BLOCK_ANVIL_LAND, 1.1F,1.45F);

        victim.playSound(victim, Sound.BLOCK_GLASS_BREAK, 0.6F,1.0F);
        victim.playSound(victim, Sound.ITEM_TOTEM_USE, 0.7F,0.4F);
        victim.playSound(victim, Sound.AMBIENT_CAVE, 0.3F,1.0F);

        victim.playSound(victim, Sound.ENTITY_PLAYER_ATTACK_CRIT, 0.3F,1.0F);
        victim.playSound(victim, Sound.ENTITY_PLAYER_ATTACK_STRONG, 0.3F,1.1F);
        victim.playSound(victim, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 0.3F,0.8F);
    }
}
