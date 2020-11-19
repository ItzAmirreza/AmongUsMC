package dev.ircode.amongus.database;

import dev.ircode.amongus.AmongUs;
import org.bukkit.event.inventory.ClickType;
import javax.xml.transform.Result;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class connector {
    public static Connection getDatabaseConnection() {
        if (AmongUs.getInstance().getConfig().getBoolean("mysql.enable")) {
            return mysql.getMysqlConnection();
        } else {
            return sqlite.getSqliteConnection();
        }
    }

    public static boolean insert(String Table_name, String[] names, String[] values, String[] types) throws SQLException {
        String Names_sql = "(";
        String Values_Sql = "(";

        int counter = 0;
        for (String name: names) {
            if (counter == names.length) {
                Names_sql = Names_sql + "`" + name + "`)";
            } else {
                Names_sql = Names_sql + "`" + name + "`, ";
            }
            counter++;
        }
        counter = 0;
        for (String value: values) {
            if (counter == values.length) {
                if (types[counter].equalsIgnoreCase("string") || types[counter].equalsIgnoreCase("int")) {
                    Values_Sql = Values_Sql + "'" + value + "')";
                } else {
                    Values_Sql = Values_Sql  + value + ")";
                }
            } else {
                if (types[counter].equalsIgnoreCase("string") || types[counter].equalsIgnoreCase("int")) {
                    Values_Sql = Values_Sql + "'" + value + "')";
                } else {
                    Values_Sql = Values_Sql  + value + ")";
                }
            }
            counter++;
        }

        String Query = "INSERT INTO `" + Table_name + "`" + Names_sql + " VALUES " + Values_Sql;
        if (names.length == values.length) {
            getDatabaseConnection().createStatement().executeQuery(Query);
            return true;
        } else {
            return false;
        }
    }

    public static ResultSet update(String Table_name, String name, String value, String value_type, String Condition_name, String Condition_value) throws SQLException {
        String Query;
        if (value_type.equalsIgnoreCase("string") || value_type.equalsIgnoreCase("int"))  {
            Query = "UPDATE `" + Table_name + "` SET `" + name + "`='" + value + "' WHERE " + Condition_name + "=' " + Condition_value + " '";
        } else {
            Query = "UPDATE `" + Table_name + "` SET `" + name + "`=" + value + " WHERE " + Condition_name + "=' " + Condition_value + " '";
        }
        return getDatabaseConnection().createStatement().executeQuery(Query);
    }

    public static ResultSet delete(String Table_name, String Condition_name , String Condition_value) throws SQLException {
        String Query = "DELETE FROM `" + Table_name + "` WHERE " + Condition_name + "='" + Condition_value + "'";
        return getDatabaseConnection().createStatement().executeQuery(Query);
    }

    public static ResultSet getSingleRow(String Table_name, String Condition_name, String Condition_value) throws SQLException {
        String Query = "SELECT * FROM " + Table_name + " WHERE " + Condition_name + "='" + Condition_value + "' LIMIT 1";
        return getDatabaseConnection().createStatement().executeQuery(Query);
    }

    public static int numRows(String Table_name, String Condition_name , String Condition_value) throws SQLException {
        String Query = "SELECT COUNT(*) FROM `" +Table_name+ "` WHERE " +Condition_name+ "='" +Condition_value+ "'";
        return getDatabaseConnection().createStatement().executeQuery(Query).getInt(1);
    }

    public static int c2NumRows(String Table_name, String Condition_name , String Condition_value, String CCondition_name, String CCondition_value) throws SQLException {
        String Query = "SELECT COUNT(*) FROM `" +Table_name+ "` WHERE " +Condition_name+ "='" +Condition_value+ "' AND " +CCondition_name+ "='" +CCondition_value+ "'";
        return getDatabaseConnection().createStatement().executeQuery(Query).getInt(1);
    }
    public static ResultSet c2Delete(String Table_name, String Condition_name , String Condition_value,String CCondition_name, String CCondition_value) throws SQLException {
        String Query = "DELETE FROM `" + Table_name + "` WHERE " + Condition_name + "='" + Condition_value + "' AND " +CCondition_name+ "='"+CCondition_value+"'";
        return getDatabaseConnection().createStatement().executeQuery(Query);
    }

}
