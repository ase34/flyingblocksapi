package de.ase34.flyingblocksapi.natives.v1_6_R3;

import net.minecraft.server.v1_6_R3.EntityWitherSkull;
import net.minecraft.server.v1_6_R3.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftWitherSkull;
import org.bukkit.entity.WitherSkull;

import de.ase34.flyingblocksapi.FlyingBlock;
import de.ase34.flyingblocksapi.natives.v1_6_R3.util.SpawnHelper;

public class NativeFlyingBlockHandler extends de.ase34.flyingblocksapi.natives.api.NativeFlyingBlockHandler {

    private EntityWitherSkull entity;
    
    public NativeFlyingBlockHandler(FlyingBlock flyingBlock) {
        super(flyingBlock);
    }

    @Override
    public void spawnFlyingBlock(Location location) {
        if (entity != null && entity.isAlive()) {
            flyingBlock.remove();
        }

        entity = SpawnHelper.spawn(flyingBlock, location);
    }

    @Override
    public void removeEntites() {
        if (entity == null) {
            return;
        }

        entity.passenger.passenger.die();
        entity.passenger.die();
        entity.die();
        entity = null;
    }

    @Override
    public void setFlyingBlockLocation(Location location) {
        if (entity == null || !entity.isAlive()) {
            throw new UnsupportedOperationException("The entity was not spawned yet!");
        }

        World newWorld = ((CraftWorld) location.getWorld()).getHandle();
        if (newWorld != entity.world) {
            flyingBlock.remove();
            flyingBlock.spawn(location);
        } else {
            entity.setLocation(location.getX(), location.getY(), location.getZ(),
                    location.getYaw(), location.getPitch());
        }
    }

    @Override
    public WitherSkull getBukkitEntity() {
        return getCraftBukkitEntity();
    }

    public CraftWitherSkull getCraftBukkitEntity() {
        return (CraftWitherSkull) entity.getBukkitEntity();
    }

}
