package gravestone.tileentity;

import gravestone.core.event.GSEventHandlerNetwork;//GSEventsHandler
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class GSSpawner {

    protected TileEntity tileEntity;
    protected int delay;
    protected Entity spawnedMob;
    protected World worldobj;

    public GSSpawner(TileEntity tileEntity, int delay) {
        this.tileEntity = tileEntity;
        this.delay = delay;
        this.worldobj = tileEntity.getWorldObj();
    }

    /**
     * Update entity state.
     */
    public void updateEntity() {
        if (!GSEventHandlerNetwork.GSgameDifficulty/*worldobj.difficultySetting.equals(EnumDifficulty.PEACEFUL) && anyPlayerInRange()*/ && canSpawnMobs(worldobj)) {
            if (worldobj==null) worldobj=tileEntity.getWorldObj();
            if (worldobj.isRemote) {
                clientUpdateLogic();
            } else {
                serverUpdateLogic();
            }
        }
    }

    /**
     * Sets the delay before a new spawn.
     */
    protected void updateDelay() {
        delay = getMinDelay() + worldobj.rand.nextInt(getMaxDelay() - getMinDelay());
    }

    protected void setMinDelay() {
        delay = getMinDelay();
    }

    protected int getNearbyMobsCount() {
        return worldobj.getEntitiesWithinAABB(this.spawnedMob.getClass(), AxisAlignedBB.getBoundingBox(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord,
                tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(1.0D, 4.0D, getSpawnRange() * 2)).size();
    }

    protected boolean anyPlayerInRange() {
        return worldobj.getClosestPlayer(tileEntity.xCoord + 0.5D, tileEntity.yCoord + 0.5D, tileEntity.zCoord + 0.5D, getPlayerRange()) != null;
    }

    abstract protected boolean canSpawnMobs(World world);

    abstract protected int getPlayerRange();

    abstract protected int getSpawnRange();

    abstract protected int getMinDelay();

    abstract protected int getMaxDelay();

    abstract protected Entity getMob();

    abstract protected void clientUpdateLogic();

    abstract protected void serverUpdateLogic();
}
