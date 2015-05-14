package gravestone.core.compatibility;

import java.util.LinkedList;
import java.util.List;

import gravestone.config.GSConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSCompatibilityTheCampingMod {

    protected static boolean isInstalled = false;

    private GSCompatibilityTheCampingMod() {
    }

    public static void addItems(List<ItemStack> items, EntityPlayer player) {
        if (isInstalled() && GSConfig.storeTheCampingModItems) {
            items.addAll(getItems(player));
        }
    }

    private static List<ItemStack> getItems(EntityPlayer player) {
        List<ItemStack> items = new LinkedList<ItemStack>();
        NBTTagCompound tag = player.getEntityData().getCompoundTag("campInv");
        NBTTagList inventory = tag.getTagList("Items", 10);
        for (int i = 0; i < inventory.tagCount(); ++i) {
            NBTTagCompound Slots = inventory.getCompoundTagAt(i);
            Slots.getByte("Slot");
            items.add(ItemStack.loadItemStackFromNBT(Slots).copy());
        }
        player.getEntityData().setTag("campInv", new NBTTagCompound());
        return items;
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
}
