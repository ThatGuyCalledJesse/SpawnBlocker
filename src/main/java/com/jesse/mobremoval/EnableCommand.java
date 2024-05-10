package com.jesse.mobremoval;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class EnableCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            MobRemoval plugin = MobRemoval.getPlugin(MobRemoval.class);
            FileConfiguration config = plugin.getConfig();

            if (args.length == 0) {
                sender.sendMessage("MobBlocker is currently set to " + config.get("isEnabled"));
                sender.sendMessage("Change this value by using /spawnblocker <true/false>");
                return true;
            }

            try {
                boolean isEnabled = Boolean.parseBoolean(args[0]);
                config.set("isEnabled", isEnabled);
                plugin.saveConfig(); // Save the configuration changes
                sender.sendMessage("MobRemoval is now set to " + isEnabled);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("Invalid value for boolean: " + args[0]);
            }
            return true;
        }
        return false;
    }
}
