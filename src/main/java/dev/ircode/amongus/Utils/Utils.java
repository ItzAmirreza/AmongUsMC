package dev.ircode.amongus.Utils;


import dev.ircode.amongus.AmongUs;
import dev.ircode.amongus.ArenaManager.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Utils {


    public static HashMap<String, String> HashMap = new HashMap<String, String>();

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

    public static FileConfiguration getMessages() throws IOException, InvalidConfigurationException {
        File MessagesFile = new File(AmongUs.getInstance().getDataFolder() + "messages.yml");
        if (!MessagesFile.exists()) {
            AmongUs.getInstance().saveResource("messages.yml", false);
        }
        FileConfiguration MessagesConfig = new YamlConfiguration();
        MessagesConfig.load(MessagesFile);
        return MessagesConfig;
    }

    public static void logger(String str) {
        AmongUs.getInstance().getServer().getConsoleSender().sendMessage(color(str));
    }

    public static List<UUID> waitingForResponse;

    public static String Prefix = "&8[&eAmong&cUS&8] ";
    public static String version = "1.0.0";

    public static String[] DBArenaNames = {"name", "min_player", "max_player", "world", "spawn_locations", "waiting_location"};
    public static String[] DBArenaTypes = {"string", "int", "int", "string", "string", "string"};
    public static List<Arena> loadedArenas;

}
