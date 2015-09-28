package gravestone.models.block.graves;

import gravestone.models.block.memorials.ModelMemorialObelisk;
import org.lwjgl.opengl.GL11;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ModelObeliskGravestone extends ModelMemorialObelisk {

    @Override
    public void renderAll() {
        GL11.glTranslatef(0, 1.12F, 0);
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        super.renderAll();
    }
}
