package dev.ircode.amongus.database;

import dev.ircode.amongus.AmongUs;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlite {
    public static Connection getSqliteConnection() {
        File db = new File(AmongUs.getInstance().getDataFolder() + "/AmongUS.db");
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + db);
            return connection;
        } catch (SQLException | ClassNotFoundException ex) {
            AmongUs.getInstance().logger(AmongUs.Prefix + "&cSQLite exception on initialize");
            return null;
        }
//        catch (ClassNotFoundException ex) {
////            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
////        }
    }

}
