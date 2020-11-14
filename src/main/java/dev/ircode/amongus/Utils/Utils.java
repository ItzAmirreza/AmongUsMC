package dev.ircode.amongus.Utils;


import org.bukkit.ChatColor;

public class Utils {


    public static String color(String color) {
        return ChatColor.translateAlternateColorCodes('&', color);
    }



    public static String BungeeColor(String color) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', color);
    }


}
