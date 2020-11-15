package dev.ircode.amongus.database;

import dev.ircode.amongus.AmongUs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysql {
    public final String host = AmongUs.getInstance().getConfig().getString("mysql.host");
    public final int port = AmongUs.getInstance().getConfig().getInt("mysql.port");
    public final String username = AmongUs.getInstance().getConfig().getString("mysql.username");
    public final String password = AmongUs.getInstance().getConfig().getString("mysql.password");
    public final String database = AmongUs.getInstance().getConfig().getString("mysql.database");
    public final Boolean use_ssl = AmongUs.getInstance().getConfig().getBoolean("mysql.usessl");

    public final Connection getMysqlConnection() {
        if (AmongUs.getInstance().getConfig().getBoolean("mysql.enable")) {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
                Connection connection = DriverManager.getConnection(url, username, password);
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                AmongUs.getInstance().logger(AmongUs.getInstance().Prefix + "That was error on mysql connection please check config.yml ...");
                AmongUs.getInstance().getServer().getPluginManager().disablePlugin(AmongUs.getInstance());
                return null;
            }
        } else {
            return null;
        }
    }
}
