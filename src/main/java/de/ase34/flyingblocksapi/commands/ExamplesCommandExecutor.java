/**
 * flyingblocksapi
 * Copyright (C) 2014 ase34 and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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