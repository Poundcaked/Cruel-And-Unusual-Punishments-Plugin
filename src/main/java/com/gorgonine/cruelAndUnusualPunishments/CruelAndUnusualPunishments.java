package com.gorgonine.cruelAndUnusualPunishments;

import com.gorgonine.cruelAndUnusualPunishments.commands.GuiCommand;
import com.gorgonine.cruelAndUnusualPunishments.gui.GuiManager;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishments;
import org.bukkit.plugin.java.JavaPlugin;

public final class CruelAndUnusualPunishments extends JavaPlugin {

    public static final String PLUGIN_ID = "cruelandunusualpunishments";

    @Override
    public void onEnable() {
        getCommand("punish").setExecutor(new GuiCommand());
        Punishments.addPunishments();
        GuiManager.init();
        getLogger().info("Cruel And Unusual Punishments Loaded!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Cruel And Unusual Punishments Shutdown!");
    }
}
