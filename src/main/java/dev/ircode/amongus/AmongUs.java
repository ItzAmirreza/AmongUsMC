package dev.ircode.amongus;

import dev.ircode.amongus.Commands.SetupCommand;
import dev.ircode.amongus.database.connector;
import dev.ircode.amongus.Utils.Utils;
import dev.ircode.amongus.database.query;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AmongUs extends JavaPlugin {

    // Tasks
    public static String[] TASK_TYPES = {"DOWNLOAD", "UPLOAD", "CART", "RAC_COUNTER", "GUNNER", "LIGHTS", "REPEATER", "ELECTRIC_CONNECTING"};
    public static List<String> TASK_TYPES_ARRAY_LIST = new ArrayList<String>(Arrays.asList(TASK_TYPES));
    // Sabotages
    public static String[] SABOTAGE_TYPES = {"REACTOR1","REACTOR2", "O2", "LIGHTS"};
    public static List<String> SABOTAGE_TYPES_ARRAY_LIST = new ArrayList<String>(Arrays.asList(SABOTAGE_TYPES));

    public static AmongUs instance;
    public static AmongUs getInstance() {
        return instance;
    }


    public static void dbConfiguration() throws SQLException {
        connector.getDatabaseConnection().createStatement().executeQuery(query.ArenaTable);
    }

    public void registeringEvents() {

        //Register Events

    }


    public void registerCommands() {

        //register Commands
        getServer().getPluginCommand("amongus").setExecutor(new SetupCommand()); //Registering AmongUs
    }

    public void logger(String str) {
        getServer().getConsoleSender().sendMessage(Utils.color(str));
    }


    public void loadConfiguration() throws IOException {
        saveDefaultConfig();
        if (getConfig().getBoolean("mysql.enable")) {
            logger(Utils.Prefix + "database Storage » Mysql");
            logger(Utils.Prefix + "Connecting Database ...");
            // Database Connector
        } else {
            File db = new File(this.getDataFolder() + "/AmongUS.db");

            if (!db.exists()) {
                db.createNewFile();
                logger(Utils.Prefix + "&eSqlite database has been created !");
            }
            logger(Utils.Prefix + "database Storage » Sqlite");
        }
    }


    @Override
    public void onEnable() {
        instance = this;
        logger(Utils.Prefix + "&aStarting AmongUS Version " + Utils.version);
        try {
            loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dbConfiguration();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        registeringEvents();
        registerCommands();
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


    }



}
