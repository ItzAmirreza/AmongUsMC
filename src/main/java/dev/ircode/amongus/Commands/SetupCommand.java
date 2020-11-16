package dev.ircode.amongus.Commands;
import com.google.gson.Gson;
import dev.ircode.amongus.Utils.Utils;
import dev.ircode.amongus.database.connector;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

import java.sql.SQLException;
import java.util.List;

public class SetupCommand implements CommandExecutor {


    public boolean isLast(List<Location> locations, int max_players) {
        if (locations.size() != max_players) {
            return false;
        } else {
            return true;
        }
    }



    public boolean checkIfEveryArgIsOk(String min, String max) {
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

            if (checkIfEveryArgIsOk(args[2], args[3])) {

                arenaname = args[1];
                min_players = Integer.parseInt(args[2]);
                max_players = Integer.parseInt(args[3]);


                String[] values = {arenaname, Integer.toString(min_players), Integer.toString(max_players), "false", "{}", "false"};
                connector.insert("au_arenas", Utils.DBArenaNames, values, Utils.DBArenaTypes);


                player.sendMessage(Utils.color("&aPerfect! Now Go to the waitingLobby of the game and execute: \n &e/au waitingLobby [Arena name]"));

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
            String arenaname = args[1];
            waitingLocation = Utils.convertLocToString(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), loc.getPitch(), loc.getYaw());
            //mehdi inja database befrest
            //waitingLocation (String)

            player.sendMessage(Utils.color("&aOK, Now you have to set spawning locations for players: \n &e/au setspawn [Arena] \n &e/au removespawn [Arena]"));
            connector.update("au_arenas", "waiting_lobby", waitingLocation, "string", "name", arenaname);
        } else {

            player.sendMessage(Utils.color("&4Bad usage: &c/au waitingLobby [Arena name]"));
        }


    }


    public Gson g = new Gson();

    private void setSpawnPoint(Player player, String[] args) throws SQLException {

        if (args.length == 2) {
            String arenaname = args[1];
            int maxcount = connector.getSingleRow("au_arenas", "name", args[1]).getInt("max_player");
            String locations = connector.getSingleRow("au_arenas", "name", args[1]).getString("spawn_locations");

            if (maxcount == 0) {

                player.sendMessage(Utils.color("&cThere is no arena called " + "&e" + arenaname));


            } else {

                String json = g.toJson(locations);
                //continue tomorrow
                //int listSize = new JSONArray().size();

                //int remainingCount = maxcount - listSize;


            }


        } else {

            player.sendMessage(Utils.color("&4Bad usage: &c/au setspawn [Arena name]"));

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

                }

            } else {

                //help Command

            }


        } else {

            Utils.logger(Utils.Prefix + " &cThis Command Can not be executed from the console. Try in the game!");

        }

        return false;
    }
}
