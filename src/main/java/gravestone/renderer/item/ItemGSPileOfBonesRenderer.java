package gravestone.renderer.item;

import gravestone.tileentity.TileEntityGSPileOfBones;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import java.util.*;
import java.util.concurrent.*;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemGSPileOfBonesRenderer implements IItemRenderer {

    public static final Map<ItemStack, TileEntityGSPileOfBones> teMap = new ConcurrentHashMap(100);
    public static int te_calls = 0;
    
    public ItemGSPileOfBonesRenderer() {
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
        TileEntityGSPileOfBones te = teMap.get(item);
        if (te==null) {
            te = new TileEntityGSPileOfBones();
            te.blockMetadata = item.getItemDamage();
            teMap.put(item, te);
        }
        TileEntityRendererDispatcher.instance.renderTileEntityAt(te, 0, 0, 0, 0);
    }
}
