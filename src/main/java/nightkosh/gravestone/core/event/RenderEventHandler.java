package nightkosh.gravestone.core.event;

import nightkosh.gravestone.config.Config;
import nightkosh.gravestone.core.TimeHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RenderEventHandler {

    public static float fogDensityPerTick = 0;
    private static float fogDensity = 0;

    public static final float MAX_DENSITY = 0.08F;
    public static final float DENSITY_PER_GRAVE = 0.001F;
    public static final float DENSITY_PER_TICK = 0.000005F;

    private static int amountOfFogSources = 0;

    public static void addFog() {
        amountOfFogSources++;
        updateFogDensity();
    }

    public static void resetAmountOfFogSources(World world) {
        amountOfFogSources = 0;
        if (fogDensityPerTick != 0 && !TimeHelper.isFogTime(world)) {
            fogDensityPerTick = 0;
        }
    }

    private static void updateFogDensity() {
        fogDensityPerTick = DENSITY_PER_GRAVE * amountOfFogSources;
        if (fogDensityPerTick > MAX_DENSITY) {
            fogDensityPerTick = MAX_DENSITY;
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void fogEvent(EntityViewRenderEvent.RenderFogEvent event) {
        if (Config.isFogEnabled) {
            if (fogDensity < fogDensityPerTick) {
                fogDensity += DENSITY_PER_TICK;
            } else if (fogDensity > fogDensityPerTick) {
                fogDensity -= DENSITY_PER_TICK;
            }
            if (fogDensity < DENSITY_PER_TICK) {
                fogDensity = 0;
            }

            if (fogDensity > 0) {
                GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                GL11.glFogf(GL11.GL_FOG_DENSITY, fogDensity);
            }
        }
    }
}
