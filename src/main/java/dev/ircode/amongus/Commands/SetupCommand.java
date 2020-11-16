package dev.ircode.amongus.Commands;
import dev.ircode.amongus.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

            Integer.parseInt(min);
            Integer.parseInt(max);

        } catch (Exception ex) {

            result = false;

        }
        return result;
    }


    private void createArena(Player player, String[] args) {

        if (args.length == 4) {

            String arenaname;
            int min_players;
            int max_players;

            if (checkIfEveryArgIsOk(args[2], args[3])) {

                arenaname = args[1];
                min_players = Integer.parseInt(args[2]);
                max_players = Integer.parseInt(args[3]);

                //Database set kn mehdi in 3 ta chizaro:
                //arena name
                //max players
                // min players


                player.sendMessage(Utils.color("&aPerfect! Now Go to the waitingLobby of the game and execute: \n &e/au waitingLobby [Arena name]"));

            } else {
                player.sendMessage(Utils.color("&4Bad usage: &c/au createarena [Arena name] [Min players] [Max players]"));
            }

        } else {

            player.sendMessage(Utils.color("&4Bad usage: &c/au createarena [Arena name] [Min players] [Max players]"));
        }


    }

    private void waitingLobby(Player player, String[] args) {


        if (args.length == 2) {
            Location loc = player.getLocation();
            String waitingLocation;
            waitingLocation = Utils.convertLocToString(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), loc.getPitch(), loc.getYaw());
            //mehdi inja database befrest
            //waitingLocation (String)

            player.sendMessage(Utils.color("&aOK, Now you have to set spawning locations for players: \n &e/au setspawn [Arena] \n &e/au removespawn [Arena]"));

        } else {

            player.sendMessage(Utils.color("&4Bad usage: &c/au waitingLobby [Arena name]"));
        }


    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (args.length >= 1) {
                // - /au createarena [Arena name] [Min players] [Max players] - (Creating Arena Command)
                if (args[0].equalsIgnoreCase("createarena")) {

                        createArena(player, args);

                // - /au waitingLobby [Arena name] - (Getting waiting lobby location)
                } else if (args[0].equalsIgnoreCase("waitinglobby")) {

                    waitingLobby(player, args);

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
