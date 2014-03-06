package de.ase34.flyingblocksapi.entities;

import net.minecraft.server.v1_7_R1.EntityTracker;
import net.minecraft.server.v1_7_R1.EntityWitherSkull;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldServer;
import de.ase34.flyingblocksapi.FlyingBlock;

public class CustomWitherSkull extends EntityWitherSkull {

    private final FlyingBlock flyingBlock;

    private boolean initial = true;

    public CustomWitherSkull(FlyingBlock flyingBlock, World world) {
        super(world);
        this.flyingBlock = flyingBlock;
    }

    @Override
    public void h() {
        // tick entity
        flyingBlock.onTick();

        // direction is acceleration
        this.motX += this.dirX;
        this.motY += this.dirY;
        this.motZ += this.dirZ;

        // motion is speed
        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;

        this.setPosition(this.locX, this.locY, this.locZ);

        if (initial) {
            // set tracker
            EntityTracker tracker = ((WorldServer) this.world).tracker;
            tracker.untrackEntity(this);
            tracker.addEntity(this, 64, flyingBlock.getTrackerUpdateInterval());

            initial = true;
        }
    }

}