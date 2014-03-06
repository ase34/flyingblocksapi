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
        final Location playerLocation = player.getLocation().clone().add(new Vector(0, 0.5, 0));
        // we add 0.5 to the y-coordinate so that the center is not at the player's feet (take a look at the
        // FlyingBlocks constructor)
        final long startTime = player.getWorld().getFullTime();
        // we save the creation time so that the sine wave starts at its origin (x=0; y=sin(x)=0)

        // variables
        final double periodSeconds = args.length > 0 ? Double.parseDouble(args[0]) : 3;
        int trackerUpdateInterval = args.length > 1 ? Integer.parseInt(args[1]) : 4;

        // anonymous class
        FlyingBlock block = new FlyingBlock(Material.STONE, (byte) 0, trackerUpdateInterval,
                FlyingBlock.OFFSET - 0.5, FlyingBlock.AGE) {
            // we used FlyingBlock.OFFSET - 0.5 so that the computed locations represent the center of the block, not
            // the downfacing side
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