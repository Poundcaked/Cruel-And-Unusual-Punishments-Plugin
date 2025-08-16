package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class FillInventoryPunishment extends Punishment {
    public FillInventoryPunishment() {
        super("FillInventoryPunishment", new ItemStack(Material.INK_SAC));
    }

    @Override
    public void execute(Player executor, Player victim) {
        ItemStack[] chosenItem = new ItemStack[1];
        if(executor == null){
            chosenItem[0] = new ItemStack(Material.INK_SAC);
        }
        ChestGui gui = new ChestGui(1, "Pick your Poison");
        StaticPane itemPane = new StaticPane(0,0,9,1);
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.addPane(itemPane);

        ItemStack[] grossItems = {
                new ItemStack(Material.INK_SAC,64),
                new ItemStack(Material.ROTTEN_FLESH,64),
                new ItemStack(Material.FEATHER,64),
                new ItemStack(Material.WHEAT_SEEDS,64),
                new ItemStack(Material.DEAD_BUSH,64),
                new ItemStack(Material.POISONOUS_POTATO,64),
                new ItemStack(Material.TROPICAL_FISH,64),
                new ItemStack(Material.PUFFERFISH,64),
                new ItemStack(Material.EGG,64)
        };

        for (int i = 0; i < grossItems.length; i++) {
            ItemStack itemChosen = grossItems[i];
            itemPane.addItem(new GuiItem(grossItems[i], event -> {
                chosenItem[0] = itemChosen;
                for (int j = 0; j < victim.getInventory().getSize(); j++) {
                    victim.getInventory().addItem(chosenItem);
                }
                victim.playSound(victim, Sound.ENTITY_CHICKEN_EGG,1.0F,1.0F);
                if(executor != null){
                    executor.closeInventory();
                }
            }),i,0);
        }

        gui.update();
        gui.show(executor);
    }
}
