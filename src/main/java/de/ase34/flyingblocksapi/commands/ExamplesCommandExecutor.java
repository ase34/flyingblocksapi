package de.ase34.flyingblocksapi.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ase34.flyingblocksapi.commands.examples.FlagCommandExecutor;
import de.ase34.flyingblocksapi.commands.examples.RisingBlockCommandExecutor;
import de.ase34.flyingblocksapi.commands.examples.SineWaveBlockCommandExecutor;
import de.ase34.flyingblocksapi.commands.examples.StaticBlockCommandExecutor;

public class ExamplesCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String example = args[0];
        CommandExecutor executor;

        if (example.equalsIgnoreCase("flag")) {
            executor = new FlagCommandExecutor();
        } else if (example.equalsIgnoreCase("rising")) {
            executor = new RisingBlockCommandExecutor();
        } else if (example.equalsIgnoreCase("static")) {
            executor = new StaticBlockCommandExecutor();
        } else if (example.equalsIgnoreCase("sinewave")) {
            executor = new SineWaveBlockCommandExecutor();
        } else {
            return false;
        }

        String[] newargs = Arrays.copyOfRange(args, 1, args.length);
        return executor.onCommand(sender, command, label, newargs);

    }

}