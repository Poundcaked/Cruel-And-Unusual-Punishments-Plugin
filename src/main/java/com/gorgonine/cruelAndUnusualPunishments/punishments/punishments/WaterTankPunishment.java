package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class WaterTankPunishment extends Punishment {
    public WaterTankPunishment(){
        super("WaterTank", ItemStack.of(Material.WATER_BUCKET),"Places an Obsidian Tank of water on your victim");
    }

    @Override
    public void execute(Player executor, Player player){
        World world = player.getWorld();
        Location playerLoc = player.getLocation();

        player.teleport(playerLoc.getBlock().getLocation());
        if(player.getGameMode() != GameMode.CREATIVE){
            PotionEffect potionEffect = PotionEffectType.MINING_FATIGUE.createEffect(-1,5);
            player.addPotionEffect(potionEffect);
        }

        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                for (int y = -1; y < 3; y++) {
                    Material mat = Material.OBSIDIAN;

                    if(x == 0 && z == 0 && y > -1 && y < 2){
                        mat = Material.WATER;
                    }
                    if(Math.abs(x) + Math.abs(z) != 0 && y == 1){
                        mat = Material.GLASS;
                    }

                    world.setType(playerLoc.getBlockX()+x,playerLoc.getBlockY()+y,playerLoc.getBlockZ()+z, mat);
                }
            }
        }

    }
}
