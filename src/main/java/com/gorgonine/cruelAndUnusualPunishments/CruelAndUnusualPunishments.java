package com.gorgonine.cruelAndUnusualPunishments;

import com.gorgonine.cruelAndUnusualPunishments.commands.GuiCommand;
import com.gorgonine.cruelAndUnusualPunishments.gui.GuiManager;
import com.gorgonine.cruelAndUnusualPunishments.punishments.Punishments;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CruelAndUnusualPunishments extends JavaPlugin {

    public static final String PLUGIN_ID = "cruelandunusualpunishments";
    public static final Logger LOGGER = LoggerFactory.getLogger(PLUGIN_ID);

    @Override
    public void onEnable() {
        getCommand("punish").setExecutor(new GuiCommand());
        Punishments.addPunishments();
        GuiManager.init();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
