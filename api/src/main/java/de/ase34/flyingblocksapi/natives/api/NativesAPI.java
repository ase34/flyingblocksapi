package de.ase34.flyingblocksapi.natives.api;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Entity;

import de.ase34.flyingblocksapi.FlyingBlock;

public abstract class NativesAPI {

    protected static NativesAPI singleton;

    public static NativesAPI getSingleton() {
        return singleton;
    }

    public static void setSingleton(NativesAPI singleton) {
        NativesAPI.singleton = singleton;
    }

    public abstract List<Entity> removeFlyingBlocks(World world);

    public abstract void initialize();

    public abstract NativeFlyingBlockHandler createFlyingBlockHandler(FlyingBlock flyingBlock);

}
