package dev.ircode.amongus.Commands;
import com.google.gson.Gson;
import dev.ircode.amongus.AmongUs;
import dev.ircode.amongus.ArenaManager.Arena;
import dev.ircode.amongus.Enums.GameState;
import dev.ircode.amongus.Utils.Maps;
import dev.ircode.amongus.Utils.Utils;
import dev.ircode.amongus.database.connector;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.w3c.dom.NameList;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetupCommand implements CommandExecutor {
    public Gson g = new Gson();

    public boolean checkCreateArenaArgs(String min, String max) {
        boolean result = true;



        try {
            int minp = Integer.parseInt(min);
            int maxp = Integer.parseInt(max);

            if (minp == 0 || maxp == 0) {
                result = false;
            }


        } catch (Exception ex) {

            result = false;

        }
        return result;
    }


    private void createArena(Player player, String[] args) throws SQLException {

        if (args.length == 4) {

            String arenaname;
            int min_players;
            int max_players;

            if (checkCreateArenaArgs(args[2], args[3])) {

                arenaname = args[1].toLowerCase();
                min_players = Integer.parseInt(args[2]);
                max_players = Integer.parseInt(args[3]);

                if (checkIfArenaExists(arenaname)) {

                    //already exists

                    player.sendMessage(Utils.color("&cThe arena &e" + arenaname + " &calready exists!"));

                } else {

                    String[] values = {arenaname, Integer.toString(min_players), Integer.toString(max_players), "false", "{}", "false"};
                    connector.insert("au_arenas", Utils.DBArenaNames, values, Utils.DBArenaTypes);
                    Arena thearena = new Arena(arenaname, min_players, max_players);
                    Maps.loadedArenas.put(arenaname, thearena);
                    player.sendMessage(Utils.color("&aPerfect! Now Go to the waitingLobby of the game and execute: \n &e/au waitingLobby [Arena name]"));

                }

            } else {
                player.sendMessage(Utils.color("&4Bad usage: &c/au createarena [Arena name] [Min players] [Max players]"));
            }

        } else {

            player.sendMessage(Utils.color("&4Bad usage: &c/au createarena [Arena name] [Min players] [Max players]"));
        }


    }

    private void waitingLobby(Player player, String[] args) throws SQLException {

        if (args.length == 2) {

            Location loc = player.getLocation();
            String waitingLocation;
            String arenaname = args[1].toLowerCase();

            if (checkIfArenaExists(arenaname)) {
                Arena thatarena = Maps.loadedArenas.get(arenaname);
                waitingLocation = Utils.convertLocToString(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), loc.getPitch(), loc.getYaw());
                connector.update("au_arenas", "waiting_lobby", waitingLocation, "string", "name", arenaname);
                connector.update("au_arenas", "world", player.getWorld().getName(), "string", "name", arenaname);
                thatarena.setWaitingLocation(loc);
                player.sendMessage(Utils.color("&aOK, Now you have to set spawning locations for players: \n &e/au setspawn [Arena] \n &e/au removespawn [Arena]"));


            } else {

                player.sendMessage(Utils.color("&cThis arena whether is not loaded or doesn't exist!"));

            }


        } else {

            player.sendMessage(Utils.color("&4Bad usage: &c/au waitingLobby [Arena name]"));
        }


    }



    private void setSpawnPoint(Player player, String[] args) throws SQLException {

        if (args.length == 2) {
            String arenaname = args[1].toLowerCase();


            if (checkIfArenaExists(arenaname)) {
                Arena thatarena = Maps.loadedArenas.get(arenaname);

                int maxcount = connector.getSingleRow("au_arenas", "name", arenaname).getInt("max_player");
                String locations = connector.getSingleRow("au_arenas", "name", arenaname).getString("spawn_locations");

                if (maxcount == 0) {

                    player.sendMessage(Utils.color("&cThere is no arena called " + "&e" + arenaname));

                } else {

                    String worldname = player.getWorld().getName();
                    thatarena.setWorldName(worldname);
                    connector.update("au_arenas", "worldname", worldname, "string", "name", arenaname);
                    String Correct_locations = connector.getSingleRow("au_arenas", "name", arenaname).getString("spawn_locations");
                    if (Correct_locations.equals("{}")) {
                        int Location_set = g.fromJson(connector.getSingleRow("au_arenas", "name", arenaname).getString("spawn_locations"), List.class).size();
                        int Location_left = connector.getSingleRow("au_arenas", "name", arenaname).getInt("max_players") - Location_set;
                        player.sendMessage(Utils.color("&aFirst spawn location has been set ! there are  " + Location_left + " location left ! set more with &e/au setspawn [Arena name]"));
                        ArrayList<String> spawn_locations = new ArrayList<String>();
                        Location loc = player.getLocation();
                        thatarena.addSpawnLocation(loc);
                        String spawn_location = Utils.convertLocToString(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), loc.getPitch(), loc.getYaw());
                        spawn_locations.add(spawn_location);
                        String spawn_locations_string = g.toJson(spawn_locations);
                        connector.update("au_arenas", "spawn_locations", spawn_locations_string, "string", "name", arenaname);
                    } else {
                        int Location_set = g.fromJson(connector.getSingleRow("au_arenas", "name", arenaname).getString("spawn_locations"), List.class).size();
                        int Location_left = connector.getSingleRow("au_arenas", "name", arenaname).getInt("max_players") - Location_set;
                        ArrayList<String> spawn_locations = g.fromJson(locations, ArrayList.class);
                        Location loc = player.getLocation();
                        thatarena.addSpawnLocation(loc);
                        String spawn_location = Utils.convertLocToString(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), loc.getPitch(), loc.getYaw());
                        spawn_locations.add(spawn_location);
                        String spawn_locations_string = g.toJson(spawn_locations);
                        connector.update("au_arenas", "spawn_locations", spawn_locations_string, "string", "name", arenaname);
                        if (g.fromJson(locations, ArrayList.class).size() != maxcount) {
                            player.sendMessage(Utils.color("&aNext spawn location has been set ! there are  " + Location_left + " location left ! set more with &e/au setspawn [Arena name]"));
                        } else {
                            player.sendMessage(Utils.color("&aAll added! Now you can set Tasks or Sabotages locations"));
                        }
                    }


                }

            } else {

                player.sendMessage(Utils.color("&cThis arena whether is not loaded or doesn't exist!"));


            }


        } else {

            player.sendMessage(Utils.color("&4Bad usage: &c/au setspawn [Arena name]"));

        }

    }

    private void removeLastSpawnPoint(Player player, String[] args) throws SQLException {
        if (args.length == 2) {
            String arenaname = args[1].toLowerCase();

            if (checkIfArenaExists(arenaname)) {
                Arena thatarena = Maps.loadedArenas.get(arenaname);

                int maxcount = connector.getSingleRow("au_arenas", "name", arenaname).getInt("max_player");
                String locations = connector.getSingleRow("au_arenas", "name", arenaname).getString("spawn_locations");
                ArrayList<String> decoded_locations = g.fromJson(locations, ArrayList.class);
                if (decoded_locations.size() != maxcount) {
                    thatarena.removeLastSpawnLocation();
                    decoded_locations.remove(decoded_locations.size() - 1);
                    player.sendMessage("&aYour last Spawn point removed !");
                    String locations_encoded = g.toJson(decoded_locations);
                    connector.update("au_arenas", "spawn_locations", locations_encoded, "string", "name", arenaname);

                } else {

                    player.sendMessage("&cThere is no location that remains.");

                }

            } else {

                player.sendMessage(Utils.color("&cThis arena whether is not loaded or doesn't exist!"));

            }

        } else {
            player.sendMessage("&cUsage : /au removespawn <arenaname>");
        }
    }

    private void setTask(Player player, String[] args) throws SQLException {
        if (args.length == 3) {
            String arenaname = args[1].toLowerCase();

            if (checkIfArenaExists(arenaname)) {

                Arena thatarena = Maps.loadedArenas.get(arenaname);

                if (Utils.TASK_TYPES_ARRAY_LIST.contains(args[2].toUpperCase())) {
                    if (connector.c2NumRows("au_arenas_meta", "name", arenaname, "meta_id", args[2].toUpperCase() + "_TASK_LOCATION") == 0) {

                        Location C_Location = player.getTargetBlock(null, 5).getLocation();

                        //????????? badesh chi?
                    } else {
                        player.sendMessage("&aYou have set this task location past ! delete it with : /au deletetask <arenaname> " + args[2]);
                    }
                } else {
                    player.sendMessage("&cThere is no task called &e" + args[2]);
                }

            } else {

                player.sendMessage(Utils.color("&cThis arena whether is not loaded or doesn't exist!"));

            }


        } else {
            player.sendMessage("&cUsage : /au settask <arenaname> <task>");
        }
    }

    private void deleteTask(Player player, String[] args) throws SQLException {
        if (args.length == 3) {
            String arenaname = args[1].toLowerCase();

            if (Utils.TASK_TYPES_ARRAY_LIST.contains(args[2].toUpperCase())) {

                if (connector.c2NumRows("au_arenas_meta", "name", arenaname, "meta_id", args[2].toUpperCase() + "_TASK_LOCATION") > 0) {

                    connector.c2Delete("au_arenas_meta", "name", arenaname, "meta_id", args[2].toUpperCase() + "_TASK_LOCATION");

                    player.sendMessage("task " + args[2] + "has been deleted !");

                } else {

                    player.sendMessage("You have'nt set this task yet !");

                }

            } else {

                player.sendMessage("There is no task in name " + args[2]);
            }

        } else {

            player.sendMessage("&cUsage : /au deletetask <arenaname> <task>");

        }
    }

    private void setSabotage(Player player, String[] args) throws SQLException {
        if (args.length == 3) {
            String arenaname = args[1].toLowerCase();
            if (Utils.SABOTAGE_TYPES_ARRAY_LIST.contains(args[2].toUpperCase())) {
                if (connector.c2NumRows("au_arenas_meta", "name", arenaname, "meta_id", args[2].toUpperCase() + "_SABOTAGE_LOCATION") == 0) {

                    Location C_Location = player.getTargetBlock(null, 5).getLocation();
                } else {
                    player.sendMessage("You have set this sabotage location past ! delete it with : /au deletesabotage <arenaname> " + args[2]);
                }
            } else {
                player.sendMessage("There is no sabotage in name " + args[2]);
            }
        } else {
            player.sendMessage("&cUsage : /au setsabotage <arenaname> <sabotage>");
        }
    }

    private void deleteSabotage(Player player, String[] args) throws SQLException {
        if (args.length == 3) {
            String arenaname = args[1].toLowerCase();
            if (Utils.SABOTAGE_TYPES_ARRAY_LIST.contains(args[2].toUpperCase())) {
                if (connector.c2NumRows("au_arenas_meta", "name", arenaname, "meta_id", args[2].toUpperCase() + "_SABOTAGE_LOCATION") > 0) {
                    player.sendMessage("task " + args[2] + "has been deleted !");
                    connector.c2Delete("au_arenas_meta", "name", arenaname, "meta_id", args[2].toUpperCase() + "_SABOTAGE_LOCATION");
                } else {
                    player.sendMessage("You haven't set this sabotage yet !");
                }
            } else {
                player.sendMessage("There is no sabotage in name " + args[2]);
            }
        } else {
            player.sendMessage("&cUsage : /au deletesabotage <arenaname> <sabotage>");
        }
    }



    private void deleteArena(Player player, String[] args) throws SQLException {

        if (args.length == 2) {
            String arenaname = args[1].toLowerCase();
            if (Maps.loadedArenas.containsKey(arenaname)) {
                Arena thatarena = Maps.loadedArenas.get(arenaname);
                if (thatarena.getGameState() == GameState.WAITING) {
                    resetTheArena(thatarena);
                    connector.delete("au_arenas", "name", arenaname);
                    Maps.loadedArenas.remove(arenaname);

                    player.sendMessage(Utils.color("&aArena &e" + arenaname + " &asuccessfully deleted!"));

                } else {
                    player.sendMessage(Utils.color("&cYou can not delete that arena while it is in the game!"));
                }

            } else {

                player.sendMessage(Utils.color("&cThe arena is whether not loaded or doesn't exist!"));

            }


        } else {
            player.sendMessage("&4Bad usage: &c/au deleteArena <arenaname>");
        }


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (args.length >= 1) {
                // - /au createarena [Arena name] [Min players] [Max players] - (Creating Arena Command)
                if (args[0].equalsIgnoreCase("createarena")) {


                    try {
                        createArena(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    // - /au waitingLobby [Arena name] - (Getting waiting lobby location)
                } else if (args[0].equalsIgnoreCase("waitinglobby")) {

                    try {
                        waitingLobby(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }// - /au setspawn
                else if (args[0].equalsIgnoreCase("setspawn")) {

                    try {
                        setSpawnPoint(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
                else if (args[0].equalsIgnoreCase("removespawn")) {

                    try {
                        removeLastSpawnPoint(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
                else if (args[0].equalsIgnoreCase("settask")) {

                    try {
                        setTask(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }

                else if (args[0].equalsIgnoreCase("deletetask")) {

                    try {
                        deleteTask(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }

                else if (args[0].equalsIgnoreCase("setsabotage")) {

                    try {
                        setSabotage(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }

                else if (args[0].equalsIgnoreCase("deletesabotage")) {

                    try {
                        deleteSabotage(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }

                else if (args[0].equalsIgnoreCase("deletearena")) {

                    try {
                        deleteArena(player, args);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            } else {
                try {
                    for (String msg: Utils.getMessages().getStringList("messages.help")) {
                        player.sendMessage(Utils.color(msg));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }


        } else {

            Utils.logger(Utils.Prefix + " &cThis Command Can not be executed from the console. Try in the game!");

        }

        return false;
    }

    private void resetTheArena(Arena arena) {
        List<Player> players = arena.getPlayers();
        for (Player player : players) {

            player.teleport(Utils.lobby);

        }
    }

    private boolean checkIfArenaExists(String str) {

        if (Maps.loadedArenas.containsKey(str)) {

            return true;

        } else {

            return false;

        }

    }


}
