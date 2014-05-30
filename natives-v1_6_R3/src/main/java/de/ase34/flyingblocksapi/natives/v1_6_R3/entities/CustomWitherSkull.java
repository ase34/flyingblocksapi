/**
 * flyingblocksapi Copyright (C) 2014 ase34 and contributors
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.ase34.flyingblocksapi.natives.v1_6_R3.entities;

import de.ase34.flyingblocksapi.FlyingBlock;
import net.minecraft.server.v1_6_R3.EntityTracker;
import net.minecraft.server.v1_6_R3.EntityWitherSkull;
import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldServer;

public class CustomWitherSkull extends EntityWitherSkull {

    private final FlyingBlock flyingBlock;

    private boolean initial = true;

    public CustomWitherSkull(FlyingBlock flyingBlock, World world) {
        super(world);
        this.flyingBlock = flyingBlock;
    }

    public CustomWitherSkull(World world) {
        super(world);
        this.flyingBlock = null;
        initial = false;
    }

    @Override
    public void l_() {
        // tick entity
        if (flyingBlock != null) {
            flyingBlock.onTick();
        }

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