package com.gorgonine.cruelAndUnusualPunishments.punishments;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Punishment {
    private String name;
    private ItemStack item;
    private String desc;

    public Punishment(String name, ItemStack item, String desc){
        this.name = name;
        this.item = item;
        this.desc = desc;
    }

    public void execute(Player victim){execute(null,victim);} //This means executor can be null, so make sure to check
    public void execute(Player executor, Player victim){}
    public String getName(){return this.name;}
    public ItemStack getItemStack(){return this.item;}
    public String getDesc(){return this.desc;}

}
