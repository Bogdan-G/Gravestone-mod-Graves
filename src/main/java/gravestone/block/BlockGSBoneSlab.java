package gravestone.block;

import gravestone.core.GSTabs;
import gravestone.core.Resources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockGSBoneSlab extends BlockSlab {

    public BlockGSBoneSlab() {
        super(false, Material.rock);
        this.setStepSound(Block.soundTypeStone);
        this.setBlockName("bone_slab");
        this.setHardness(2F);
        this.setResistance(2F);
        this.setCreativeTab(GSTabs.otherItemsTab);
        this.setBlockTextureName(Resources.BONE_BLOCK);
        this.setHarvestLevel("pickaxe", 0);
    }

    /**
     * Returns the slab block name with step type.
     */
    @Override
    public String func_150002_b(int par1) {
        return getUnlocalizedName();
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
