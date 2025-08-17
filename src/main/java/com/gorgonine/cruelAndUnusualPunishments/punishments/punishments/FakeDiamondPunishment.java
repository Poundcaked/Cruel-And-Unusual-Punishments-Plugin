package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FakeDiamondPunishment extends Punishment {
    public FakeDiamondPunishment() {
        super("FakeDiamondPunishment", new ItemStack(Material.DIAMOND));
    }

    @Override
    public void execute(Player executor, Player victim) {
        for (int j = 0; j < victim.getInventory().getSize(); j++) {
            victim.getInventory().addItem(new ItemStack(Material.DIAMOND,64));
        }
        victim.playSound(victim, Sound.ENTITY_CHICKEN_EGG,1.0F,1.0F);
        victim.showTitle(Title.title(Component.text("Giveaway Winner Alert!"),Component.text("Inventory filled with Diamonds")));
    }
}
