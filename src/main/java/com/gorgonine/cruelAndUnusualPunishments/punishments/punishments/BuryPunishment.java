package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuryPunishment extends Punishment {
    public BuryPunishment() {
        super("BuryPunishment", ItemStack.of(Material.IRON_SHOVEL), "Send your victim 6 feet under");
    }

    @Override
    public void execute(Player executor, Player victim) {
        Location playerLoc = victim.getLocation();
        Block mainBlock = playerLoc.getBlock();
        World world = victim.getWorld();

        world.setType(mainBlock.getX(), mainBlock.getY()-11, mainBlock.getZ(), Material.AIR);
        world.setType(mainBlock.getX(), mainBlock.getY()-12, mainBlock.getZ(), Material.REDSTONE_TORCH);

        victim.teleport(new Location(world,mainBlock.getX(),mainBlock.getY()-12,mainBlock.getZ()));
        victim.playSound(victim, Sound.BLOCK_GRASS_BREAK,2.0F,1.0F);
    }
}
