package gravestone.block;

import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gravestone.ModGraveStone;
import gravestone.block.enums.EnumHangedMobs;
import gravestone.block.enums.EnumMemorials;
import gravestone.config.GraveStoneConfig;
import gravestone.core.GSBlock;
import gravestone.core.GSTabs;
import gravestone.item.ItemGSCorpse;
import gravestone.item.corpse.VillagerCorpseHelper;
import gravestone.item.enums.EnumCorpse;
import gravestone.particle.EntityBigFlameFX;
import gravestone.tileentity.TileEntityGSMemorial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.*;
import org.bogdang.modifications.random.*;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockGSMemorial extends BlockContainer {

    public static final byte[] TAB_MEMORIALS = {
            (byte) EnumMemorials.WOODEN_CROSS.ordinal(),
            (byte) EnumMemorials.SANDSTONE_CROSS.ordinal(),
            (byte) EnumMemorials.STONE_CROSS.ordinal(),
            (byte) EnumMemorials.MOSSY_CROSS.ordinal(),
            (byte) EnumMemorials.IRON_CROSS.ordinal(),
            (byte) EnumMemorials.GOLDEN_CROSS.ordinal(),
            (byte) EnumMemorials.DIAMOND_CROSS.ordinal(),
            (byte) EnumMemorials.EMERALD_CROSS.ordinal(),
            (byte) EnumMemorials.LAPIS_CROSS.ordinal(),
            (byte) EnumMemorials.REDSTONE_CROSS.ordinal(),
            (byte) EnumMemorials.OBSIDIAN_CROSS.ordinal(),
            (byte) EnumMemorials.QUARTZ_CROSS.ordinal(),
            (byte) EnumMemorials.ICE_CROSS.ordinal(),
            // obelisks
//            (byte) EnumMemorials.WOODEN_OBELISK.ordinal(),
//            (byte) EnumMemorials.SANDSTONE_OBELISK.ordinal(),
//            (byte) EnumMemorials.STONE_OBELISK.ordinal(),
//            (byte) EnumMemorials.MOSSY_OBELISK.ordinal(),
//            (byte) EnumMemorials.IRON_OBELISK.ordinal(),
//            (byte) EnumMemorials.GOLDEN_OBELISK.ordinal(),
//            (byte) EnumMemorials.DIAMOND_OBELISK.ordinal(),
//            (byte) EnumMemorials.EMERALD_OBELISK.ordinal(),
//            (byte) EnumMemorials.LAPIS_OBELISK.ordinal(),
//            (byte) EnumMemorials.REDSTONE_OBELISK.ordinal(),
//            (byte) EnumMemorials.OBSIDIAN_OBELISK.ordinal(),
            (byte) EnumMemorials.QUARTZ_OBELISK.ordinal(),
//            (byte) EnumMemorials.ICE_OBELISK.ordinal(),
            // ANGEL memorials
            (byte) EnumMemorials.WOODEN_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.SANDSTONE_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.STONE_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.MOSSY_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.IRON_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.GOLDEN_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.DIAMOND_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.EMERALD_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.LAPIS_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.REDSTONE_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.OBSIDIAN_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.QUARTZ_STEVE_STATUE.ordinal(),
            (byte) EnumMemorials.ICE_STEVE_STATUE.ordinal(),
            // villager memorials
            (byte) EnumMemorials.WOODEN_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.SANDSTONE_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.STONE_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.MOSSY_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.IRON_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.GOLDEN_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.DIAMOND_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.EMERALD_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.LAPIS_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.REDSTONE_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.OBSIDIAN_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.QUARTZ_VILLAGER_STATUE.ordinal(),
            (byte) EnumMemorials.ICE_VILLAGER_STATUE.ordinal(),
            // angel memorials
            (byte) EnumMemorials.WOODEN_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.SANDSTONE_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.STONE_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.MOSSY_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.IRON_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.GOLDEN_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.DIAMOND_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.EMERALD_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.LAPIS_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.REDSTONE_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.OBSIDIAN_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.QUARTZ_ANGEL_STATUE.ordinal(),
            (byte) EnumMemorials.ICE_ANGEL_STATUE.ordinal(),
            // dog memorials
            (byte) EnumMemorials.WOODEN_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.SANDSTONE_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.STONE_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.MOSSY_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.IRON_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.GOLDEN_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.DIAMOND_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.EMERALD_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.LAPIS_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.REDSTONE_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.OBSIDIAN_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.QUARTZ_DOG_STATUE.ordinal(),
            (byte) EnumMemorials.ICE_DOG_STATUE.ordinal(),
            // cat memorials
            (byte) EnumMemorials.WOODEN_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.SANDSTONE_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.STONE_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.MOSSY_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.IRON_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.GOLDEN_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.DIAMOND_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.EMERALD_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.LAPIS_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.REDSTONE_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.OBSIDIAN_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.QUARTZ_CAT_STATUE.ordinal(),
            (byte) EnumMemorials.ICE_CAT_STATUE.ordinal(),
            // creeper memorials
            (byte) EnumMemorials.WOODEN_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.SANDSTONE_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.STONE_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.MOSSY_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.IRON_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.GOLDEN_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.DIAMOND_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.EMERALD_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.LAPIS_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.REDSTONE_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.OBSIDIAN_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.QUARTZ_CREEPER_STATUE.ordinal(),
            (byte) EnumMemorials.ICE_CREEPER_STATUE.ordinal()//,
            // gibbets
//            (byte) EnumMemorials.GIBBET.ordinal(),
//            // stocks
//            (byte) EnumMemorials.STOCKS.ordinal()
    };
    public static final EnumMemorials[] WOODEN_GENERATED_MEMORIALS = {
            EnumMemorials.WOODEN_CROSS,
            EnumMemorials.QUARTZ_OBELISK,
            EnumMemorials.WOODEN_STEVE_STATUE,
            EnumMemorials.WOODEN_VILLAGER_STATUE,
            EnumMemorials.WOODEN_ANGEL_STATUE,
            EnumMemorials.WOODEN_DOG_STATUE,
            EnumMemorials.WOODEN_CAT_STATUE
    };
    public static final EnumMemorials[] SANDSTONE_GENERATED_MEMORIALS = {
            EnumMemorials.SANDSTONE_CROSS,
            EnumMemorials.QUARTZ_OBELISK,
            EnumMemorials.SANDSTONE_STEVE_STATUE,
            EnumMemorials.SANDSTONE_VILLAGER_STATUE,
            EnumMemorials.SANDSTONE_ANGEL_STATUE,
            EnumMemorials.SANDSTONE_DOG_STATUE,
            EnumMemorials.SANDSTONE_CAT_STATUE
    };
    public static final EnumMemorials[] STONE_GENERATED_MEMORIALS = {
            EnumMemorials.STONE_CROSS,
            EnumMemorials.QUARTZ_OBELISK,
            EnumMemorials.STONE_STEVE_STATUE,
            EnumMemorials.STONE_VILLAGER_STATUE,
            EnumMemorials.STONE_ANGEL_STATUE,
            EnumMemorials.STONE_DOG_STATUE,
            EnumMemorials.STONE_CAT_STATUE
    };
    public static final EnumMemorials[] MOSSY_GENERATED_MEMORIALS = {
            EnumMemorials.MOSSY_CROSS,
            EnumMemorials.QUARTZ_OBELISK,
            EnumMemorials.MOSSY_STEVE_STATUE,
            EnumMemorials.MOSSY_VILLAGER_STATUE,
            EnumMemorials.MOSSY_ANGEL_STATUE,
            EnumMemorials.MOSSY_DOG_STATUE,
            EnumMemorials.MOSSY_CAT_STATUE
    };
    public static final EnumMemorials[] QUARTZ_GENERATED_MEMORIALS = {
            EnumMemorials.QUARTZ_CROSS,
            EnumMemorials.QUARTZ_OBELISK,
            EnumMemorials.QUARTZ_STEVE_STATUE,
            EnumMemorials.QUARTZ_VILLAGER_STATUE,
            EnumMemorials.QUARTZ_ANGEL_STATUE,
            EnumMemorials.QUARTZ_DOG_STATUE,
            EnumMemorials.QUARTZ_CAT_STATUE
    };
    public static final EnumMemorials[] ICE_GENERATED_MEMORIALS = {
            EnumMemorials.ICE_CROSS,
            EnumMemorials.QUARTZ_OBELISK,
            EnumMemorials.ICE_STEVE_STATUE,
            EnumMemorials.ICE_VILLAGER_STATUE,
            EnumMemorials.ICE_ANGEL_STATUE,
            EnumMemorials.ICE_DOG_STATUE,
            EnumMemorials.ICE_CAT_STATUE
    };
    public static final EnumMemorials[] WOODEN_DOG_MEMORIALS = {EnumMemorials.WOODEN_DOG_STATUE};
    public static final EnumMemorials[] SANDSTONE_DOG_MEMORIALS = {EnumMemorials.SANDSTONE_DOG_STATUE};
    public static final EnumMemorials[] STONE_DOG_MEMORIALS = {EnumMemorials.STONE_DOG_STATUE};
    public static final EnumMemorials[] MOSSY_DOG_MEMORIALS = {EnumMemorials.MOSSY_DOG_STATUE};
    public static final EnumMemorials[] QUARTZ_DOG_MEMORIALS = {EnumMemorials.QUARTZ_DOG_STATUE};
    public static final EnumMemorials[] ICE_DOG_MEMORIALS = {EnumMemorials.ICE_DOG_STATUE};

    public static final EnumMemorials[] WOODEN_CAT_MEMORIALS = {EnumMemorials.WOODEN_CAT_STATUE};
    public static final EnumMemorials[] SANDSTONE_CAT_MEMORIALS = {EnumMemorials.SANDSTONE_CAT_STATUE};
    public static final EnumMemorials[] STONE_CAT_MEMORIALS = {EnumMemorials.STONE_CAT_STATUE};
    public static final EnumMemorials[] MOSSY_CAT_MEMORIALS = {EnumMemorials.MOSSY_CAT_STATUE};
    public static final EnumMemorials[] QUARTZ_CAT_MEMORIALS = {EnumMemorials.QUARTZ_CAT_STATUE};
    public static final EnumMemorials[] ICE_CAT_MEMORIALS = {EnumMemorials.ICE_CAT_STATUE};

    public static final EnumMemorials[] WOODEN_CREEPER_MEMORIALS = {EnumMemorials.WOODEN_CREEPER_STATUE};
    public static final EnumMemorials[] SANDSTONE_CREEPER_MEMORIALS = {EnumMemorials.SANDSTONE_CREEPER_STATUE};
    public static final EnumMemorials[] STONE_CREEPER_MEMORIALS = {EnumMemorials.STONE_CREEPER_STATUE};
    public static final EnumMemorials[] MOSSY_CREEPER_MEMORIALS = {EnumMemorials.MOSSY_CREEPER_STATUE};
    public static final EnumMemorials[] QUARTZ_CREEPER_MEMORIALS = {EnumMemorials.QUARTZ_CREEPER_STATUE};
    public static final EnumMemorials[] ICE_CREEPER_MEMORIALS = {EnumMemorials.ICE_CREEPER_STATUE};

    public static final EnumMemorials[] WOODEN_STATUES_MEMORIALS = {
            EnumMemorials.WOODEN_STEVE_STATUE,
            EnumMemorials.WOODEN_VILLAGER_STATUE,
            EnumMemorials.WOODEN_ANGEL_STATUE
    };
    public static final EnumMemorials[] SANDSTONE_STATUES_MEMORIALS = {
            EnumMemorials.SANDSTONE_STEVE_STATUE,
            EnumMemorials.SANDSTONE_VILLAGER_STATUE,
            EnumMemorials.SANDSTONE_ANGEL_STATUE
    };
    public static final EnumMemorials[] STONE_STATUES_MEMORIALS = {
            EnumMemorials.STONE_STEVE_STATUE,
            EnumMemorials.STONE_VILLAGER_STATUE,
            EnumMemorials.STONE_ANGEL_STATUE
    };
    public static final EnumMemorials[] MOSSY_STATUES_MEMORIALS = {
            EnumMemorials.MOSSY_STEVE_STATUE,
            EnumMemorials.MOSSY_VILLAGER_STATUE,
            EnumMemorials.MOSSY_ANGEL_STATUE
    };
    public static final EnumMemorials[] QUARTZ_STATUES_MEMORIALS = {
            EnumMemorials.QUARTZ_STEVE_STATUE,
            EnumMemorials.QUARTZ_VILLAGER_STATUE,
            EnumMemorials.QUARTZ_ANGEL_STATUE
    };
    public static final EnumMemorials[] ICE_STATUES_MEMORIALS = {
            EnumMemorials.ICE_STEVE_STATUE,
            EnumMemorials.ICE_VILLAGER_STATUE,
            EnumMemorials.ICE_ANGEL_STATUE
    };

    public static final EnumMemorials[] TORTURE_MEMORIALS = {
            EnumMemorials.GIBBET,
            EnumMemorials.STOCKS,
            EnumMemorials.BURNING_STAKE
    };

    public BlockGSMemorial() {
        super(Material.rock);
        this.isBlockContainer = true;
        this.setStepSound(Block.soundTypeStone);
        this.setBlockName("Memorial");
        this.setHardness(1);
        this.setResistance(5F);
        this.setCreativeTab(GSTabs.memorialsTab);
        this.setBlockTextureName("stone");
    }

    /*
     * Return memorial metadata by direction
     */
    public static int getMetaDirection(int direction) {
        if (direction==0) { // S
                return 1;
        } else if (direction==1) { // W
                return 2;
        } else if (direction==3) { // E
                return 3;
        } else {
                return 0;// N
        }
    }

    /**
     * Return random memorial type memorialTypetype - type of memorial 0 - all
     * memorials(20% for pets graves), except creeper 1 - only pets memorials 2
     * - only dogs memorials 3 - only cats memorials 4 - creeper memorials 5 -
     * only statues memorials(steve, villager, angel)
     */
    public static byte getMemorialType(World world, int x, int z, Random random, int memorialType) {
        if (memorialType==1) {
                return getRandomMemorial(getPetsMemorialsTypes(world, x, z), random);
        } else if (memorialType==2) {
                return getRandomMemorial(getDogsMemorialsTypes(world, x, z), random);
        } else if (memorialType==3) {
                return getRandomMemorial(getCatsMemorialsTypes(world, x, z), random);
        } else if (memorialType==4) {
                return getRandomMemorial(getCreeperMemorialsTypes(world, x, z), random);
        } else if (memorialType==5) {
                return getRandomMemorial(getStatuesMemorialsTypes(world, x, z), random);
        } else {
                return getRandomMemorial(getGeneratedMemorialsTypes(world, x, z), random);//memorialType==0
        }
    }

    public static byte getRandomMemorial(List<EnumMemorials> memorialTypes, Random rand) {
        if (memorialTypes.size() > 0) {
            return (byte) memorialTypes.get(rand.nextInt(memorialTypes.size())).ordinal();
        } else {
            return 0;
        }
    }

    public static ArrayList<EnumMemorials> getGeneratedMemorialsTypes(World world, int x, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        BiomeDictionary.Type[] array = BiomeDictionary.getTypesForBiome(biome);
        ArrayList<EnumMemorials> memorialTypes = new ArrayList<EnumMemorials>();

        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.DESERT || object==BiomeDictionary.Type.BEACH) {
                memorialTypes.addAll(Arrays.asList(SANDSTONE_GENERATED_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.JUNGLE || object==BiomeDictionary.Type.SWAMP) {
                memorialTypes.addAll(Arrays.asList(MOSSY_GENERATED_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.MOUNTAIN || object==BiomeDictionary.Type.HILLS || object==BiomeDictionary.Type.PLAINS || object==BiomeDictionary.Type.MUSHROOM) {
                memorialTypes.addAll(Arrays.asList(STONE_GENERATED_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.FOREST) {
                memorialTypes.addAll(Arrays.asList(WOODEN_GENERATED_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.FROZEN) {
                memorialTypes.addAll(Arrays.asList(ICE_GENERATED_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.NETHER) {
                memorialTypes.addAll(Arrays.asList(QUARTZ_GENERATED_MEMORIALS));
                break;
            }
        }

        // TODO
//        if (biomeTypesList.contains(BiomeDictionary.Type.END)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.MAGICAL)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WATER)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WASTELAND)) {
//        }

        if (memorialTypes.isEmpty()) {
            memorialTypes.addAll(Arrays.asList(STONE_GENERATED_MEMORIALS));
        }

        return memorialTypes;
    }

    public static ArrayList<EnumMemorials> getPetsMemorialsTypes(World world, int x, int z) {
        ArrayList<EnumMemorials> memorialTypes = new ArrayList<EnumMemorials>();
        memorialTypes.addAll(getDogsMemorialsTypes(world, x, z));
        memorialTypes.addAll(getCatsMemorialsTypes(world, x, z));

        return memorialTypes;
    }

    public static ArrayList<EnumMemorials> getDogsMemorialsTypes(World world, int x, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        BiomeDictionary.Type[] array = BiomeDictionary.getTypesForBiome(biome);
        cpw.mods.fml.common.FMLLog.info("GraveStone info getDogsMemorialsTypes call");
        cpw.mods.fml.common.FMLLog.info("GraveStone info getDogsMemorialsTypes - BiomeDictionary.Type[] array length: "+array.length);
        ArrayList<EnumMemorials> memorialTypes = new ArrayList<EnumMemorials>();

        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.SANDY || object==BiomeDictionary.Type.BEACH) {
                memorialTypes.addAll(Arrays.asList(SANDSTONE_DOG_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.JUNGLE || object==BiomeDictionary.Type.SWAMP) {
            memorialTypes.addAll(Arrays.asList(MOSSY_DOG_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.SANDY || object==BiomeDictionary.Type.BEACH) {
                memorialTypes.addAll(Arrays.asList(SANDSTONE_DOG_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.MOUNTAIN || object==BiomeDictionary.Type.HILLS || object==BiomeDictionary.Type.PLAINS || object==BiomeDictionary.Type.MUSHROOM) {
            memorialTypes.addAll(Arrays.asList(STONE_DOG_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.FOREST) {
                memorialTypes.addAll(Arrays.asList(WOODEN_DOG_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.SNOWY) {
                memorialTypes.addAll(Arrays.asList(ICE_DOG_MEMORIALS));
                break;
            }
        }
        for (BiomeDictionary.Type object : array) {
            if (object==BiomeDictionary.Type.NETHER) {
                memorialTypes.addAll(Arrays.asList(QUARTZ_DOG_MEMORIALS));
                break;
            }
        }

        // TODO
//        if (biomeTypesList.contains(BiomeDictionary.Type.END)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.MAGICAL)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WATER)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WASTELAND)) {
//        }

        if (memorialTypes.isEmpty()) {
            memorialTypes.addAll(Arrays.asList(STONE_DOG_MEMORIALS));
        }

        return memorialTypes;
    }

    public static ArrayList<EnumMemorials> getCatsMemorialsTypes(World world, int x, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        EnumSet<BiomeDictionary.Type> biomeTypesList = EnumSet.copyOf((Collection<BiomeDictionary.Type>)Arrays.asList(BiomeDictionary.getTypesForBiome(biome)));
        ArrayList<EnumMemorials> memorialTypes = new ArrayList<EnumMemorials>();

        if (biomeTypesList.contains(BiomeDictionary.Type.SANDY) || biomeTypesList.contains(BiomeDictionary.Type.BEACH)) {
            memorialTypes.addAll(Arrays.asList(SANDSTONE_CAT_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.JUNGLE) || biomeTypesList.contains(BiomeDictionary.Type.SWAMP)) {
            memorialTypes.addAll(Arrays.asList(MOSSY_CAT_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.MOUNTAIN) || biomeTypesList.contains(BiomeDictionary.Type.HILLS) ||
                biomeTypesList.contains(BiomeDictionary.Type.PLAINS) || biomeTypesList.contains(BiomeDictionary.Type.MUSHROOM)) {
            memorialTypes.addAll(Arrays.asList(STONE_CAT_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.FOREST)) {
            memorialTypes.addAll(Arrays.asList(WOODEN_CAT_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.SNOWY)) {
            memorialTypes.addAll(Arrays.asList(ICE_CAT_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.NETHER)) {
            memorialTypes.addAll(Arrays.asList(QUARTZ_CAT_MEMORIALS));
        }

        // TODO
//        if (biomeTypesList.contains(BiomeDictionary.Type.END)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.MAGICAL)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WATER)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WASTELAND)) {
//        }

        if (memorialTypes.isEmpty()) {
            memorialTypes.addAll(Arrays.asList(STONE_CAT_MEMORIALS));
        }

        return memorialTypes;
    }

    public static ArrayList<EnumMemorials> getCreeperMemorialsTypes(World world, int x, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        EnumSet<BiomeDictionary.Type> biomeTypesList = EnumSet.copyOf((Collection<BiomeDictionary.Type>)Arrays.asList(BiomeDictionary.getTypesForBiome(biome)));
        ArrayList<EnumMemorials> memorialTypes = new ArrayList<EnumMemorials>();

        if (biomeTypesList.contains(BiomeDictionary.Type.SANDY) || biomeTypesList.contains(BiomeDictionary.Type.BEACH)) {
            memorialTypes.addAll(Arrays.asList(SANDSTONE_CREEPER_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.JUNGLE) || biomeTypesList.contains(BiomeDictionary.Type.SWAMP)) {
            memorialTypes.addAll(Arrays.asList(MOSSY_CREEPER_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.MOUNTAIN) || biomeTypesList.contains(BiomeDictionary.Type.HILLS) ||
                biomeTypesList.contains(BiomeDictionary.Type.PLAINS) || biomeTypesList.contains(BiomeDictionary.Type.MUSHROOM)) {
            memorialTypes.addAll(Arrays.asList(STONE_CREEPER_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.FOREST)) {
            memorialTypes.addAll(Arrays.asList(WOODEN_CREEPER_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.SNOWY)) {
            memorialTypes.addAll(Arrays.asList(ICE_CREEPER_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.NETHER)) {
            memorialTypes.addAll(Arrays.asList(QUARTZ_CREEPER_MEMORIALS));
        }

        // TODO
//        if (biomeTypesList.contains(BiomeDictionary.Type.END)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.MAGICAL)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WATER)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WASTELAND)) {
//        }

        if (memorialTypes.isEmpty()) {
            memorialTypes.addAll(Arrays.asList(STONE_CREEPER_MEMORIALS));
        }

        return memorialTypes;
    }

    public static ArrayList<EnumMemorials> getStatuesMemorialsTypes(World world, int x, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        EnumSet<BiomeDictionary.Type> biomeTypesList = EnumSet.copyOf((Collection<BiomeDictionary.Type>)Arrays.asList(BiomeDictionary.getTypesForBiome(biome)));
        ArrayList<EnumMemorials> memorialTypes = new ArrayList<EnumMemorials>();

        if (biomeTypesList.contains(BiomeDictionary.Type.SANDY) || biomeTypesList.contains(BiomeDictionary.Type.BEACH)) {
            memorialTypes.addAll(Arrays.asList(SANDSTONE_STATUES_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.JUNGLE) || biomeTypesList.contains(BiomeDictionary.Type.SWAMP)) {
            memorialTypes.addAll(Arrays.asList(MOSSY_STATUES_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.MOUNTAIN) || biomeTypesList.contains(BiomeDictionary.Type.HILLS) ||
                biomeTypesList.contains(BiomeDictionary.Type.PLAINS) || biomeTypesList.contains(BiomeDictionary.Type.MUSHROOM)) {
            memorialTypes.addAll(Arrays.asList(STONE_STATUES_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.FOREST)) {
            memorialTypes.addAll(Arrays.asList(WOODEN_STATUES_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.SNOWY)) {
            memorialTypes.addAll(Arrays.asList(ICE_STATUES_MEMORIALS));
        }
        if (biomeTypesList.contains(BiomeDictionary.Type.NETHER)) {
            memorialTypes.addAll(Arrays.asList(QUARTZ_STATUES_MEMORIALS));
        }

        // TODO
//        if (biomeTypesList.contains(BiomeDictionary.Type.END)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.MAGICAL)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WATER)) {
//        }
//        if (biomeTypesList.contains(BiomeDictionary.Type.WASTELAND)) {
//        }

        if (memorialTypes.isEmpty()) {
            memorialTypes.addAll(Arrays.asList(STONE_STATUES_MEMORIALS));
        }

        return memorialTypes;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
        int direction = MathHelper.floor_float(player.rotationYaw);

        if (direction < 0) {
            direction = 360 + direction;
        }

        int metadata = getMetadataBasedOnRotation(direction);
        world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            if (itemStack.stackTagCompound != null) {
                if (itemStack.stackTagCompound.hasKey("isLocalized") && itemStack.stackTagCompound.getBoolean("isLocalized")) {
                    tileEntity.getDeathTextComponent().setLocalized();

                    if (itemStack.stackTagCompound.hasKey("name") && itemStack.stackTagCompound.hasKey("KillerName")) {
                        tileEntity.getDeathTextComponent().setName(itemStack.stackTagCompound.getString("name"));
                        tileEntity.getDeathTextComponent().setKillerName(itemStack.stackTagCompound.getString("KillerName"));
                    }
                }

                if (itemStack.stackTagCompound.hasKey("DeathText")) {
                    tileEntity.getDeathTextComponent().setDeathText(itemStack.stackTagCompound.getString("DeathText"));
                }

                if (itemStack.stackTagCompound.hasKey("GraveType")) {
                    tileEntity.setGraveType(itemStack.stackTagCompound.getByte("GraveType"));
                } else {
                    tileEntity.setGraveType((byte) 0);
                }

                if (itemStack.stackTagCompound.hasKey("HangedMob")) {
                    tileEntity.setHangedMob(EnumHangedMobs.getByID(itemStack.stackTagCompound.getByte("HangedMob")));
                    tileEntity.setHangedVillagerProfession(itemStack.stackTagCompound.getInteger("HangedVillagerProfession"));
                }

                placeWalls(world, x, y, z);
            }
        }
    }

    public static void placeWalls(World world, int x, int y, int z) {
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            //TODO almost the same code in ItemBlockGSMemorial
            byte maxY;
            byte maxX = 1;
            byte maxZ = 1;
            byte startX = 0;
            byte startZ = 0;
            EnumMemorials memorialType = tileEntity.getMemorialType();

            if (memorialType==EnumMemorials.WOODEN_CROSS || memorialType==EnumMemorials.SANDSTONE_CROSS || memorialType==EnumMemorials.STONE_CROSS || memorialType==EnumMemorials.MOSSY_CROSS || memorialType==EnumMemorials.IRON_CROSS || memorialType==EnumMemorials.GOLDEN_CROSS || memorialType==EnumMemorials.DIAMOND_CROSS || memorialType==EnumMemorials.EMERALD_CROSS || memorialType==EnumMemorials.LAPIS_CROSS || memorialType==EnumMemorials.REDSTONE_CROSS || memorialType==EnumMemorials.OBSIDIAN_CROSS || memorialType==EnumMemorials.QUARTZ_CROSS || memorialType==EnumMemorials.ICE_CROSS || memorialType==EnumMemorials.WOODEN_OBELISK || memorialType==EnumMemorials.SANDSTONE_OBELISK || memorialType==EnumMemorials.STONE_OBELISK || memorialType==EnumMemorials.MOSSY_OBELISK || memorialType==EnumMemorials.IRON_OBELISK || memorialType==EnumMemorials.GOLDEN_OBELISK || memorialType==EnumMemorials.DIAMOND_OBELISK || memorialType==EnumMemorials.EMERALD_OBELISK || memorialType==EnumMemorials.LAPIS_OBELISK || memorialType==EnumMemorials.REDSTONE_OBELISK || memorialType==EnumMemorials.OBSIDIAN_OBELISK || memorialType==EnumMemorials.QUARTZ_OBELISK || memorialType==EnumMemorials.ICE_OBELISK) {
                    maxY = 5;
                    maxX = 2;
                    maxZ = 2;
                    startX = -1;
                    startZ = -1;
            } else if (memorialType==EnumMemorials.WOODEN_DOG_STATUE || memorialType==EnumMemorials.WOODEN_CAT_STATUE || memorialType==EnumMemorials.SANDSTONE_DOG_STATUE || memorialType==EnumMemorials.SANDSTONE_CAT_STATUE || memorialType==EnumMemorials.STONE_DOG_STATUE || memorialType==EnumMemorials.STONE_CAT_STATUE || memorialType==EnumMemorials.MOSSY_DOG_STATUE || memorialType==EnumMemorials.MOSSY_CAT_STATUE || memorialType==EnumMemorials.IRON_DOG_STATUE || memorialType==EnumMemorials.IRON_CAT_STATUE || memorialType==EnumMemorials.GOLDEN_DOG_STATUE || memorialType==EnumMemorials.GOLDEN_CAT_STATUE || memorialType==EnumMemorials.DIAMOND_DOG_STATUE || memorialType==EnumMemorials.DIAMOND_CAT_STATUE || memorialType==EnumMemorials.EMERALD_DOG_STATUE || memorialType==EnumMemorials.EMERALD_CAT_STATUE || memorialType==EnumMemorials.LAPIS_DOG_STATUE || memorialType==EnumMemorials.LAPIS_CAT_STATUE || memorialType==EnumMemorials.REDSTONE_DOG_STATUE || memorialType==EnumMemorials.REDSTONE_CAT_STATUE || memorialType==EnumMemorials.OBSIDIAN_DOG_STATUE || memorialType==EnumMemorials.OBSIDIAN_CAT_STATUE || memorialType==EnumMemorials.QUARTZ_DOG_STATUE || memorialType==EnumMemorials.QUARTZ_CAT_STATUE || memorialType==EnumMemorials.ICE_DOG_STATUE || memorialType==EnumMemorials.ICE_CAT_STATUE) {
                    maxY = 2;
            /*} else if (memorialType==EnumMemorials.WOODEN_STEVE_STATUE || memorialType==EnumMemorials.SANDSTONE_STEVE_STATUE || memorialType==EnumMemorials.STONE_STEVE_STATUE || memorialType==EnumMemorials.MOSSY_STEVE_STATUE || memorialType==EnumMemorials.IRON_STEVE_STATUE || memorialType==EnumMemorials.GOLDEN_STEVE_STATUE || memorialType==EnumMemorials.DIAMOND_STEVE_STATUE || memorialType==EnumMemorials.EMERALD_STEVE_STATUE || memorialType==EnumMemorials.LAPIS_STEVE_STATUE || memorialType==EnumMemorials.REDSTONE_STEVE_STATUE || memorialType==EnumMemorials.OBSIDIAN_STEVE_STATUE || memorialType==EnumMemorials.QUARTZ_STEVE_STATUE || memorialType==EnumMemorials.ICE_STEVE_STATUE
                    // villager
            || memorialType==EnumMemorials.WOODEN_VILLAGER_STATUE || memorialType==EnumMemorials.SANDSTONE_VILLAGER_STATUE || memorialType==EnumMemorials.STONE_VILLAGER_STATUE || memorialType==EnumMemorials.MOSSY_VILLAGER_STATUE || memorialType==EnumMemorials.IRON_VILLAGER_STATUE || memorialType==EnumMemorials.GOLDEN_VILLAGER_STATUE || memorialType==EnumMemorials.DIAMOND_VILLAGER_STATUE || memorialType==EnumMemorials.EMERALD_VILLAGER_STATUE || memorialType==EnumMemorials.LAPIS_VILLAGER_STATUE || memorialType==EnumMemorials.REDSTONE_VILLAGER_STATUE || memorialType==EnumMemorials.OBSIDIAN_VILLAGER_STATUE || memorialType==EnumMemorials.QUARTZ_VILLAGER_STATUE || memorialType==EnumMemorials.ICE_VILLAGER_STATUE
                    //angel
            || memorialType==EnumMemorials.WOODEN_ANGEL_STATUE:
            } else if (memorialType==EnumMemorials.SANDSTONE_ANGEL_STATUE || memorialType==EnumMemorials.STONE_ANGEL_STATUE || memorialType==EnumMemorials.MOSSY_ANGEL_STATUE || memorialType==EnumMemorials.IRON_ANGEL_STATUE || memorialType==EnumMemorials.GOLDEN_ANGEL_STATUE || memorialType==EnumMemorials.DIAMOND_ANGEL_STATUE || memorialType==EnumMemorials.EMERALD_ANGEL_STATUE || memorialType==EnumMemorials.LAPIS_ANGEL_STATUE || memorialType==EnumMemorials.REDSTONE_ANGEL_STATUE || memorialType==EnumMemorials.OBSIDIAN_ANGEL_STATUE || memorialType==EnumMemorials.QUARTZ_ANGEL_STATUE || memorialType==EnumMemorials.ICE_ANGEL_STATUE || memorialType==EnumMemorials.WOODEN_CREEPER_STATUE || memorialType==EnumMemorials.SANDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.STONE_CREEPER_STATUE || memorialType==EnumMemorials.MOSSY_CREEPER_STATUE || memorialType==EnumMemorials.IRON_CREEPER_STATUE || memorialType==EnumMemorials.GOLDEN_CREEPER_STATUE || memorialType==EnumMemorials.DIAMOND_CREEPER_STATUE || memorialType==EnumMemorials.EMERALD_CREEPER_STATUE || memorialType==EnumMemorials.LAPIS_CREEPER_STATUE || memorialType==EnumMemorials.REDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.OBSIDIAN_CREEPER_STATUE || memorialType==EnumMemorials.QUARTZ_CREEPER_STATUE || memorialType==EnumMemorials.ICE_CREEPER_STATUE) {*/
            } else {//commented object ^, include in default switch
                    maxY = 3;
            }
            for (byte shiftY = 0; shiftY < maxY; shiftY++) {
                for (byte shiftZ = startZ; shiftZ < maxZ; shiftZ++) {
                    for (byte shiftX = startX; shiftX < maxX; shiftX++) {
                        int newX = x + shiftX;
                        int newY = y + shiftY;
                        int newZ = z + shiftZ;
                        if (world.getBlock(newX, newY, newZ) == Blocks.air) {
                            world.setBlock(newX, newY, newZ, GSBlock.invisibleWall);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosionIn) {
        removeWalls(world, x, y, z);
        super.onBlockDestroyedByExplosion(world, x, y, z, explosionIn);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        removeWalls(world, x, y, z);
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    private static void removeWalls(World world, int x, int y, int z) {
        //TODO almost the same code in ItemBlockGSMemorial
        byte maxY;
        byte maxX = 1;
        byte maxZ = 1;
        byte startX = 0;
        byte startZ = 0;

        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            EnumMemorials memorialType = tileEntity.getMemorialType();
            if (memorialType==EnumMemorials.WOODEN_CROSS || memorialType==EnumMemorials.SANDSTONE_CROSS || memorialType==EnumMemorials.STONE_CROSS || memorialType==EnumMemorials.MOSSY_CROSS || memorialType==EnumMemorials.IRON_CROSS || memorialType==EnumMemorials.GOLDEN_CROSS || memorialType==EnumMemorials.DIAMOND_CROSS || memorialType==EnumMemorials.EMERALD_CROSS || memorialType==EnumMemorials.LAPIS_CROSS || memorialType==EnumMemorials.REDSTONE_CROSS || memorialType==EnumMemorials.OBSIDIAN_CROSS || memorialType==EnumMemorials.QUARTZ_CROSS || memorialType==EnumMemorials.ICE_CROSS || memorialType==EnumMemorials.WOODEN_OBELISK || memorialType==EnumMemorials.SANDSTONE_OBELISK || memorialType==EnumMemorials.STONE_OBELISK || memorialType==EnumMemorials.MOSSY_OBELISK || memorialType==EnumMemorials.IRON_OBELISK || memorialType==EnumMemorials.GOLDEN_OBELISK || memorialType==EnumMemorials.DIAMOND_OBELISK || memorialType==EnumMemorials.EMERALD_OBELISK || memorialType==EnumMemorials.LAPIS_OBELISK || memorialType==EnumMemorials.REDSTONE_OBELISK || memorialType==EnumMemorials.OBSIDIAN_OBELISK || memorialType==EnumMemorials.QUARTZ_OBELISK || memorialType==EnumMemorials.ICE_OBELISK) {
                    maxY = 5;
                    maxX = 2;
                    maxZ = 2;
                    startX = -1;
                    startZ = -1;
            } else if (memorialType==EnumMemorials.WOODEN_DOG_STATUE || memorialType==EnumMemorials.WOODEN_CAT_STATUE || memorialType==EnumMemorials.SANDSTONE_DOG_STATUE || memorialType==EnumMemorials.SANDSTONE_CAT_STATUE || memorialType==EnumMemorials.STONE_DOG_STATUE || memorialType==EnumMemorials.STONE_CAT_STATUE || memorialType==EnumMemorials.MOSSY_DOG_STATUE || memorialType==EnumMemorials.MOSSY_CAT_STATUE || memorialType==EnumMemorials.IRON_DOG_STATUE || memorialType==EnumMemorials.IRON_CAT_STATUE || memorialType==EnumMemorials.GOLDEN_DOG_STATUE || memorialType==EnumMemorials.GOLDEN_CAT_STATUE || memorialType==EnumMemorials.DIAMOND_DOG_STATUE || memorialType==EnumMemorials.DIAMOND_CAT_STATUE || memorialType==EnumMemorials.EMERALD_DOG_STATUE || memorialType==EnumMemorials.EMERALD_CAT_STATUE || memorialType==EnumMemorials.LAPIS_DOG_STATUE || memorialType==EnumMemorials.LAPIS_CAT_STATUE || memorialType==EnumMemorials.REDSTONE_DOG_STATUE || memorialType==EnumMemorials.REDSTONE_CAT_STATUE || memorialType==EnumMemorials.OBSIDIAN_DOG_STATUE || memorialType==EnumMemorials.OBSIDIAN_CAT_STATUE || memorialType==EnumMemorials.QUARTZ_DOG_STATUE || memorialType==EnumMemorials.QUARTZ_CAT_STATUE || memorialType==EnumMemorials.ICE_DOG_STATUE || memorialType==EnumMemorials.ICE_CAT_STATUE) {
                    maxY = 2;
            /*} else if (memorialType==EnumMemorials.WOODEN_STEVE_STATUE || memorialType==EnumMemorials.SANDSTONE_STEVE_STATUE || memorialType==EnumMemorials.STONE_STEVE_STATUE || memorialType==EnumMemorials.MOSSY_STEVE_STATUE || memorialType==EnumMemorials.IRON_STEVE_STATUE || memorialType==EnumMemorials.GOLDEN_STEVE_STATUE || memorialType==EnumMemorials.DIAMOND_STEVE_STATUE || memorialType==EnumMemorials.EMERALD_STEVE_STATUE || memorialType==EnumMemorials.LAPIS_STEVE_STATUE || memorialType==EnumMemorials.REDSTONE_STEVE_STATUE || memorialType==EnumMemorials.OBSIDIAN_STEVE_STATUE || memorialType==EnumMemorials.QUARTZ_STEVE_STATUE || memorialType==EnumMemorials.ICE_STEVE_STATUE
                    // villager
            || memorialType==EnumMemorials.WOODEN_VILLAGER_STATUE || memorialType==EnumMemorials.SANDSTONE_VILLAGER_STATUE || memorialType==EnumMemorials.STONE_VILLAGER_STATUE || memorialType==EnumMemorials.MOSSY_VILLAGER_STATUE || memorialType==EnumMemorials.IRON_VILLAGER_STATUE || memorialType==EnumMemorials.GOLDEN_VILLAGER_STATUE || memorialType==EnumMemorials.DIAMOND_VILLAGER_STATUE || memorialType==EnumMemorials.EMERALD_VILLAGER_STATUE || memorialType==EnumMemorials.LAPIS_VILLAGER_STATUE || memorialType==EnumMemorials.REDSTONE_VILLAGER_STATUE || memorialType==EnumMemorials.OBSIDIAN_VILLAGER_STATUE || memorialType==EnumMemorials.QUARTZ_VILLAGER_STATUE || memorialType==EnumMemorials.ICE_VILLAGER_STATUE
                    //angel
            || memorialType==EnumMemorials.WOODEN_ANGEL_STATUE || memorialType==EnumMemorials.SANDSTONE_ANGEL_STATUE || memorialType==EnumMemorials.STONE_ANGEL_STATUE || memorialType==EnumMemorials.MOSSY_ANGEL_STATUE || memorialType==EnumMemorials.IRON_ANGEL_STATUE || memorialType==EnumMemorials.GOLDEN_ANGEL_STATUE || memorialType==EnumMemorials.DIAMOND_ANGEL_STATUE || memorialType==EnumMemorials.EMERALD_ANGEL_STATUE || memorialType==EnumMemorials.LAPIS_ANGEL_STATUE || memorialType==EnumMemorials.REDSTONE_ANGEL_STATUE || memorialType==EnumMemorials.OBSIDIAN_ANGEL_STATUE || memorialType==EnumMemorials.QUARTZ_ANGEL_STATUE || memorialType==EnumMemorials.ICE_ANGEL_STATUE || memorialType==EnumMemorials.WOODEN_CREEPER_STATUE || memorialType==EnumMemorials.SANDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.STONE_CREEPER_STATUE || memorialType==EnumMemorials.MOSSY_CREEPER_STATUE || memorialType==EnumMemorials.IRON_CREEPER_STATUE || memorialType==EnumMemorials.GOLDEN_CREEPER_STATUE || memorialType==EnumMemorials.DIAMOND_CREEPER_STATUE || memorialType==EnumMemorials.EMERALD_CREEPER_STATUE || memorialType==EnumMemorials.LAPIS_CREEPER_STATUE || memorialType==EnumMemorials.REDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.OBSIDIAN_CREEPER_STATUE || memorialType==EnumMemorials.QUARTZ_CREEPER_STATUE || memorialType==EnumMemorials.ICE_CREEPER_STATUE) {*/
            } else {//commented object ^, include in default switch
                    maxY = 3;
            }
            for (byte shiftY = 0; shiftY < maxY; shiftY++) {
                for (byte shiftZ = startZ; shiftZ < maxZ; shiftZ++) {
                    for (byte shiftX = startX; shiftX < maxX; shiftX++) {
                        int newX = x + shiftX;
                        int newY = y + shiftY;
                        int newZ = z + shiftZ;
                        if (world.getBlock(newX, newY, newZ) == GSBlock.invisibleWall) {
                            world.setBlock(newX, newY, newZ, Blocks.air);
                        }
                    }
                }
            }
        }
    }

    private int getMetadataBasedOnRotation(int rotation) {
        if (rotation >= 315 || rotation < 45) {
            return 1;
        } else if (rotation >= 45 && rotation < 135) {
            return 2;
        } else if (rotation >= 135 && rotation < 225) {
            return 0;
        } else {
            return 3;
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        int meta = access.getBlockMetadata(x, y, z);
        EnumMemorials memorialType;
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) access.getTileEntity(x, y, z);

        boolean te_isNull = tileEntity != null;
        if (te_isNull) {
            memorialType = tileEntity.getMemorialType();
        } else {
            memorialType = EnumMemorials.STONE_CROSS;
        }

        if (memorialType==EnumMemorials.STONE_CROSS || memorialType==EnumMemorials.WOODEN_CROSS || memorialType==EnumMemorials.SANDSTONE_CROSS || memorialType==EnumMemorials.MOSSY_CROSS || memorialType==EnumMemorials.IRON_CROSS || memorialType==EnumMemorials.GOLDEN_CROSS || memorialType==EnumMemorials.DIAMOND_CROSS || memorialType==EnumMemorials.EMERALD_CROSS || memorialType==EnumMemorials.LAPIS_CROSS || memorialType==EnumMemorials.REDSTONE_CROSS || memorialType==EnumMemorials.OBSIDIAN_CROSS || memorialType==EnumMemorials.QUARTZ_CROSS || memorialType==EnumMemorials.ICE_CROSS || memorialType==EnumMemorials.WOODEN_OBELISK || memorialType==EnumMemorials.SANDSTONE_OBELISK || memorialType==EnumMemorials.STONE_OBELISK || memorialType==EnumMemorials.MOSSY_OBELISK || memorialType==EnumMemorials.IRON_OBELISK || memorialType==EnumMemorials.GOLDEN_OBELISK || memorialType==EnumMemorials.DIAMOND_OBELISK || memorialType==EnumMemorials.EMERALD_OBELISK || memorialType==EnumMemorials.LAPIS_OBELISK || memorialType==EnumMemorials.REDSTONE_OBELISK || memorialType==EnumMemorials.OBSIDIAN_OBELISK || memorialType==EnumMemorials.QUARTZ_OBELISK || memorialType==EnumMemorials.ICE_OBELISK) {
                this.setBlockBounds(-1, 0, -1, 2, 5, 2);
        } else if (memorialType==EnumMemorials.WOODEN_STEVE_STATUE || memorialType==EnumMemorials.SANDSTONE_STEVE_STATUE || memorialType==EnumMemorials.STONE_STEVE_STATUE || memorialType==EnumMemorials.MOSSY_STEVE_STATUE || memorialType==EnumMemorials.IRON_STEVE_STATUE || memorialType==EnumMemorials.GOLDEN_STEVE_STATUE || memorialType==EnumMemorials.DIAMOND_STEVE_STATUE || memorialType==EnumMemorials.EMERALD_STEVE_STATUE || memorialType==EnumMemorials.LAPIS_STEVE_STATUE || memorialType==EnumMemorials.REDSTONE_STEVE_STATUE || memorialType==EnumMemorials.OBSIDIAN_STEVE_STATUE || memorialType==EnumMemorials.QUARTZ_STEVE_STATUE || memorialType==EnumMemorials.ICE_STEVE_STATUE
                // villager
        || memorialType==EnumMemorials.WOODEN_VILLAGER_STATUE || memorialType==EnumMemorials.SANDSTONE_VILLAGER_STATUE || memorialType==EnumMemorials.STONE_VILLAGER_STATUE || memorialType==EnumMemorials.MOSSY_VILLAGER_STATUE || memorialType==EnumMemorials.IRON_VILLAGER_STATUE || memorialType==EnumMemorials.GOLDEN_VILLAGER_STATUE || memorialType==EnumMemorials.DIAMOND_VILLAGER_STATUE || memorialType==EnumMemorials.EMERALD_VILLAGER_STATUE || memorialType==EnumMemorials.LAPIS_VILLAGER_STATUE || memorialType==EnumMemorials.REDSTONE_VILLAGER_STATUE || memorialType==EnumMemorials.OBSIDIAN_VILLAGER_STATUE || memorialType==EnumMemorials.QUARTZ_VILLAGER_STATUE || memorialType==EnumMemorials.ICE_VILLAGER_STATUE
                //angel
        || memorialType==EnumMemorials.WOODEN_ANGEL_STATUE || memorialType==EnumMemorials.SANDSTONE_ANGEL_STATUE || memorialType==EnumMemorials.STONE_ANGEL_STATUE || memorialType==EnumMemorials.MOSSY_ANGEL_STATUE || memorialType==EnumMemorials.IRON_ANGEL_STATUE || memorialType==EnumMemorials.GOLDEN_ANGEL_STATUE || memorialType==EnumMemorials.DIAMOND_ANGEL_STATUE || memorialType==EnumMemorials.EMERALD_ANGEL_STATUE || memorialType==EnumMemorials.LAPIS_ANGEL_STATUE || memorialType==EnumMemorials.REDSTONE_ANGEL_STATUE || memorialType==EnumMemorials.OBSIDIAN_ANGEL_STATUE || memorialType==EnumMemorials.QUARTZ_ANGEL_STATUE || memorialType==EnumMemorials.ICE_ANGEL_STATUE) {
                this.setBlockBounds(0.125F, 0, 0.125F, 0.875F, 3F, 0.875F);
        } else if (memorialType==EnumMemorials.WOODEN_DOG_STATUE || memorialType==EnumMemorials.WOODEN_CAT_STATUE || memorialType==EnumMemorials.SANDSTONE_DOG_STATUE || memorialType==EnumMemorials.SANDSTONE_CAT_STATUE || memorialType==EnumMemorials.STONE_DOG_STATUE || memorialType==EnumMemorials.STONE_CAT_STATUE || memorialType==EnumMemorials.MOSSY_DOG_STATUE || memorialType==EnumMemorials.MOSSY_CAT_STATUE || memorialType==EnumMemorials.IRON_DOG_STATUE || memorialType==EnumMemorials.IRON_CAT_STATUE || memorialType==EnumMemorials.GOLDEN_DOG_STATUE || memorialType==EnumMemorials.GOLDEN_CAT_STATUE || memorialType==EnumMemorials.DIAMOND_DOG_STATUE || memorialType==EnumMemorials.DIAMOND_CAT_STATUE || memorialType==EnumMemorials.EMERALD_DOG_STATUE || memorialType==EnumMemorials.EMERALD_CAT_STATUE || memorialType==EnumMemorials.LAPIS_DOG_STATUE || memorialType==EnumMemorials.LAPIS_CAT_STATUE || memorialType==EnumMemorials.REDSTONE_DOG_STATUE || memorialType==EnumMemorials.REDSTONE_CAT_STATUE || memorialType==EnumMemorials.OBSIDIAN_DOG_STATUE || memorialType==EnumMemorials.OBSIDIAN_CAT_STATUE || memorialType==EnumMemorials.QUARTZ_DOG_STATUE || memorialType==EnumMemorials.QUARTZ_CAT_STATUE || memorialType==EnumMemorials.ICE_DOG_STATUE || memorialType==EnumMemorials.ICE_CAT_STATUE) {
                this.setBlockBounds(0.125F, 0, 0.125F, 0.875F, 2, 0.875F);
        } else if (memorialType==EnumMemorials.WOODEN_CREEPER_STATUE || memorialType==EnumMemorials.SANDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.STONE_CREEPER_STATUE || memorialType==EnumMemorials.MOSSY_CREEPER_STATUE || memorialType==EnumMemorials.IRON_CREEPER_STATUE || memorialType==EnumMemorials.GOLDEN_CREEPER_STATUE || memorialType==EnumMemorials.DIAMOND_CREEPER_STATUE || memorialType==EnumMemorials.EMERALD_CREEPER_STATUE || memorialType==EnumMemorials.LAPIS_CREEPER_STATUE || memorialType==EnumMemorials.REDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.OBSIDIAN_CREEPER_STATUE || memorialType==EnumMemorials.QUARTZ_CREEPER_STATUE || memorialType==EnumMemorials.ICE_CREEPER_STATUE) {
                this.setBlockBounds(0.125F, 0, 0.125F, 0.875F, 2.5F, 0.875F);
        } else if (memorialType==EnumMemorials.GIBBET || memorialType==EnumMemorials.BURNING_STAKE) {
                this.setBlockBounds(0, 0, 0, 1, 2.5F, 1);
        } else if (memorialType==EnumMemorials.STOCKS) {
                if (meta==0 || meta==1) {
                        this.setBlockBounds(-0.5F, 0, 0, 1.5F, 2, 1);
                } else if (meta==2 || meta==3) {
                        this.setBlockBounds(0, 0, -0.5F, 1, 2, 1.5F);
                }
        }
    }

    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0, 0, 0, 1, 1, 2);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return GraveStoneConfig.memorialRenderID;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        TileEntityGSMemorial te = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (te != null) {
            ItemStack item = player.inventory.getCurrentItem();
            if (item != null && item.getItem() instanceof ItemGSCorpse && EnumCorpse.getById((byte) item.getItemDamage()).equals(EnumCorpse.VILLAGER) &&
                    te.getHangedMob() == EnumHangedMobs.NONE && (te.getMemorialType().equals(EnumMemorials.GIBBET) ||
                    te.getMemorialType().equals(EnumMemorials.STOCKS) || te.getMemorialType().equals(EnumMemorials.BURNING_STAKE))) {
                te.setHangedMob(EnumHangedMobs.VILLAGER);
                te.setHangedVillagerProfession(VillagerCorpseHelper.getVillagerType(item.getTagCompound()));
                item.stackSize--;
            }

            if (world.isRemote) {
                String name;
                String killerName;
                String deathText = te.getDeathTextComponent().getDeathText();

                if (deathText.length() != 0) {
                    //entityPlayer.sendChatToPlayer(ChatMessageComponent.func_111066_d(deathText));

                    if (te.getDeathTextComponent().isLocalized()) {
                        name = ModGraveStone.proxy.getLocalizedEntityName(te.getDeathTextComponent().getName());
                        if (name.length() != 0) {
                            killerName = ModGraveStone.proxy.getLocalizedEntityName(te.getDeathTextComponent().getKillerName());

                            if (killerName.length() == 0) {
                                player.addChatComponentMessage(new ChatComponentTranslation(deathText, new Object[]{name}));
                            } else {
                                player.addChatComponentMessage(new ChatComponentTranslation(deathText, new Object[]{name, killerName}));
                            }
                            return false;
                        }
                    }
                    player.addChatComponentMessage(new ChatComponentTranslation(deathText));
                }
            }
        }

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
        return new TileEntityGSMemorial(world);
    }

    @Override
    public int damageDropped(int metadata) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (byte i = 0; i < TAB_MEMORIALS.length; i++) {
            list.add(getMemorialItemForCreativeInventory(item, TAB_MEMORIALS[i]));
        }

        // gibbets
        for (byte mobType = 0; mobType < EnumHangedMobs.VALUES.length; mobType++) {
            ItemStack stack = getMemorialItemForCreativeInventory(item, (byte) EnumMemorials.GIBBET.ordinal());
            stack.getTagCompound().setByte("HangedMob", mobType);
            EnumHangedMobs value = EnumHangedMobs.VALUES[mobType];
            if (value==EnumHangedMobs.VILLAGER) {
                    ItemStack villagerStack;
                    for (byte villagerProfession = 0; villagerProfession <= 4; villagerProfession++) {
                        villagerStack = stack.copy();
                        villagerStack.getTagCompound().setInteger("HangedVillagerProfession", villagerProfession);
                        list.add(villagerStack);
                    }

                    Collection<Integer> villagerIds = VillagerRegistry.getRegisteredVillagers();
                    Iterator<Integer> it = villagerIds.iterator();
                    while (it.hasNext()) {
                        villagerStack = stack.copy();
                        villagerStack.getTagCompound().setInteger("HangedVillagerProfession", it.next());
                        list.add(villagerStack);
                    }
            } else {
                    list.add(stack);
            }
        }

        // stocks
        for (byte mobType = 0; mobType < EnumHangedMobs.VALUES.length; mobType++) {
            ItemStack stack = getMemorialItemForCreativeInventory(item, (byte) EnumMemorials.STOCKS.ordinal());
            stack.getTagCompound().setByte("HangedMob", mobType);
            EnumHangedMobs value = EnumHangedMobs.VALUES[mobType];
            if (value==EnumHangedMobs.VILLAGER) {
                    ItemStack villagerStack;
                    for (byte villagerProfession = 0; villagerProfession <= 4; villagerProfession++) {
                        villagerStack = stack.copy();
                        villagerStack.getTagCompound().setInteger("HangedVillagerProfession", villagerProfession);
                        list.add(villagerStack);
                    }

                    Collection<Integer> villagerIds = VillagerRegistry.getRegisteredVillagers();
                    Iterator<Integer> it = villagerIds.iterator();
                    while (it.hasNext()) {
                        villagerStack = stack.copy();
                        villagerStack.getTagCompound().setInteger("HangedVillagerProfession", it.next());
                        list.add(villagerStack);
                    }
            } else {
                    list.add(stack);
            }
        }
        // burning stake
        for (byte mobType = 0; mobType < EnumHangedMobs.VALUES.length; mobType++) {
            ItemStack stack = getMemorialItemForCreativeInventory(item, (byte) EnumMemorials.BURNING_STAKE.ordinal());
            stack.getTagCompound().setByte("HangedMob", mobType);
            EnumHangedMobs value = EnumHangedMobs.VALUES[mobType];
            if (value==EnumHangedMobs.VILLAGER) {
                    ItemStack villagerStack;
                    for (byte villagerProfession = 0; villagerProfession <= 4; villagerProfession++) {
                        villagerStack = stack.copy();
                        villagerStack.getTagCompound().setInteger("HangedVillagerProfession", villagerProfession);
                        list.add(villagerStack);
                    }

                    Collection<Integer> villagerIds = VillagerRegistry.getRegisteredVillagers();
                    Iterator<Integer> it = villagerIds.iterator();
                    while (it.hasNext()) {
                        villagerStack = stack.copy();
                        villagerStack.getTagCompound().setInteger("HangedVillagerProfession", it.next());
                        list.add(villagerStack);
                    }
                    break;
            } else {
                    list.add(stack);
            }
        }
    }

    private static ItemStack getMemorialItemForCreativeInventory(Item item, byte graveType) {
        ItemStack stack = new ItemStack(item, 1, 0);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("GraveType", graveType);
        stack.setTagCompound(nbt);
        return stack;
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
        player.addExhaustion(0.025F);

        ItemStack itemStack;
        if (EnchantmentHelper.getSilkTouchModifier(player)) {
            itemStack = getBlockItemStack(world, x, y, z);
        } else {
            itemStack = getBlockItemStackWithoutInfo(world, x, y, z);
        }

        if (itemStack != null) {
            this.dropBlockAsItem(world, x, y, z, itemStack);
        }
    }

    private ItemStack getBlockItemStack(World world, int x, int y, int z) {
        ItemStack itemStack = this.createStackedBlock(0);
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            if (tileEntity.getDeathTextComponent().isLocalized()) {
                nbt.setBoolean("isLocalized", true);
                nbt.setString("name", tileEntity.getDeathTextComponent().getName());
                nbt.setString("KillerName", tileEntity.getDeathTextComponent().getKillerName());
            }
            nbt.setString("DeathText", tileEntity.getDeathTextComponent().getDeathText());
            nbt.setByte("GraveType", tileEntity.getGraveTypeNum());

            nbt.setBoolean("Enchanted", tileEntity.isEnchanted());

            nbt.setByte("HangedMob", (byte) tileEntity.getHangedMob().ordinal());
            nbt.setInteger("HangedVillagerProfession", tileEntity.getHangedVillagerProfession());

            itemStack.setTagCompound(nbt);
        }

        return itemStack;
    }

    private ItemStack getBlockItemStackWithoutInfo(World world, int x, int y, int z) {
        ItemStack itemStack = this.createStackedBlock(0);
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setByte("GraveType", tileEntity.getGraveTypeNum());
            itemStack.setTagCompound(nbt);
        }

        return itemStack;
    }

    /**
     * Called when the player destroys a block with an item that can harvest it.
     * (i, j, k) are the coordinates of the block and l is the block's
     * subtype/damage.
     */
    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
    }

    /*
     * Drop sword as item
     */
    public void dropCreeperMemorial(World world, int x, int y, int z) {
        byte memorialType = BlockGSMemorial.getMemorialType(world, x, z, new XSTR(new XSTR().getSeed()*(new GeneratorEntropy().getSeed())), 4);
        ItemStack itemStack = new ItemStack(this);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("GraveType", memorialType);
        itemStack.setTagCompound(nbt);
        this.dropBlockAsItem(world, x, y, z, itemStack);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        ItemStack itemStack = this.createStackedBlock(0);
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            if (itemStack != null) {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setByte("GraveType", tileEntity.getGraveTypeNum());

                nbt.setByte("HangedMob", (byte) tileEntity.getHangedMob().ordinal());
                nbt.setInteger("HangedVillagerProfession", tileEntity.getHangedVillagerProfession());

                itemStack.setTagCompound(nbt);
            }
        }
        return itemStack;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        //fix crash, or random crash?
        if (y<0) y=Math.abs(y);
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);

        if (tileEntity != null && tileEntity.getMemorialType() == EnumMemorials.BURNING_STAKE && tileEntity.getHangedMob() != EnumHangedMobs.NONE) {
            return 15;
        } else {
            return super.getLightValue(world, x, y, z);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity.getMemorialType() == EnumMemorials.BURNING_STAKE && tileEntity.getHangedMob() != EnumHangedMobs.NONE) {
            double xPos, zPos, yPos;

            yPos = y + 0.25;
            for (int angle = 0; angle < 20; angle++) {
                xPos = x + 0.5 + Math.sin(angle * 0.2792) * 0.75;
                zPos = z + 0.5 + Math.cos(angle * 0.2792) * 0.75;

                EntityFX entityfx = new EntityBigFlameFX(world, xPos, yPos, zPos, 0, 0, 0);
                Minecraft.getMinecraft().effectRenderer.addEffect(entityfx);
            }

            yPos += 0.25;
            for (int angle = 0; angle < 11; angle++) {
                xPos = x + 0.5 + Math.sin(angle * 0.5584) * 0.5;
                zPos = z + 0.5 + Math.cos(angle * 0.5584) * 0.5;

                EntityFX entityfx = new EntityBigFlameFX(world, xPos, yPos, zPos, 0, 0, 0);
                Minecraft.getMinecraft().effectRenderer.addEffect(entityfx);
            }

            yPos += 0.35;
            for (int angle = 0; angle < 5; angle++) {
                xPos = x + 0.5 + Math.sin(angle * 1.1168) * 0.2;
                zPos = z + 0.5 + Math.cos(angle * 1.1168) * 0.2;

                EntityFX entityfx = new EntityBigFlameFX(world, xPos, yPos, zPos, 0, 0, 0);
                Minecraft.getMinecraft().effectRenderer.addEffect(entityfx);
                world.spawnParticle("lava", xPos, yPos, zPos, 0, 0, 0);
                world.spawnParticle("largesmoke", xPos, yPos, zPos, 0, 0, 0);
            }
        }
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
