package de.ase34.flyingblocksapi.commands.examples;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ase34.flyingblocksapi.FlyingBlock;

public class FlagCommandExecutor implements CommandExecutor {

    public class FlagFlyingBlock extends FlyingBlock {
        private final Location playerLocation;

        public FlagFlyingBlock(Material material, byte materialData, int trackerUpdateInterval,
                Location playerLocation) {
            super(material, materialData, trackerUpdateInterval);
            this.playerLocation = playerLocation;
        }

        @Override
        public void onTick() {
            // variables
            double t = getBukkitEntity().getWorld().getTime();
            double x = getBukkitEntity().getLocation().getX() - playerLocation.getX();

            // constants
            double a = 0.3; // wavelength
            double b = Math.PI / 20; // frequency
            double s = 0.2; // slope

            // actual math
            double y = Math.sin(x * a - t * b) * s * x;

            // set the position
            Location location = getBukkitEntity().getLocation();
            location.setZ(y + playerLocation.getZ());
            setLocation(location);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only available as player!");
            return true;
        }

        final Player player = (Player) sender;
        final Location playerLocation = player.getLocation().clone();

        // variables
        int width = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        int height = args.length > 1 ? Integer.parseInt(args[1]) : 6;
        final int flagColor = args.length > 2 ? Integer.parseInt(args[2]) : 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // create on each position a new flying block

                FlyingBlock block = new FlagFlyingBlock(Material.WOOL, (byte) flagColor, 1,
                        playerLocation);
                block.spawn(playerLocation.clone().add(x, y, 0));
            }
        }

        player.sendMessage(ChatColor.GRAY + "Sucessfully spawned a new flag!");
        return true;
    }
}