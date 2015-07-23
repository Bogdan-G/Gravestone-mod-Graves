package gravestone.structures.catacombs.components;

import gravestone.core.GSBlock;
import gravestone.core.logger.GSLogger;
import gravestone.structures.BoundingBoxHelper;
import gravestone.structures.MobSpawnHelper;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

import java.util.Random;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class WitherHall extends CatacombsBaseComponent {

    public static final int X_LENGTH = 23;
    public static final int HEIGHT = 10;
    public static final int Z_LENGTH = 24;
    private IBlockState netherBrickStairsTopState, netherBrickStairsBotState, netherBrickStairsLeftState, netherBrickStairsRightState;
    private IBlockState netherBrickStairsTopUPState, netherBrickStairsBotUPState, netherBrickStairsLeftUPState, netherBrickStairsRightUPState;

    public WitherHall(EnumFacing facing, int level, Random random, int x, int y, int z) {
        super(0, facing, level);
        xShift = 9;
        boundingBox = BoundingBoxHelper.getCorrectBox(facing, x, y, z, X_LENGTH, HEIGHT, Z_LENGTH, xShift);
        goForward = false;
    }

    /**
     * Build component
     */
    @Override
    public boolean addComponentParts(World world, Random random) {
        Vec3i center = boundingBox.func_180717_f();
        GSLogger.logInfo("Generate Wither hall at " + center.getX() + "x" + center.getY() + "x" + center.getZ());

        IBlockState netherBrickStairsState = Blocks.nether_brick_stairs.getDefaultState();
        netherBrickStairsTopState = netherBrickStairsState.withProperty(BlockStairs.FACING, this.coordBaseMode.getOpposite());
        netherBrickStairsBotState = netherBrickStairsState.withProperty(BlockStairs.FACING, this.coordBaseMode);
        netherBrickStairsLeftState = netherBrickStairsState.withProperty(BlockStairs.FACING, this.getLeftDirection(this.coordBaseMode));
        netherBrickStairsRightState = netherBrickStairsState.withProperty(BlockStairs.FACING, this.getRightDirection(this.coordBaseMode));


        netherBrickStairsTopUPState = netherBrickStairsTopState.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
        netherBrickStairsBotUPState = netherBrickStairsBotState.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
        netherBrickStairsLeftUPState = netherBrickStairsLeftState.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
        netherBrickStairsRightUPState = netherBrickStairsRightState.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
        this.fillWithAir(world, boundingBox, 1, 1, 2, 21, 9, 22);

        // nether floor
        this.fillWithBlocks(world, boundingBox, 10, 0, 0, 12, 0, 4, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 5, 0, 5, 17, 0, 16, Blocks.nether_brick.getDefaultState(), false);

        // nether ceiling
        this.fillWithBlocks(world, boundingBox, 1, 10, 1, 21, 10, 22, Blocks.nether_brick.getDefaultState(), false);

        // nether walls
        this.fillWithBlocks(world, boundingBox, 0, 0, 1, 0, 10, 23, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 22, 0, 1, 22, 10, 23, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 1, 0, 1, 21, 10, 1, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 1, 0, 23, 21, 10, 23, Blocks.nether_brick.getDefaultState(), false);

        // entrance
        this.fillWithAir(world, boundingBox, 10, 1, 0, 12, 3, 1);
        this.fillWithBlocks(world, boundingBox, 9, 0, 0, 9, 4, 0, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 13, 0, 0, 13, 4, 0, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 10, 4, 0, 12, 4, 0, Blocks.nether_brick.getDefaultState(), false);

        // lava floor
        this.fillWithBlocks(world, boundingBox, 1, 0, 2, 9, 0, 4, Blocks.lava.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 1, 0, 5, 4, 0, 19, Blocks.lava.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 1, 0, 20, 8, 0, 22, Blocks.lava.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 13, 0, 2, 21, 0, 4, Blocks.lava.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 18, 0, 5, 21, 0, 19, Blocks.lava.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 14, 0, 20, 21, 0, 22, Blocks.lava.getDefaultState(), false);

        // lava walls
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 0, 6, 3, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 0, 6, 9, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 0, 6, 15, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 0, 6, 21, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 22, 6, 3, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 22, 6, 9, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 22, 6, 15, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 22, 6, 21, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 4, 6, 23, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 18, 6, 23, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 4, 6, 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 18, 6, 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 9, 6, 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.flowing_lava.getDefaultState(), 13, 6, 1, boundingBox);

        // fire
        buildFire(world, 6, 1, 6);
        buildFire(world, 6, 1, 12);
        buildFire(world, 6, 4, 18);
        buildFire(world, 16, 1, 6);
        buildFire(world, 16, 1, 12);
        buildFire(world, 16, 4, 18);

        // columns
        buildColumnLeft(world, 6);
        buildColumnLeft(world, 12);
        buildColumnLeft(world, 18);
        buildColumnRight(world, 6);
        buildColumnRight(world, 12);
        buildColumnRight(world, 18);

        // ceiling light
        buildLight(world, 6, 6);
        buildLight(world, 6, 12);
        buildLight(world, 6, 18);
        buildLight(world, 16, 6);
        buildLight(world, 16, 12);
        buildLight(world, 16, 18);

        // stairs
        this.fillWithBlocks(world, boundingBox, 9, 1, 12, 13, 1, 14, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 9, 2, 13, 13, 2, 14, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 9, 3, 14, 13, 3, 14, Blocks.nether_brick.getDefaultState(), false);

        this.fillWithBlocks(world, boundingBox, 10, 1, 12, 12, 1, 14, netherBrickStairsBotState, false);
        this.fillWithBlocks(world, boundingBox, 10, 2, 13, 12, 2, 14, netherBrickStairsBotState, false);
        this.fillWithBlocks(world, boundingBox, 10, 3, 14, 12, 3, 14, netherBrickStairsBotState, false);

        // wither place
        this.fillWithBlocks(world, boundingBox, 8, 0, 15, 14, 3, 21, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 5, 0, 17, 7, 3, 19, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 15, 0, 17, 17, 3, 19, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 7, 0, 20, 7, 3, 20, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 15, 0, 20, 15, 3, 20, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 9, 0, 22, 13, 2, 22, Blocks.nether_brick.getDefaultState(), false);

        this.placeBlockAtCurrentPosition(world, Blocks.netherrack.getDefaultState(), 9, 3, 15, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.fire.getDefaultState(), 9, 4, 15, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.netherrack.getDefaultState(), 13, 3, 15, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.fire.getDefaultState(), 13, 4, 15, boundingBox);

        // portal
        this.fillWithBlocks(world, boundingBox, 9, 3, 22, 13, 3, 22, Blocks.obsidian.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 9, 7, 22, 13, 7, 22, Blocks.obsidian.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 9, 4, 22, 9, 6, 22, Blocks.obsidian.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 13, 4, 22, 13, 6, 22, Blocks.obsidian.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 10, 4, 22, 12, 6, 22, Blocks.portal.getDefaultState(), false);

        // spawner
        this.placeBlockAtCurrentPosition(world, GSBlock.spawner.getDefaultState(), 11, 4, 18, boundingBox);

        // treasure
        this.fillWithBlocks(world, boundingBox, 10, 3, 17, 12, 3, 19, Blocks.diamond_block.getDefaultState(), false);
        this.placeBlockAtCurrentPosition(world, Blocks.emerald_block.getDefaultState(), 11, 3, 18, boundingBox);

        // spawn bats
        MobSpawnHelper.spawnBats(world, random, boundingBox);

        return true;
    }

    private void buildFire(World world, int x, int y, int z) {
        // fire
        this.placeBlockAtCurrentPosition(world, Blocks.netherrack.getDefaultState(), x, y, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, Blocks.fire.getDefaultState(), x, y + 1, z, boundingBox);

        // fire stairs
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotState, x - 1, y, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotState, x, y, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotState, x + 1, y, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsLeftState, x - 1, y, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsRightState, x + 1, y, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopState, x - 1, y, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopState, x, y, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopState, x + 1, y, z + 1, boundingBox);
    }

    private void buildLight(World world, int x, int z) {
        // glowStone
        this.placeBlockAtCurrentPosition(world, Blocks.glowstone.getDefaultState(), x, 9, z, boundingBox);

        // fire stairs
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotUPState, x - 1, 9, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotUPState, x, 9, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotUPState, x + 1, 9, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsLeftUPState, x - 1, 9, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsRightUPState, x + 1, 9, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopUPState, x - 1, 9, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopUPState, x, 9, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopUPState, x + 1, 9, z + 1, boundingBox);
    }

    private void buildColumnLeft(World world, int z) {
        this.fillWithBlocks(world, boundingBox, 1, 0, z - 1, 2, 0, z + 1, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 1, 1, z, 1, 9, z, Blocks.nether_brick.getDefaultState(), false);

        // bot
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotState, 1, 1, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotState, 2, 1, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsRightState, 2, 1, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopState, 1, 1, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopState, 2, 1, z + 1, boundingBox);

        // top
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotUPState, 1, 9, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotUPState, 2, 9, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsRightUPState, 2, 9, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopUPState, 1, 9, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopUPState, 2, 9, z + 1, boundingBox);
    }

    private void buildColumnRight(World world, int z) {
        this.fillWithBlocks(world, boundingBox, 20, 0, z - 1, 21, 0, z + 1, Blocks.nether_brick.getDefaultState(), false);
        this.fillWithBlocks(world, boundingBox, 21, 1, z, 21, 9, z, Blocks.nether_brick.getDefaultState(), false);

        // bot
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotState, 20, 1, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotState, 21, 1, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsLeftState, 20, 1, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopState, 20, 1, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopState, 21, 1, z + 1, boundingBox);

        // top
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotUPState, 20, 9, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsBotUPState, 21, 9, z - 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsLeftUPState, 20, 9, z, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopUPState, 20, 9, z + 1, boundingBox);
        this.placeBlockAtCurrentPosition(world, netherBrickStairsTopUPState, 21, 9, z + 1, boundingBox);
    }
}
