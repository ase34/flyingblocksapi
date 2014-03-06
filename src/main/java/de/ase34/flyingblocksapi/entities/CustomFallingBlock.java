/**
 * flyingblocksapi
 * Copyright (C) 2014 ase34 and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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