package dev.ircode.amongus.Commands;

import dev.ircode.amongus.AmongUs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class au implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("au") || command.getName().equalsIgnoreCase("amongus")) {
            if (args.length == 0) {
                // help
            }
        }
        return false;
    }
}

