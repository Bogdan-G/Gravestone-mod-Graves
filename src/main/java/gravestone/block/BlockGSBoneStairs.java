package gravestone.block;

import gravestone.core.GSBlock;
import gravestone.core.GSTabs;
import gravestone.core.Resources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockGSBoneStairs extends BlockStairs {

    public BlockGSBoneStairs() {
        super(GSBlock.boneBlock, 0);
        this.setBlockName("bone_stairs");
        this.setCreativeTab(GSTabs.otherItemsTab);
        this.setBlockTextureName(Resources.BONE_BLOCK);
        this.setHarvestLevel("pickaxe", 0);
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
