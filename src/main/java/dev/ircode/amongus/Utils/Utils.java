package dev.ircode.amongus.Utils;


import dev.ircode.amongus.ArenaManager.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Utils {



    public static String color(String color) {
        return ChatColor.translateAlternateColorCodes('&', color);
    }



    public static String BungeeColor(String color) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', color);
    }


    public static String convertLocToString(int x, int y, int z, String world, float pitch, float yaw) {

        return Integer.toString(x) + ":" + Integer.toString(y) + ":" + Integer.toString(z) + ":" + world + ":" + Float.toString(pitch) + ":" + Float.toString(yaw);
    }

    public static Location convertStringToLoc(String stringloc) {

        //4 --> pitch
        //5 --> yaw
        List<String> splited = Arrays.asList(stringloc.split(":"));

        return new Location(Bukkit.getWorld(splited.get(3)), Integer.parseInt(splited.get(0)) + 0.5, Integer.parseInt(splited.get(1)), Integer.parseInt(splited.get(2)) + 0.5, Float.parseFloat(splited.get(5)), Float.parseFloat(splited.get(4)));
    }

    public static HashMap<Arena, Boolean> arenas = new HashMap();




}
