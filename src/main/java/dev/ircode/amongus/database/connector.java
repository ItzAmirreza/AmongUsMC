package dev.ircode.amongus.database;

import dev.ircode.amongus.AmongUs;

import java.sql.Connection;

public class connector {
    public static Connection getDatabaseConnection() {
        if (AmongUs.getInstance().getConfig().getBoolean("mysql.enable")) {
            return mysql.getMysqlConnection();
        } else {
            return sqlite.getSqliteConnection();
        }
    }
}
