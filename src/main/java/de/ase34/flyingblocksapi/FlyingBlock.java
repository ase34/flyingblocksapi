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
package de.ase34.flyingblocksapi;

import net.minecraft.server.v1_7_R1.EntityWitherSkull;
import net.minecraft.server.v1_7_R1.World;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftWitherSkull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;

import de.ase34.flyingblocksapi.util.SpawnHelper;

/**
 * Handles the entities required for making a flying block.
 */
public abstract class FlyingBlock {

    /**
     * Default age for the horse.
     */
    public static int AGE = -4077000;
    /**
     * Default height offset for the skull.
     */
    public static double OFFSET = 100;
    /**
     * Default tracker update interval in ticks.
     */
    public static int UPDATE_INTERVAL = 2;

    private EntityWitherSkull entity;

    private final Material material;
    private final byte materialData;
    private final int trackerUpdateInterval;
    private final double heightOffset;
    private final int horseAge;

    /**
     * Convenience constructor, uses {@link FlyingBlock#FlyingBlock(Material, byte, int, double, int)} with
     * {@link FlyingBlock#AGE} as age parameter, {@link FlyingBlock#OFFSET} as offset parameter and
     * {@link FlyingBlock#UPDATE_INTERVAL} as update interval parameter.
     * 
     * @param material
     *            The material
     * @param materialData
     *            The material's data
     */
    public FlyingBlock(Material material, byte materialData) {
        this(material, materialData, UPDATE_INTERVAL, OFFSET, AGE);
    }

    /**
     * Convenience constructor, uses {@link FlyingBlock#FlyingBlock(Material, byte, int, double, int)} with
     * {@link FlyingBlock#AGE} as age parameter and {@link FlyingBlock#OFFSET} as offset parameter.
     * 
     * @param material
     *            The material
     * @param materialData
     *            The material's data
     * @param trackerUpdateInterval
     *            The update interval of the tracker (in ticks)
     */
    public FlyingBlock(Material material, byte materialData, int trackerUpdateInterval) {
        this(material, materialData, trackerUpdateInterval, OFFSET, AGE);
    }

    /**
     * Constructs a new {@link FlyingBlock}.
     * 
     * @param material
     *            The material
     * @param materialData
     *            The material's data
     * @param trackerUpdateInterval
     *            The update interval of the tracker (in ticks)
     * @param heightOffset
     *            The height offset of the skull
     * @param horseAge
     *            The age of the horse
     */
    public FlyingBlock(Material material, byte materialData, int trackerUpdateInterval,
            double heightOffset, int horseAge) {
        this.material = material;
        this.materialData = materialData;
        this.trackerUpdateInterval = trackerUpdateInterval;
        this.heightOffset = heightOffset;
        this.horseAge = horseAge;
    }

    /**
     * Creates and spawns this {@link FlyingBlock} at the specified position. The height offset will be automatically
     * applied so that the center of the falling block appears at the given position. If {@link #spawn(Location)} was
     * already called, this methods first invokes {@link #remove()}.
     * 
     * @param location
     *            The location to spawn the entity at
     */
    public void spawn(Location location) {
        if (entity != null && entity.isAlive()) {
            remove();
        }

        entity = SpawnHelper.spawn(this, location);
    }

    /**
     * This method gets called every tick and can be used to modify location and motion of this {@link FlyingBlock}.
     * Please note these special cases:
     * 
     * <ul>
     * <li>In order to change the motion/velocity/direction, please use
     * {@link Entity#setVelocity(org.bukkit.util.Vector)} if you are working with (Craft)Bukkit entities (
     * {@link #getBukkitEntity()}), or modify the public fields <code>motX</code>, <code>motY</code> and
     * <code>motZ</code> of {@link #getNMSEntity()}.</li>
     * <li>
     * In order to change the location, please use {@link #setLocation(Location)}, or if you are using
     * {@link #getNMSEntity()}, either modify the public fields <code>locX</code>, <code>locY</code> and
     * <code>locZ</code>, or use {@link net.minecraft.server.v1_7_R1.Entity#setPosition(double, double, double)}.</li>
     * </ul>
     */
    public abstract void onTick();

