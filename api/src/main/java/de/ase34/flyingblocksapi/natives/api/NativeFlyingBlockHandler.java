package de.ase34.flyingblocksapi.natives.api;

import org.bukkit.Location;
import org.bukkit.entity.WitherSkull;

import de.ase34.flyingblocksapi.FlyingBlock;

public abstract class NativeFlyingBlockHandler {

    protected FlyingBlock flyingBlock;

    public NativeFlyingBlockHandler(FlyingBlock flyingBlock) {
        this.flyingBlock = flyingBlock;
    }

    public abstract void spawnFlyingBlock(Location location);

    public abstract void removeEntites();

    public abstract void setFlyingBlockLocation(Location location);

    public abstract WitherSkull getBukkitEntity();

}
