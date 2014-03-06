package de.ase34.flyingblocksapi.entities;

import net.minecraft.server.v1_7_R1.EntityTracker;
import net.minecraft.server.v1_7_R1.EntityWitherSkull;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldServer;
import de.ase34.flyingblocksapi.FlyingBlock;

public class CustomWitherSkull extends EntityWitherSkull {

    private final FlyingBlock flyingBlock;

    private boolean initial;

    public CustomWitherSkull(FlyingBlock flyingBlock, World world) {
        super(world);
        this.flyingBlock = flyingBlock;
    }

    @Override
    public void h() {
        // tick entity
        flyingBlock.onTick();
        super.h();

        if (initial) {
            // set tracker
            EntityTracker tracker = ((WorldServer) this.world).tracker;
            tracker.untrackEntity(this);
            tracker.addEntity(this, 64, flyingBlock.getTrackerUpdateInterval());

            initial = true;
        }
    }

}