    /**
     * Removes all involved entites so that this {@link FlyingBlock} gets removed.
     */
    public void remove() {
        if (entity == null) {
            return;
        }

        entity.die();
        entity = null;
    }

    /**
     * <p>
     * Sets the location of this {@link FlyingBlock} to the specified location. Please keep in mind that this sets the
     * location of the wither skull, not the block itself. The block is located below the skull. (See
     * {@link #getHeightOffset()}) If the world of the speicified location does not match that of the entity,
     * {@link #remove()} is invoked followed by {@link #spawn(Location)} with the specified location. This method needs
     * to be used in {@link #onTick()} instead of <code>getBukkitEntity().teleport(Location)</code>.
     * </p>
     * 
     * <p>
     * If the horse's age and the height offset is the default one, {@link #setBlockLocation(Location)} might be a good
     * utility.
     * </p>
     * 
     * @see #setBlockLocation(Location)
     * @see #getHeightOffset()
     * @param location
     *            The location to move the entity to
     * @throws UnsupportedOperationException
     *             Thrown if the entity is dead
     */
    public void setLocation(Location location) {
        if (entity == null || !entity.isAlive()) {
            throw new UnsupportedOperationException("The entity was not spawned yet!");
        }

        World newWorld = ((CraftWorld) location.getWorld()).getHandle();
        if (newWorld != entity.world) {
            this.remove();
            this.spawn(location);
        } else {
            entity.setLocation(location.getX(), location.getY(), location.getZ(),
                    location.getYaw(), location.getPitch());
        }
    }

    /**
     * Convenience method to make the block's center appear at the specified position. This calls
     * {@link #setLocation(Location)} with the height offset ({@link #getHeightOffset()}) added to the y-coordinate
     * location. If the height offset or the age is different to the default values, this method might not give proper
     * results.
     * 
     * @see #setLocation(Location)
     * @see #getHeightOffset()
     * @see #getHorseAge()
     * @param location
     */
    public void setBlockLocation(Location location) {
        setLocation(location.add(0, heightOffset, 0));
    }

    /**
     * Sets the velocity of this {@link FlyingBlock}. Convenience method analogous to {@link #setLocation(Location)}.
     * 
     * @param velocity
     *            The
     */
    public void setVelocity(Vector velocity) {
        getBukkitEntity().setVelocity(velocity);
    }

    /**
     * Gets the underlying <code>net.minecraft.server</code> entity. May be null if not spawned yet by
     * {@link #spawn(Location)}.
     * 
     * @return The NMS entity
     */
    public EntityWitherSkull getNMSEntity() {
        return entity;
    }

    /**
     * Gets the underlying entity as CraftBukkit entity. May be null if not spawned yet by {@link #spawn(Location)}.
     * 
     * @return The CraftBukkit entity
     */
    public CraftWitherSkull getCraftBukkitEntity() {
        return (CraftWitherSkull) entity.getBukkitEntity();
    }

    /**
     * Gets the underlying entity as Bukkit entity. May be null if not spawned yet by {@link #spawn(Location)}.
     * 
     * @return The Bukkit entity
     */
    public WitherSkull getBukkitEntity() {
        return getCraftBukkitEntity();
    }

    public Material getMaterial() {
        return material;
    }

    public byte getMaterialData() {
        return materialData;
    }

    public int getTrackerUpdateInterval() {
        return trackerUpdateInterval;
    }

    /**
     * Gets the height offset of the skull. The downfacing side of flying block appears at the y-coordinate of the skull
     * minus the return value of {@link #getHeightOffset()}.
     * 
     * @return The height offset
     */
    public double getHeightOffset() {
        return heightOffset;
    }

    public int getHorseAge() {
        return horseAge;
    }
}
