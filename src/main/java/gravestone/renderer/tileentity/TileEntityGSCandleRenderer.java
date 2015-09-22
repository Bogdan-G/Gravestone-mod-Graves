package gravestone.renderer.tileentity;

import gravestone.core.Resources;
import gravestone.models.block.ModelCandle;
import gravestone.tileentity.TileEntityGSCandle;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@SideOnly(Side.CLIENT)
public class TileEntityGSCandleRenderer extends TileEntitySpecialRenderer {

    private ModelCandle candleModel = new ModelCandle();

    public void renderTileEntityCandleAt(TileEntityGSCandle tileEntity, float x, float y, float z, float par8) {
        this.bindTexture(Resources.CANDLE);

        GL11.glPushMatrix();//TODO tileEntity == null ??
        if (tileEntity == null || tileEntity.getWorld() == null) {
            GL11.glTranslatef(x + 0.5F, y + 3.7F, z + 0.5F);
            GL11.glScalef(2.5F, -2.5F, -2.5F);
        } else {
            GL11.glTranslatef(x + 0.5F, y + 1.5F, z + 0.5F);
            GL11.glScalef(1, -1, -1);
        }
        GL11.glRotatef(0, 0, 1, 0);

        candleModel.renderAll();
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float par8, int par9) {
        this.renderTileEntityCandleAt((TileEntityGSCandle) tileEntity, (float) x, (float) y, (float) z, par8);
    }
}
