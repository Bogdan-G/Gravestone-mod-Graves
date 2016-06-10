package gravestone.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
//import gravestone.config.GraveStoneConfig;
import gravestone.core.TimeHelper;
//import net.minecraft.client.Minecraft;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSTickEventHandler {

    private static short ticCount = 0;

    @SubscribeEvent
    public void worldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ticCount++;

            if (ticCount >= 500) {
                TimeHelper.updateIsGraveSpawnTime(event.world);
                ticCount = 0;
            }
        }
    }


}
