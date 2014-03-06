package de.ase34.flyingblocksapi.commands.examples;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.ase34.flyingblocksapi.FlyingBlock;

public class RisingBlockCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // player check
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only available as player!");
            return true;
        }

        // variables
        final double velocity = args.length > 0 ? Double.parseDouble(args[0]) : 0.05;

        // constants
        final Vector velocityVector = new Vector(0, velocity, 0);
        int trackerUpdateInterval = 4;
        // we can use a high update interval (0.5 seconds) because the velocity mainly handles the movement

        // anonymous class
        FlyingBlock block = new FlyingBlock(Material.STONE, (byte) 0, trackerUpdateInterval) {
            @Override
            public void onTick() {
                // set velocity
                if (!this.getBukkitEntity().getVelocity().equals(velocityVector)) {
                    // huh, wrong velocity, override...
                    this.getBukkitEntity().setVelocity(velocityVector);
                }
            }
        };
        // spawn block
        block.spawn(((Player) sender).getLocation());

        sender.sendMessage(ChatColor.GRAY + "Sucessfully spawned a rising block!");
        return true;
    }

}