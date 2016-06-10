package gravestone.core.event;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gravestone.core.MobHandler;
import net.minecraft.world.EnumDifficulty;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSEventHandlerNetwork {

    public static boolean GSgameDifficulty;
    
    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            MobHandler.setMobSpawnTime(event.player);
            GSgameDifficulty = event.player.worldObj.difficultySetting.equals(EnumDifficulty.PEACEFUL);
        }
    }

    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            MobHandler.setMobSpawnTime(event.player);
        }
    }
}
