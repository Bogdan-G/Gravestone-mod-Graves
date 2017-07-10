package gravestone.core.commands;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSCommands {
    public static GSCommands instance;
    private GSCommands(FMLServerStartingEvent event) {
        instance = this;
        
        initCommands(event.getServer());
    }
    
    public static GSCommands getInstance(FMLServerStartingEvent event) {
        if (instance == null) {
            return new GSCommands(event);
        } else {
            return instance;//bug - no register commant if change world (in list world in SSP)
        }
    }
    
    private void initCommands(MinecraftServer server) {
        CommandHandler commandManager = (CommandHandler) server.getCommandManager();
        commandManager.registerCommand(new CommandStructuresGenerator());
        commandManager.registerCommand(new CommandCustomGraveItems());
    }
}
