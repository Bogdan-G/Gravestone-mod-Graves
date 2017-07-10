package gravestone.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.AxisAlignedBB;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class TileEntityGSSpawner extends TileEntity {

    protected AxisAlignedBB aabb;

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }

    protected GSMobSpawner spawner;

    public TileEntityGSSpawner() {
        spawner = new GSMobSpawner(this);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    @Override
    public void updateEntity() {
        if (spawner!=null) spawner.updateEntity();
    }

    @Override 
    public void markDirty() {/* Do not do the super Function */} 
}
