package nightkosh.gravestone.core.compatibility;

import nightkosh.gravestone.ModGraveStone;
import nightkosh.gravestone.config.Config;
import mariculture.api.core.MaricultureHandlers;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CompatibilityMariculture implements ICompatibility {

    public static final String MOD_ID = "Mariculture";

    protected CompatibilityMariculture() {
        if (isModLoaded(MOD_ID) && Config.storeMaricultureItems) {
            if (MaricultureHandlers.mirror != null) {
                ModGraveStone.apiGraveGeneration.addPlayerItemsHandler((player, source) -> {
                    List<ItemStack> items = new ArrayList<>();
                    ItemStack[] mirrorItems = MaricultureHandlers.mirror.getMirrorContents(player);
                    if (mirrorItems != null) {
                        for (ItemStack item : mirrorItems) {
                            if (item != null) {
                                items.add(item.copy());
                            }
                        }
                        MaricultureHandlers.mirror.setMirrorContents(player, new ItemStack[4]);
                    }
                    return items;
                });
            }
        }
    }
}
