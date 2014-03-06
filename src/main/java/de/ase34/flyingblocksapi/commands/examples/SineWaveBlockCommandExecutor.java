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
package de.ase34.flyingblocksapi.commands.examples;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.ase34.flyingblocksapi.FlyingBlock;

public class SineWaveBlockCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // player instance check
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only available as player!");
            return true;
        }

        Player player = (Player) sender;
        final Location playerLocation = player.getLocation().clone();
        final long startTime = player.getWorld().getFullTime();
        // we save the creation time so that the sine wave starts at its origin (x=0; y=sin(x)=0)

        // variables
        final double periodSeconds = args.length > 0 ? Double.parseDouble(args[0]) : 3;
        int trackerUpdateInterval = args.length > 1 ? Integer.parseInt(args[1]) : 4;

        // anonymous class
        FlyingBlock block = new FlyingBlock(Material.STONE, (byte) 0, trackerUpdateInterval) {
            @Override
            public void onTick() {
                // constants
                double amplitude = 3.0; // peak amplitude of 3 (-3 to 3)
                double period = (2 * Math.PI) / (periodSeconds * 20); // period of `periodSeconds` seconds

                // variables
                double time = getBukkitEntity().getWorld().getFullTime();

                // math
                double y = Math.sin((time - startTime) * period) * amplitude;
                double nexty = Math.sin((time + 1 - startTime) * period) * amplitude;
                // we calculate the next y value so we can compute the current velocity

                setLocation(playerLocation.clone().add(0, y + getHeightOffset(), 0));
                // we add the height offset because we are modifying the coordinates of the skull, not the block
                setVelocity(new Vector(0, nexty - y, 0));
                // velocity until the next tick
            }
        };
        // spawn block
        block.spawn(playerLocation);

        sender.sendMessage(ChatColor.GRAY + "Sucessfully spawned a block moving in a sine wave!");
        return true;
    }

}