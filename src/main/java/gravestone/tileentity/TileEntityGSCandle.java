
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
public class TileEntityGSCandle extends TileEntity {

    protected AxisAlignedBB aabb;

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1, zCoord, xCoord, yCoord + 1, zCoord);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

}
