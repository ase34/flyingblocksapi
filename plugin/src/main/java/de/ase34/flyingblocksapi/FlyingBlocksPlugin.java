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

package de.ase34.flyingblocksapi;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.ase34.flyingblocksapi.commands.ExamplesCommandExecutor;
import de.ase34.flyingblocksapi.commands.RemoveAllCommandExecutor;
import de.ase34.flyingblocksapi.natives.api.NativesAPI;

public class FlyingBlocksPlugin extends JavaPlugin implements Listener {

    private NativesAPI nativesAPI;

    @Override
    public void onEnable() {
        createNativesAPI();
        
        nativesAPI.initialize();
        NativesAPI.setSingleton(nativesAPI);
        
        
        getCommand("flyingblocks-removeall").setExecutor(new RemoveAllCommandExecutor(this));
        getCommand("flyingblocks-examples").setExecutor(new ExamplesCommandExecutor());

        getServer().getPluginManager().registerEvents(this, this);
    }

    private void createNativesAPI() {
        String packageName = this.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        
        try {
            final Class<?> clazz = Class.forName("de.ase34.flyingblocksapi.natives." + version + ".NativesAPI");
            if (NativesAPI.class.isAssignableFrom(clazz)) { 
                this.nativesAPI = (NativesAPI) clazz.getConstructor().newInstance();
            }
        } catch (final Exception e) {
            throw new RuntimeException("Could not find support for CraftBukkit version: " + version);
        }
    }

    @Override
    public void onDisable() {
        for (World world : Bukkit.getWorlds()) {
            removeFlyingBlocks(world);
        }
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent ev) {
        removeFlyingBlocks(ev.getWorld());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent ev) {
        World world = ev.getPlayer().getWorld();
        if (world.getPlayers().size() == 1) {
            // this is the last player
            removeFlyingBlocks(world);
        }
    }

    public List<Entity> removeFlyingBlocks(World world) {
        return nativesAPI.removeFlyingBlocks(world);
    }
    
    public NativesAPI getNativesAPI() {
        return nativesAPI;
    }

}
