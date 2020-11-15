package dev.ircode.amongus;

import dev.ircode.amongus.database.connector;
import dev.ircode.amongus.Utils.Utils;
import dev.ircode.amongus.database.query;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class AmongUs extends JavaPlugin {

    public static String Prefix = "&8[&eAmong&cUS&8] ";
    public static String version = "1.0.0";
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

    }

    public void logger(String str) {
        getServer().getConsoleSender().sendMessage(Utils.color(str));
    }


    public void loadConfiguration() throws IOException {
        saveDefaultConfig();
        if (getConfig().getBoolean("mysql.enable")) {
            logger(Prefix + "database Storage » Mysql");
            logger(Prefix + "Connecting Database ...");
            // Database Connector
        } else {
            File db = new File(this.getDataFolder() + "/AmongUS.db");

            if (!db.exists()) {
                db.createNewFile();
                logger(Prefix + "&eSqlite database has been created !");
            }
            logger(Prefix + "database Storage » Sqlite");
        }
    }


    @Override
    public void onEnable() {
        instance = this;
        logger(Prefix + "&aStarting AmongUS Version " + version);
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
