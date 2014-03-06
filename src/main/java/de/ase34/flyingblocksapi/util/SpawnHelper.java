package de.ase34.flyingblocksapi.util;

import net.minecraft.server.v1_7_R1.EntityFallingBlock;
import net.minecraft.server.v1_7_R1.EntityHorse;
import net.minecraft.server.v1_7_R1.EntityWitherSkull;
import net.minecraft.server.v1_7_R1.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;

import de.ase34.flyingblocksapi.FlyingBlock;
import de.ase34.flyingblocksapi.entities.CustomFallingBlock;
import de.ase34.flyingblocksapi.entities.CustomHorse;
import de.ase34.flyingblocksapi.entities.CustomWitherSkull;

public class SpawnHelper {

    public static EntityWitherSkull spawn(FlyingBlock flyingBlock, Location location) {
        World world = ((CraftWorld) location.getWorld()).getHandle();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        EntityWitherSkull skull = new CustomWitherSkull(flyingBlock, world);
        skull.setPosition(x, y + flyingBlock.getHeightOffset(), z);
        world.addEntity(skull);

        EntityHorse horse = new CustomHorse(world);
        horse.setAge(flyingBlock.getHorseAge());
        horse.setPosition(x, y + flyingBlock.getHeightOffset(), z);
        world.addEntity(horse);

        EntityFallingBlock block = new CustomFallingBlock(horse, world);
        block.id = CraftMagicNumbers.getBlock(flyingBlock.getMaterial());
        block.data = flyingBlock.getMaterialData();
        block.setPosition(x, y + flyingBlock.getHeightOffset(), z);
        world.addEntity(block);

        block.setPassengerOf(horse);
        horse.setPassengerOf(skull);

        return skull;
    }

}
