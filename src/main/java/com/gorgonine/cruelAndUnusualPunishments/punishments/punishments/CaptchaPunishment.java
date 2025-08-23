package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class CaptchaPunishment extends Punishment {
    public CaptchaPunishment() {
        super("CaptchaPunishment", ItemStack.of(Material.IRON_GOLEM_SPAWN_EGG), "Force your victim to solve a captcha to continue playing Minecraft");
    }

    private Material getRandomMaterial(ArrayList<Material> matsList, Random random){
        int index = random.nextInt(matsList.size());
        Material chosen = matsList.get(index);
        matsList.remove(index);

        return chosen;
    }

    @Override
    public void execute(Player executor, Player victim) {
        Random random = new Random();

        ArrayList<Material> allMaterials = new ArrayList<Material>();

        for (Material m: Material.values()){
            if(m.isItem()){
                allMaterials.add(m);
            }
        }

        Material chosen = getRandomMaterial(allMaterials, random);

        ChestGui gui = new ChestGui(6,"[CAPTCHA] Pick the "+ chosen.name().toLowerCase());
        StaticPane pane = new StaticPane(0, 0, 9, 6, Pane.Priority.HIGH);
        gui.addPane(pane);

        boolean[] answerPromptedCloseRequest = {false};

        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnClose(event -> {
            if(!answerPromptedCloseRequest[0]){
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> gui.show(victim), 1);
            }
        });

        int rowOfChosen = random.nextInt(6);
        int colOfChosen = random.nextInt(9);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                if(row == rowOfChosen && col == colOfChosen){
                    pane.addItem(new GuiItem(ItemStack.of(chosen), event -> {
                        victim.playSound(victim, Sound.BLOCK_NOTE_BLOCK_BELL,1.0F,1.0F);
                        answerPromptedCloseRequest[0] = true;
                        victim.closeInventory();
                        victim.sendMessage(Component.text("CAPTCHA passed, proceed!").color(TextColor.color(0,255,0)).decorate(TextDecoration.ITALIC));
                    }),col,row);
                }else{
                    Material randomGarbageItem = getRandomMaterial(allMaterials, random);
                    pane.addItem(new GuiItem(ItemStack.of(randomGarbageItem), event -> {
                        victim.playSound(victim, Sound.BLOCK_NOTE_BLOCK_BASS,1.0F,1.0F);
                    }),col,row);
                }
            }
        }

        gui.update();
        gui.show(victim);

        victim.playSound(victim,Sound.BLOCK_NOTE_BLOCK_PLING,1.0F,1.0F);
    }
}
