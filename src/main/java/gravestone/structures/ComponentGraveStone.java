package gravestone.structures;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.Random;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ComponentGraveStone extends StructureComponent {

    protected ComponentGraveStone(int direction) {
        super(direction);
        coordBaseMode = direction;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs,
     * Mob Spawners, it closes Mineshafts at the end, it adds Fences... not
     * used!!!
     */
    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
        return true;
    }

    /**
     * Build component
     */
    public boolean addComponentParts(World world, Random random) {
        return true;
    }

    /**
     * Place block
     */
    @Override
    public void placeBlockAtCurrentPosition(World world, Block block, int blockMeta, int x, int y, int z, StructureBoundingBox boundingBox) {
        super.placeBlockAtCurrentPosition(world, block, blockMeta, x, y, z, boundingBox);
    }

    /**
     * Return world X coord
     *
     * @param x Bounding box X coord
     * @param z Bounding box Z coord
     */
    @Override
    public int getXWithOffset(int x, int z) {
        return super.getXWithOffset(x, z);
    }

    /**
     * Return world y coord
     *
     * @param y Bounding box Y coord
     */
    @Override
    public int getYWithOffset(int y) {
        return super.getYWithOffset(y);
    }

    /**
     * Return world Z coord
     *
     * @param x Bounding box X coord
     * @param z Bounding box Z coord
     */
    @Override
    public int getZWithOffset(int x, int z) {
        return super.getZWithOffset(x, z);
    }

    /**
     * Used to generate chests with items in it. ex: Temple Chests, Village
     * Blacksmith Chests, Mineshaft Chests.
     */
    @Override
    public boolean generateStructureChestContents(World world, StructureBoundingBox boundingBox, Random random, int x, int y, int z, WeightedRandomChestContent[] chestContent, int par8) {
        return super.generateStructureChestContents(world, boundingBox, random, x, y, z, chestContent, par8);
    }

    @Override
    protected void func_143012_a(NBTTagCompound nbttagcompound) {
    }

    @Override
    protected void func_143011_b(NBTTagCompound nbttagcompound) {
    }

    //for fake air block
    @Override
    protected void fillWithAir(World p_74878_1_, StructureBoundingBox p_74878_2_, int p_74878_3_, int p_74878_4_, int p_74878_5_, int p_74878_6_, int p_74878_7_, int p_74878_8_)
    {
        for (int k1 = p_74878_4_; k1 <= p_74878_7_; ++k1)
        {
            for (int l1 = p_74878_3_; l1 <= p_74878_6_; ++l1)
            {
                for (int i2 = p_74878_5_; i2 <= p_74878_8_; ++i2)
                {
                    this.placeBlockAtCurrentPosition(p_74878_1_, gravestone.core.GSBlock.air, 0, l1, k1, i2, p_74878_2_);
                }
            }
        }
    }
}
