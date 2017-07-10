package gravestone.renderer.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gravestone.block.BlockGSGraveStone;
import gravestone.tileentity.TileEntityGSGraveStone;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

import java.util.*;
import java.util.concurrent.*;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@SideOnly(Side.CLIENT)
public class ItemGSGraveStoneRenderer implements IItemRenderer {

    public static final Map<ItemStack, TileEntityGSGraveStone> teMap = new ConcurrentHashMap(100);
    public static int te_calls = 0;
    
    public ItemGSGraveStoneRenderer() {
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        te_calls++;
        if (te_calls==120000) {
            te_calls=0;
            teMap.clear();
        }
        TileEntityGSGraveStone te = teMap.get(item);
        if (te==null) {
        te = new TileEntityGSGraveStone();
        //fix NPE rendering item in CreativeTab with CCC present(?), this fix for mod compatibility module issue, example - sword of Thaumcraft
        te.blockType = new BlockGSGraveStone();
        te.blockMetadata = item.getItemDamage();

        NBTTagCompound tag = item.stackTagCompound;
        if (tag != null) {
            te.setGraveType(tag.getByte("GraveType"));
            if (tag.hasKey("Sword")) {
                    te.setSword(ItemStack.loadItemStackFromNBT(item.getTagCompound().getCompoundTag("Sword")));
            }
            if (tag.hasKey("Enchanted")) {
                    te.setEnchanted(tag.getBoolean("Enchanted"));
            }
        }
        teMap.put(item, te);
        }

        TileEntityRendererDispatcher.instance.renderTileEntityAt(te, 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
