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
package de.ase34.flyingblocksapi;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R1.Entity;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.ase34.flyingblocksapi.commands.ExamplesCommandExecutor;
import de.ase34.flyingblocksapi.commands.RemoveAllCommandExecutor;
import de.ase34.flyingblocksapi.entities.CustomFallingBlock;
import de.ase34.flyingblocksapi.entities.CustomHorse;
import de.ase34.flyingblocksapi.entities.CustomWitherSkull;
import de.ase34.flyingblocksapi.util.EntityRegistrator;

public class FlyingBlocksPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        EntityRegistrator.registerCustomEntity(CustomFallingBlock.class, "fba.fallingblock", 21);
        EntityRegistrator.registerCustomEntity(CustomHorse.class, "fba.horse", 100);
        EntityRegistrator.registerCustomEntity(CustomWitherSkull.class, "fba.witherskull", 19);

        getCommand("flyingblocks-removeall").setExecutor(new RemoveAllCommandExecutor(this));
        getCommand("flyingblocks-examples").setExecutor(new ExamplesCommandExecutor());
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

    public List<Entity> removeFlyingBlocks(World world) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        net.minecraft.server.v1_7_R1.World nmsworld = ((CraftWorld) world).getHandle();

        for (Object entityObject : nmsworld.entityList) {
            Entity entity = (Entity) entityObject;

            if (entity instanceof CustomFallingBlock) {
                entity.die();
                entities.add(entity);
            } else if (entity instanceof CustomHorse) {
                entity.die();
                entities.add(entity);
            } else if (entity instanceof CustomWitherSkull) {
                entity.die();
                entities.add(entity);
            }
        }

        return entities;
    }

}
