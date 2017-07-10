package gravestone.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gravestone.inventory.GraveInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;
import java.util.Random;
import org.bogdang.modifications.random.*;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * changes by @author Fewizz
 */
public abstract class TileEntityGSGrave extends TileEntity {

    protected AxisAlignedBB aabb;
    protected GraveInventory inventory;
    protected GSGraveStoneDeathText gSDeathText;
    protected boolean isEditable = true;
    protected boolean isEnchanted = false;
    protected byte graveType = 0;
    protected int age = -1;
    public static final Random r = new XSTR(new XSTR().getSeed()*(new GeneratorEntropy().getSeed()));

    public TileEntityGSGrave() {
        gSDeathText = new GSGraveStoneDeathText(this);
    }

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }

    public void setGraveType(byte graveType) {
        this.graveType = graveType;
    }

    public byte getGraveTypeNum() {
        return graveType;
    }

    protected void readType(NBTTagCompound nbtTag) {
        graveType = nbtTag.getByte("GraveType");
    }

    protected void saveType(NBTTagCompound nbtTag) {
        nbtTag.setByte("GraveType", graveType);
    }

    public void setGraveContent(Random random, boolean isPetGrave, boolean allLoot) {
        gSDeathText.setRandomDeathTextAndName(random, graveType, false, true);
        inventory.setRandomGraveContent(inventory, random, isPetGrave, allLoot);
        setRandomAge();
    }

    public GraveInventory getInventory() {
        return inventory;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName() {
        return "container.gravestone";
    }

    public void setItems(List<ItemStack> items) {
        inventory.setItems(items);
    }

    public void setAdditionalItems(ItemStack[] items) {
        inventory.setAdditionalItems(items);
    }

    public void dropAllItems() {
        inventory.dropAllItems();
    }

    public GSGraveStoneDeathText getDeathTextComponent() {
        return gSDeathText;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    protected void setRandomAge() {
        age = 10 + r.nextInt(100);
    }

    public boolean isEditable() {
        return isEditable;
    }

    public boolean isEnchanted() {
        return this.isEnchanted;
    }

    public void setEnchanted(boolean isEnchanted) {
        this.isEnchanted = isEnchanted;
    }

    /**
     * Sets the grave's isEditable flag to the specified parameter.
     */
    @SideOnly(Side.CLIENT)
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTag) {
        super.readFromNBT(nbtTag);

        if (nbtTag.hasKey("Enchanted")) {
            isEnchanted = nbtTag.getBoolean("Enchanted");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTag) {
        super.writeToNBT(nbtTag);

        nbtTag.setBoolean("Enchanted", isEnchanted);
    }
    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param packet The data packet
     */
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
