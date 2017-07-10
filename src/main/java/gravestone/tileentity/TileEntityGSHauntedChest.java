package gravestone.tileentity;

import gravestone.block.enums.EnumHauntedChest;
import gravestone.config.GraveStoneConfig;
import gravestone.core.GSMobSpawn;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.*;
import org.bogdang.modifications.random.*;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class TileEntityGSHauntedChest extends TileEntity {

    private int openTicks = 0;
    private boolean isOpen = false;
    /**
     * The current angle of the lid (between 0 and 1)
     */
    public float lidAngle;
    /**
     * The angle of the lid last tick
     */
    public float prevLidAngle;
    private EnumHauntedChest chestType;
    private static final Random random = new XSTR(new XSTR().getSeed()*(new GeneratorEntropy().getSeed()));
    private int TimeoutSpawnWhenOpen;
    private int TimeSaveValue;

    public TileEntityGSHauntedChest() {
        chestType = random.nextInt(10)==0 ? EnumHauntedChest.SKELETON_CHEST : EnumHauntedChest.BATS_CHEST;
        if (chestType==EnumHauntedChest.BATS_CHEST) {
        TimeoutSpawnWhenOpen = 0;
        TimeSaveValue = 720000;
        } else {
        int day_return_chest = random.nextInt(3)+1;
        if (day_return_chest==1) {TimeoutSpawnWhenOpen = 0;TimeSaveValue = 24000;
        } else if (day_return_chest==2) {TimeoutSpawnWhenOpen = 0;TimeSaveValue = 48000;
        } else {TimeoutSpawnWhenOpen = 0;TimeSaveValue = 72000;}
        }
    }

    protected AxisAlignedBB aabb;

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    @Override
    public void updateEntity() {
        super.updateEntity();
        float f;

        if (openTicks > 0) {
            openTicks--;
        }

        if (this.worldObj.isRemote) {
            this.prevLidAngle = this.lidAngle;
            f = 0.1F;
            double d0;

            if (this.openTicks > 0 && this.lidAngle == 0.0F) {
                double d1 = (double) this.xCoord + 0.5D;
                d0 = (double) this.zCoord + 0.5D;

                this.worldObj.playSoundEffect(d1, (double) this.yCoord + 0.5D, d0, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.openTicks == 0 && this.lidAngle > 0.0F || this.openTicks > 0 && this.lidAngle < 1.0F) {
                float f1 = this.lidAngle;

                if (this.openTicks > 0) {
                    this.lidAngle += f;
                } else {
                    this.lidAngle -= f;
                }

                if (this.lidAngle > 1.0F) {
                    this.lidAngle = 1.0F;
                }

                float f2 = 0.5F;
                if (this.lidAngle < f2 && f1 >= f2) {
                    d0 = (double) this.xCoord + 0.5D;
                    double d2 = (double) this.zCoord + 0.5D;

                    this.worldObj.playSoundEffect(d0, (double) this.yCoord + 0.5D, d2, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }

                if (this.lidAngle < 0.0F) {
                    this.lidAngle = 0.0F;
                }
            }
        } else {
            if (openTicks == 45) {
                spawnMobs(this.worldObj);
            }
        }

        if (openTicks == 0) {
            if (this.isOpen && GraveStoneConfig.replaceHauntedChest) {
                int meta = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
                this.getWorldObj().removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
                this.getWorldObj().setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.chest);
                this.getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, meta, 2);
            }
            this.isOpen = false;
        }
    }

    public void openChest() {
        if (openTicks == 0) {
            this.openTicks = 50;
            this.isOpen = true;
        }
    }

    public EnumHauntedChest getChestType() {
        return chestType;
    }

    public void setChestType(EnumHauntedChest chestType) {
        this.chestType = chestType;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        chestType = EnumHauntedChest.getById(nbt.getByte("ChestType"));
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setByte("ChestType", (byte) chestType.ordinal());
    }

    public void spawnMobs(World world) {
        EnumHauntedChest type = getChestType();
        if (TimeoutSpawnWhenOpen==0) {
        if (type==EnumHauntedChest.SKELETON_CHEST) {
                TimeoutSpawnWhenOpen=TimeSaveValue;
                EntitySkeleton skeleton = GSMobSpawn.getSkeleton(world, (byte) 1);
                skeleton.setLocationAndAngles(this.xCoord + 0.5, this.yCoord, this.zCoord + 0.5, 0.0F, 0.0F);
                world.spawnEntityInWorld(skeleton);
        } else {//type==EnumHauntedChest.BATS_CHEST include in default switch
                TimeoutSpawnWhenOpen=TimeSaveValue;
                EntityBat bat;
                int batsCount = 15;

                for (byte i = 0; i < batsCount; i++) {
                    bat = new EntityBat(world);
                    bat.setLocationAndAngles(this.xCoord + 0.5, this.yCoord + 0.7, this.zCoord + 0.5, 0.0F, 0.0F);

                    world.spawnEntityInWorld(bat);
                }
        }
        }
        TimeoutSpawnWhenOpen--;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override 
    public void markDirty() {/* Do not do the super Function */} 
}
