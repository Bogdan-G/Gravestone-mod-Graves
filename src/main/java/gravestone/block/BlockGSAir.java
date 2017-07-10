package gravestone.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gravestone.ModGraveStone;
import gravestone.block.enums.EnumTrap;
import gravestone.config.GraveStoneConfig;
import gravestone.core.GSPotion;
import gravestone.core.GSTabs;
import gravestone.core.Resources;
import gravestone.core.TimeHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * created file by Bogdan-G
 */
public class BlockGSAir extends BlockAir {

    public BlockGSAir() {
        super();
        this.setBlockName("gs.air");
    }

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 20;
    }

    @Override
    public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target)
    {
        return true;
    }
}
