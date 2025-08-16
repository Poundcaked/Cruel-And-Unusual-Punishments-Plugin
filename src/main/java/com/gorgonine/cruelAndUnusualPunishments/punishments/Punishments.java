package com.gorgonine.cruelAndUnusualPunishments.punishments;

import com.gorgonine.cruelAndUnusualPunishments.punishments.punishments.FillInventoryPunishment;
import com.gorgonine.cruelAndUnusualPunishments.punishments.punishments.PopQuizPunishment;
import com.gorgonine.cruelAndUnusualPunishments.punishments.punishments.ThirtyEffectsPunishment;
import com.gorgonine.cruelAndUnusualPunishments.punishments.punishments.WaterTankPunishment;

import java.util.ArrayList;

public class Punishments {
    private static ArrayList<Punishment> punishments = new ArrayList<Punishment>();

    public static void addPunishments(){
        addPunishment(new WaterTankPunishment());
        addPunishment(new ThirtyEffectsPunishment());
        addPunishment(new PopQuizPunishment());
        addPunishment(new FillInventoryPunishment());
    }

    public static ArrayList<Punishment> getPunishments(){
        return punishments;
    }
    public static int getPunishmentsCount(){
        return punishments.size();
    }
    public static Punishment getPunishmentFromName(String name){
        for(int i = 0; i < punishments.size(); i++){
            if(punishments.get(i).getName().equals(name)){
                return punishments.get(i);
            }
        }
        return null;
    }
    public static ArrayList<String> getPunishmentNames(){
        ArrayList<String> p = new ArrayList<>();

        for (int i = 0; i < punishments.size(); i++) {
            p.add(punishments.get(i).getName());
        }
        return p;
    }

    private static void addPunishment(Punishment p){
        punishments.add(p);
    }
    private static void removePunishment(Punishment p) {
        punishments.remove(p);
    }

}
