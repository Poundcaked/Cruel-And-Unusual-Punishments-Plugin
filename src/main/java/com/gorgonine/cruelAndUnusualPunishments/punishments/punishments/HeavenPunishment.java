package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class HeavenPunishment extends Punishment {
    public HeavenPunishment() {
        super("HeavenPunishment", ItemStack.of(Material.ELYTRA), "Send your victim straight up to heaven");
    }

    @Override
    public void execute(Player executor, Player victim) {
        Location location = victim.getLocation();
        Random random = new Random();
        World world = victim.getWorld();

        Material[] randomMats = {
                Material.AIR,
                Material.WHITE_STAINED_GLASS,
                Material.WHITE_STAINED_GLASS_PANE,
                Material.WHITE_WOOL,
                Material.COBWEB
        };

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 7; x++) {
                for (int z = 0; z < 4; z++) {
                    world.setType(location.getBlockX()+x,Math.min(location.getBlockY()+y+210,317+y),location.getBlockZ()+z,randomMats[random.nextInt(randomMats.length)]);
                }
            }
        }


        victim.teleport(new Location(world, location.getBlockX()+3,Math.min(location.getBlockY()+3+210,317+3),location.getBlockZ()+2));
        victim.heal(20);
        victim.showTitle(Title.title(Component.text("Welcome to Heaven").color(TextColor.color(255,255,255)).decorate(TextDecoration.BOLD),Component.text("😇😇😇").color(TextColor.color(255,255,0))));
    }
}
