package com.gorgonine.cruelAndUnusualPunishments.punishments.punishments;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.HopperGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.text.StringEscapeUtils;

import static com.gorgonine.cruelAndUnusualPunishments.CruelAndUnusualPunishments.PLUGIN_ID;

public class PopQuizPunishment extends Punishment {
    public PopQuizPunishment() {
        super("PopQuizPunishment", new ItemStack(Material.PLAYER_HEAD) {{ SkullMeta m = (SkullMeta) getItemMeta(); m.setPlayerProfile(Bukkit.createProfile(UUID.randomUUID(), null)); PlayerProfile p = m.getPlayerProfile(); p.setProperties(Collections.singleton(new ProfileProperty("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwNWZkOTRhMGM0MzE5MjdmYjRlNjM5YjBmY2ZiNDk3MTdlNDEyMjg1YTAyYjQzOWUwMTEyZGEyMmIyZTJlYyJ9fX0="))); m.setPlayerProfile(p); setItemMeta(m); }},"Force the victim to answer some trivia");
    }

    private static String readAll(Reader rd) {
        StringBuilder sb = new StringBuilder();
        try {
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static JSONObject readJsonFromUrl(String url) {
        try (InputStream is = new URL(url).openStream();
             BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {
            return new JSONObject(readAll(rd));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return new JSONObject(); // empty object so it won't crash
        }
    }

    @Override
    public void execute(Player executor, Player victim){

        //api url: https://opentdb.com/api.php?amount=1&difficulty=medium
        JSONObject json = readJsonFromUrl("https://opentdb.com/api.php?amount=1&type=multiple");

        JSONArray results = json.getJSONArray("results");
        JSONObject questionObj = results.getJSONObject(0);


        String qType = questionObj.getString("type");
        String q = questionObj.getString("question");
        String a = questionObj.getString("correct_answer");

        JSONArray incorrectAnswers = questionObj.getJSONArray("incorrect_answers");
        String i1 = incorrectAnswers.getString(0);
        String i2 = incorrectAnswers.getString(1);
        String i3 = incorrectAnswers.getString(2);

        //stop the formatting
        q = q.replace("&quot;", "\"").replace("&#039;", "'").replace("&amp;", "&");
        a = a.replace("&quot;", "\"").replace("&#039;", "'").replace("&amp;", "&");
        i1 = i1.replace("&quot;", "\"").replace("&#039;", "'").replace("&amp;", "&");
        i2 = i2.replace("&quot;", "\"").replace("&#039;", "'").replace("&amp;", "&");
        i3 = i3.replace("&quot;", "\"").replace("&#039;", "'").replace("&amp;", "&");


        ChestGui gui = new ChestGui(1,"Pop Quiz!");
        StaticPane pane = new StaticPane(0, 0, 9, 1, Pane.Priority.HIGH);
        gui.addPane(pane);

        boolean[] answerPromptedCloseRequest = {false};

        gui.setOnGlobalClick(event -> event.setCancelled(true)); // cancel all clicks
        gui.setOnClose(event -> {
            if(!answerPromptedCloseRequest[0]){
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(PLUGIN_ID), () -> gui.show(victim), 1);
            }
        });

        ArrayList<String> availableAnswers = new ArrayList<String>();
        availableAnswers.add(a);
        availableAnswers.add(i1);
        availableAnswers.add(i2);
        availableAnswers.add(i3);

        Random random = new Random();

        Material[] choiceBlocks = {
                Material.REDSTONE_BLOCK,
                Material.GOLD_BLOCK,
                Material.EMERALD_BLOCK,
                Material.LAPIS_BLOCK
        };

        ItemStack question = new ItemStack(Material.PLAYER_HEAD) {{ SkullMeta m = (SkullMeta) getItemMeta(); m.setPlayerProfile(Bukkit.createProfile(UUID.randomUUID(), null)); PlayerProfile p = m.getPlayerProfile(); p.setProperties(Collections.singleton(new ProfileProperty("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwNWZkOTRhMGM0MzE5MjdmYjRlNjM5YjBmY2ZiNDk3MTdlNDEyMjg1YTAyYjQzOWUwMTEyZGEyMmIyZTJlYyJ9fX0="))); m.setPlayerProfile(p); setItemMeta(m); }};
        ItemMeta questionItemMeta = question.getItemMeta();
        questionItemMeta.setDisplayName("QUESTION: "+q);
        question.setItemMeta(questionItemMeta);
        pane.addItem(new GuiItem(question),0,0);

        for (int i = 0; i < 4; i++) {
            int chosenIndex = random.nextInt(availableAnswers.size());
            String chosenAnswer = availableAnswers.get(chosenIndex);
            availableAnswers.remove(chosenIndex);

            ItemStack answer = new ItemStack(choiceBlocks[i]);
            ItemMeta answerItemMeta = answer.getItemMeta();
            answerItemMeta.setDisplayName(chosenAnswer);
            answer.setItemMeta(answerItemMeta);

            String finalA = a;
            pane.addItem(new GuiItem(answer, event -> {
                answerPromptedCloseRequest[0] = true;
                if(chosenAnswer.equals(finalA)){
                    victim.closeInventory();
                    victim.playSound(victim, Sound.BLOCK_NOTE_BLOCK_BELL,1.0F,1.0F);
                    victim.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Correct!");
                }else{
                    victim.playSound(victim, Sound.ENTITY_HORSE_DEATH,1.0F,1.0F);
                    victim.setHealth(0);
                    victim.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "WRONG! Correct Answer was " + finalA);
                }
            }),i+3,0);
        }
        gui.update();
        gui.show(victim);
        victim.playSound(victim,Sound.BLOCK_NOTE_BLOCK_PLING,1.0F,1.0F);

    }
}
