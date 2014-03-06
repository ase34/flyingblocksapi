package de.ase34.flyingblocksapi.entities;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityFallingBlock;
import net.minecraft.server.v1_7_R1.World;

public class CustomFallingBlock extends EntityFallingBlock {

    private final Entity forcedVehicle;

    public CustomFallingBlock(Entity forcedVehicle, World world) {
        super(world);
        this.forcedVehicle = forcedVehicle;
    }

    @Override
    public void h() {
        // set vehicle
        this.setPassengerOf(forcedVehicle);
    }

}