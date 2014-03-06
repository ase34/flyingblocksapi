/**
 * flyingblocksapi Copyright (C) 2014 ase34 and contributors
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
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