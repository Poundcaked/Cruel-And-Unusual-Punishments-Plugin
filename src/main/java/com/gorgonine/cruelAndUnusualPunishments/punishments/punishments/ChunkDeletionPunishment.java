package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class ChunkDeletionPunishment extends Punishment {
    public ChunkDeletionPunishment() {
        super("ChunkDeletionPunishment", ItemStack.of(Material.GRASS_BLOCK), "Destroy the chunk your victim is in");
    }

    @Override
    public void execute(Player executor, Player victim) {
        Chunk chunk = victim.getChunk();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int finalZ = z;
                int finalX = x;
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> {
                    boolean atLeastOneBlockBroke = false;
                    for (int y = -64; y < 320; y++) {
                        if(!chunk.getBlock(finalX,y,finalZ).isEmpty()){
                            if(!atLeastOneBlockBroke){atLeastOneBlockBroke = true;}
                            chunk.getBlock(finalX, y, finalZ).setType(Material.AIR);
                        }
                    }
                    if(atLeastOneBlockBroke){chunk.getWorld().playSound(victim, Sound.BLOCK_STONE_BREAK,0.1F,1.0F);}
                }, x);
            }
        }
    }
}
