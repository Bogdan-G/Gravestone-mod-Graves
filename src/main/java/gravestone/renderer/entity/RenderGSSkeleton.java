package gravestone.renderer.entity;

import gravestone.models.entity.ModelGSSkeleton;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

import java.util.Iterator;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RenderGSSkeleton extends RenderSkeleton {
    public RenderGSSkeleton(RenderManager p_i46143_1_) {
        super(p_i46143_1_);

        ModelGSSkeleton skeletonModel = new ModelGSSkeleton();
        this.mainModel = skeletonModel;
        this.modelBipedMain = skeletonModel;
        Iterator it = this.layerRenderers.iterator();
        while (it.hasNext()) {
            Object layer = it.next();
            if (layer instanceof LayerCustomHead) {
                it.remove();
            }
        }
        this.addLayer(new LayerCustomHead(skeletonModel.bipedHead));
    }
}
