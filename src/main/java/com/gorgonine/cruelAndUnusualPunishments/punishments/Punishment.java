package com.gorgonine.cruelAndUnusualPunishments.punishments;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public abstract class Punishment {
    private String name;
    private ItemStack item;

    public Punishment(String name, ItemStack item){
        this.name = name;
        this.item = item;
    }

    public void execute(Player victim){execute(null,victim);} //This means executor can be null, so make sure to check
    public void execute(Player executor, Player victim){}
    public String getName(){return this.name;}
    public ItemStack getItemStack(){return this.item;}

}
