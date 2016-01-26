package nightkosh.gravestone.core;

import nightkosh.gravestone.gui.GSGraveInventoryGui;
import nightkosh.gravestone.gui.container.GraveContainer;
import nightkosh.gravestone.tileentity.TileEntityGSGraveStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSGuiHandler implements IGuiHandler {

    public static final int GRAVE_INVENTORY_GUI_ID = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity;
        switch (id) {
            case GRAVE_INVENTORY_GUI_ID:
                tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntity instanceof TileEntityGSGraveStone) {
                    return new GraveContainer(player.inventory, (TileEntityGSGraveStone) tileEntity);
                }
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity;
        switch (id) {
            case GRAVE_INVENTORY_GUI_ID:
                tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntity instanceof TileEntityGSGraveStone) {
                    return new GSGraveInventoryGui(player.inventory, (TileEntityGSGraveStone) tileEntity);
                }
                break;
        }
        return null;
    }
}
