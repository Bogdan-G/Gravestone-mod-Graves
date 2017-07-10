package gravestone.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
//import gravestone.config.GraveStoneConfig;
import gravestone.core.TimeHelper;
//import net.minecraft.client.Minecraft;

import java.util.*;

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
                //Fog/spawns rule random changes
                if (TimeHelper.random.nextInt(100)==0) {
                    Random rnd = TimeHelper.random;
                    int r = rnd.nextInt(100)+1;
                    int value_shift = r<=5?5000:r<=10?2500:r<=20?2000:r<=30?1500:r<=40?1000:r<=50?500:r<=75?250:r<=85?100:r<=95?50:r<=96?25:r<=97?15:r<=98?10:r<=99?5:1;
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.PRE_NIGHT +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.NIGHT +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.PRE_MORNING +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.FOG_START_TIME +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.FOG_END_TIME +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.SUN_SET +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.SUN_RISING +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.GRAVE_SPAWN_START_TIME +=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.GRAVE_SPAWN_END_TIME +=rnd.nextInt(value_shift);
                    
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.PRE_NIGHT -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.NIGHT -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.PRE_MORNING -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.FOG_START_TIME -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.FOG_END_TIME -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.SUN_SET -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.SUN_RISING -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.GRAVE_SPAWN_START_TIME -=rnd.nextInt(value_shift);
                    if (rnd.nextInt(100)>rnd.nextInt(100)) TimeHelper.GRAVE_SPAWN_END_TIME -=rnd.nextInt(value_shift);
                    
                    if (TimeHelper.PRE_NIGHT<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.PRE_NIGHT>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.NIGHT<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.NIGHT>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.PRE_MORNING<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.PRE_MORNING>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.FOG_START_TIME<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.FOG_START_TIME>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.FOG_END_TIME<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.FOG_END_TIME>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.SUN_SET<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.SUN_SET>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.SUN_RISING<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.SUN_RISING>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.GRAVE_SPAWN_START_TIME<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.GRAVE_SPAWN_START_TIME>24000) TimeHelper.PRE_NIGHT = 0;
                    if (TimeHelper.GRAVE_SPAWN_END_TIME<0) TimeHelper.PRE_NIGHT = 24000;
                    else if (TimeHelper.GRAVE_SPAWN_END_TIME>24000) TimeHelper.PRE_NIGHT = 0;
                }
            }
        }
    }


}
