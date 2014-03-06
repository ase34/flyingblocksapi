package de.ase34.flyingblocksapi.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ase34.flyingblocksapi.FlyingBlocksPlugin;

public class RemoveAllCommandExecutor implements CommandExecutor {

    private FlyingBlocksPlugin plugin;

    public RemoveAllCommandExecutor(FlyingBlocksPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int counter = 0;

        for (World world : Bukkit.getWorlds()) {
            counter += plugin.removeFlyingBlocks(world).size();
        }

        // 3 entities per moving block
        counter = counter / 3;
        sender.sendMessage(ChatColor.GRAY + "" + counter + " flying "
                + (counter == 1 ? "block was" : "blocks were") + " removed!");
        return true;
    }

}