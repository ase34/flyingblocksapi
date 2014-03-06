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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.ase34.flyingblocksapi.FlyingBlock;

public class StaticBlockCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // player check
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only available as player!");
            return true;
        }

        // variables
        int materialId = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        int materialData = args.length > 1 ? Integer.parseInt(args[1]) : 0;

        @SuppressWarnings("deprecation")
        Material material = Material.getMaterial(materialId);
        if (material == null) {
            sender.sendMessage(ChatColor.RED + "This material id does not seem to exist!");
            return true;
        }

        // anonymous class
        FlyingBlock block = new FlyingBlock(material, (byte) materialData) {
            @Override
            public void onTick() {
                if (this.getBukkitEntity().getVelocity().lengthSquared() != 0) {
                    // hum, weird velocity, override...
                    this.getBukkitEntity().setVelocity(new Vector(0, 0, 0));
                }
            }
        };
        // spawn block
        block.spawn(((Player) sender).getLocation());

        sender.sendMessage(ChatColor.GRAY + "Sucessfully spawned a rising block!");
        return true;
    }

}