package nightkosh.gravestone.core;

import nightkosh.gravestone.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSTileEntity {

    public static void registration() {
        GameRegistry.registerTileEntity(TileEntityGraveStone.class, "GraveStoneTE");
    }
}
