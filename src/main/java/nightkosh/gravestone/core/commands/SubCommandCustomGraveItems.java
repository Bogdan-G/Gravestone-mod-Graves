package nightkosh.gravestone.core.commands;

import nightkosh.gravestone.core.logger.GSLogger;
import nightkosh.gravestone.tileentity.TileEntityGraveStone;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SubCommandCustomGraveItems implements ISubCommand {

    public static final String COMMAND_NAME = "fill_grave";
    public static final String COMMAND_USAGE = Command.MAIN_COMMAND_NAME + COMMAND_NAME + " <grave x coordinate> <grave y coordinate> <grave z coordinate>  <chest x coordinate> <chest y coordinate> <chest z coordinate>";

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String getCommandUsage() {
        return COMMAND_USAGE;
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        GSLogger.logInfo("Custom grave items command received");

        if (args.length >= 7) {
            try {
                int graveXCoord = Integer.parseInt(args[1]);
                int graveYCoord = Integer.parseInt(args[2]);
                int graveZCoord = Integer.parseInt(args[3]);

                int chestXCoord = Integer.parseInt(args[4]);
                int chestYCoord = Integer.parseInt(args[5]);
                int chestZCoord = Integer.parseInt(args[6]);

                TileEntity graveTE = sender.getEntityWorld().getTileEntity(new BlockPos(graveXCoord, graveYCoord, graveZCoord));
                TileEntity chestTE = sender.getEntityWorld().getTileEntity(new BlockPos(chestXCoord, chestYCoord, chestZCoord));
                if (graveTE != null && chestTE != null) {
                    if (graveTE instanceof TileEntityGraveStone && chestTE instanceof TileEntityChest) {
                        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
                        ItemStack item;
                        for (int slot = 0; slot < ((TileEntityChest) chestTE).getSizeInventory(); slot++) {
                            item = ((TileEntityChest) chestTE).getStackInSlot(slot);
                            if (item != null) {
                                items.add(item.copy());
                            }
                        }
                        ((TileEntityGraveStone) graveTE).getInventory().setAdditionalItems(items.toArray(new ItemStack[items.size()]));
                    } else {
                        sender.addChatMessage(new ChatComponentTranslation("commands.fill_grave.wrong_block").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentTranslation("commands.fill_grave.empty").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                }
            } catch (NumberFormatException e) {
                sender.addChatMessage(new ChatComponentTranslation("commands.coordinate_error").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            }
        } else {
            sender.addChatMessage(new ChatComponentTranslation("commands.not_enough_parameters").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
    }
}
