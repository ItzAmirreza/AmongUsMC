package dev.ircode.amongus;

import dev.ircode.amongus.Utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public final class AmongUs extends JavaPlugin {

    public static AmongUs instance;
    public static AmongUs getInstance() {

        return instance;
    }



    public void registeringEvents() {

        //Register Events

    }


    public void registerCommands() {

        //register Commands

    }

    public void logger(String str) {
        getServer().getConsoleSender().sendMessage(Utils.color(str));
    }


    public void loadConfiguration() {
        saveDefaultConfig();

    }


    @Override
    public void onEnable() {
        instance = this;
        loadConfiguration();
        registeringEvents();
        registerCommands();
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


    }



}
