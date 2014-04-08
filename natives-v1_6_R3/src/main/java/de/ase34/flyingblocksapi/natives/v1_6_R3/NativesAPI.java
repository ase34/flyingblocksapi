package de.ase34.flyingblocksapi.natives.v1_6_R3;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.entity.Entity;

import de.ase34.flyingblocksapi.FlyingBlock;
import de.ase34.flyingblocksapi.natives.v1_6_R3.entites.CustomFallingBlock;
import de.ase34.flyingblocksapi.natives.v1_6_R3.entites.CustomHorse;
import de.ase34.flyingblocksapi.natives.v1_6_R3.entites.CustomWitherSkull;
import de.ase34.flyingblocksapi.natives.v1_6_R3.util.EntityRegistrator;

public class NativesAPI extends de.ase34.flyingblocksapi.natives.api.NativesAPI {

    @Override
    public List<Entity> removeFlyingBlocks(World world) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        net.minecraft.server.v1_6_R3.World nmsworld = ((CraftWorld) world).getHandle();

        for (Object entityObject : nmsworld.entityList) {
            net.minecraft.server.v1_6_R3.Entity entity = (net.minecraft.server.v1_6_R3.Entity) entityObject;

            if (entity instanceof CustomFallingBlock) {
                entity.die();
                entities.add(entity.getBukkitEntity());
            } else if (entity instanceof CustomHorse) {
                entity.die();
                entities.add(entity.getBukkitEntity());
            } else if (entity instanceof CustomWitherSkull) {
                entity.die();
                entities.add(entity.getBukkitEntity());
            }
        }

        return entities;
    }

    @Override
    public void initialize() {
        EntityRegistrator.registerCustomEntity(CustomFallingBlock.class, "fba.fallingblock", 21);
        EntityRegistrator.registerCustomEntity(CustomHorse.class, "fba.horse", 100);
        EntityRegistrator.registerCustomEntity(CustomWitherSkull.class, "fba.witherskull", 19);
    }

    @Override
    public de.ase34.flyingblocksapi.natives.api.NativeFlyingBlockHandler createFlyingBlockHandler(FlyingBlock flyingBlock) {
        return new NativeFlyingBlockHandler(flyingBlock);
    }

}
