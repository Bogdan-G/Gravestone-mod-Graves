package gravestone.tileentity;

import gravestone.block.BlockGSSpawner;
import gravestone.block.enums.EnumSpawner;
import gravestone.core.GSMobSpawn;
import gravestone.core.logger.GSLogger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSMobSpawner extends GSSpawner {

    private static final int BASE_DELAY = 90;//60
    private static final int MIN_DELAY = 600;
    private static final int MAX_DELAY = 1200;//800
    private static final int BOSS_PLAYER_RANGE = 8;
    private static final int MOB_PLAYER_RANGE = 16;
    //private static final int SPAWN_EFFECTS_DELAY = 20;
    private static final float MAX_LIGHT_VALUE = 0.51F;//0.46F
    private EnumSpawner spawnerType = null;
    private int mobs_spawn_count = 0;

    public GSMobSpawner(TileEntity tileEntity) {
        super(tileEntity, BASE_DELAY);
    }

    @Override
    protected void clientUpdateLogic() {
//        delay--;
//        if (delay <= SPAWN_EFFECTS_DELAY) {
//            double x = tileEntity.xCoord + tileEntity.worldObj.rand.nextFloat();
//            double y = tileEntity.yCoord + tileEntity.worldObj.rand.nextFloat();
//            double z = tileEntity.zCoord + tileEntity.worldObj.rand.nextFloat();
//            tileEntity.worldObj.spawnParticle("largesmoke", x, y, z, 0.0D, 0.0D, 0.0D);
//            tileEntity.worldObj.spawnParticle("portal", x, y, z, 0.0D, 0.0D, 0.0D);
//            tileEntity.worldObj.spawnParticle("spell", x, y, z, 0.0D, 0.0D, 0.0D);
//            tileEntity.worldObj.spawnParticle("witchMagic", x, y, z, 0.0D, 0.0D, 0.0D);
//            tileEntity.worldObj.spawnParticle("lava", x, y, z, 0.0D, 0.0D, 0.0D);
//            tileEntity.worldObj.spawnParticle("flame", x, y, z, 0.0D, 0.0D, 0.0D);
//        }
    }

    @Override
    protected void serverUpdateLogic() {
        delay--;
        if (delay <= 0) {
            double x = tileEntity.xCoord + 0.5;
            double y = tileEntity.yCoord;
            double z = tileEntity.zCoord + 0.5;
            //World worldobj = tileEntity.getWorldObj();
            if (isBossSpawner() && anyPlayerInRange2()) {
                EntityLiving entity = (EntityLiving) getMob();
                if (entity != null) {
                    entity.setLocationAndAngles(x, y, z, worldobj.rand.nextFloat() * 360, 0);
                    worldobj.removeTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
                    worldobj.setBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, Blocks.air);
                    worldobj.spawnEntityInWorld(entity);
                } else GSLogger.logError("Spanwer mob get 'null' as mob!!!");
            } else if (worldobj.getLightBrightness(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord) <= MAX_LIGHT_VALUE) {
                if (mobs_spawn_count<=4) {
                EntityLiving entity = (EntityLiving) getMob();
                if (entity != null) {
                    entity.setLocationAndAngles(x, y, z, worldobj.rand.nextFloat() * 360, 0);
                    worldobj.spawnEntityInWorld(entity);
                    mobs_spawn_count++;
                } else GSLogger.logError("Spanwer mob get 'null' as mob!!!");
                } else {
                    if (worldobj.getEntitiesWithinAABB(((EntityLiving) EntityList.createEntityByName("Skeleton", worldobj)).getClass(), AxisAlignedBB.getBoundingBox(tileEntity.xCoord - 1, tileEntity.yCoord - 1, tileEntity.zCoord - 1, tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(8.0D, 8.0D, 8.0D)).size()<2 && worldobj.getEntitiesWithinAABB(((EntityLiving) EntityList.createEntityByName("Zombie", worldobj)).getClass(), AxisAlignedBB.getBoundingBox(tileEntity.xCoord - 1, tileEntity.yCoord - 1, tileEntity.zCoord - 1, tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(8.0D, 8.0D, 8.0D)).size()<2 && worldobj.getEntitiesWithinAABB(((EntityLiving) EntityList.createEntityByName("GraveStone.GSSkeletonDog", worldobj)).getClass(), AxisAlignedBB.getBoundingBox(tileEntity.xCoord - 1, tileEntity.yCoord - 1, tileEntity.zCoord - 1, tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(8.0D, 8.0D, 8.0D)).size()<2 && worldobj.getEntitiesWithinAABB(((EntityLiving) EntityList.createEntityByName("GraveStone.GSSkeletonCat", worldobj)).getClass(), AxisAlignedBB.getBoundingBox(tileEntity.xCoord - 1, tileEntity.yCoord - 1, tileEntity.zCoord - 1, tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(8.0D, 8.0D, 8.0D)).size()<2 && worldobj.getEntitiesWithinAABB(((EntityLiving) EntityList.createEntityByName("GraveStone.GSZombieDog", worldobj)).getClass(), AxisAlignedBB.getBoundingBox(tileEntity.xCoord - 1, tileEntity.yCoord - 1, tileEntity.zCoord - 1, tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(8.0D, 8.0D, 8.0D)).size()<2 && worldobj.getEntitiesWithinAABB(((EntityLiving) EntityList.createEntityByName("GraveStone.GSZombieCat", worldobj)).getClass(), AxisAlignedBB.getBoundingBox(tileEntity.xCoord - 1, tileEntity.yCoord - 1, tileEntity.zCoord - 1, tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(8.0D, 8.0D, 8.0D)).size()<2) {mobs_spawn_count=0;}
                }
            }
            this.updateDelay();
        }
    }

    private boolean isBossSpawner() {
        return BlockGSSpawner.BOSS_SPAWNERS.contains((byte) getSpawnerType().ordinal());
    }

    private EnumSpawner getSpawnerType() {
        if (spawnerType == null) {
            if (tileEntity.getWorldObj() == null) {
                GSLogger.logError("Spawner te worldobj is null !!!!!");
                return EnumSpawner.ZOMBIE_SPAWNER;
            } else {
                byte meta = (byte) tileEntity.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
                spawnerType = EnumSpawner.getById(meta);
                return spawnerType;
            }
        }
        return spawnerType;
    }
    
    protected boolean anyPlayerInRange2() {
        return worldobj.getClosestPlayer(tileEntity.xCoord + 0.5D, tileEntity.yCoord + 0.5D, tileEntity.zCoord + 0.5D, BOSS_PLAYER_RANGE) != null;
    }

    @Override
    protected boolean canSpawnMobs(World world) {
        return true;
    }

    @Override
    protected int getPlayerRange() {
        return isBossSpawner() ? BOSS_PLAYER_RANGE : MOB_PLAYER_RANGE;
    }

    @Override
    protected Entity getMob() {
        return GSMobSpawn.getMobEntityForSpawner(this.tileEntity.getWorldObj(), getSpawnerType(),
                this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord);
    }

    @Override
    protected int getSpawnRange() {
        return 0;
    }

    @Override
    protected int getMinDelay() {
        return MIN_DELAY;
    }

    @Override
    protected int getMaxDelay() {
        return MAX_DELAY;
    }
}
