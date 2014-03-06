package de.ase34.flyingblocksapi.entities;

import net.minecraft.server.v1_7_R1.EntityHorse;
import net.minecraft.server.v1_7_R1.World;

public class CustomHorse extends EntityHorse {

    public CustomHorse(World world) {
        super(world);
    }

    @Override
    public void h() {
        // do nothing
    }

}