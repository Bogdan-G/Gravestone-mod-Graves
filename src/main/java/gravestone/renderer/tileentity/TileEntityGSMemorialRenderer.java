package gravestone.renderer.tileentity;

import com.google.common.collect.ImmutableMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gravestone.block.enums.EnumMemorials;
import gravestone.models.block.ModelMemorial;
import gravestone.models.block.memorials.*;
import gravestone.tileentity.TileEntityGSMemorial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Map;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@SideOnly(Side.CLIENT)
public class TileEntityGSMemorialRenderer extends TileEntityGSRenderer {

    public static ModelMemorial cross = new ModelMemorialCross();
    public static ModelMemorial obelisk = new ModelMemorialObelisk();
    public static ModelMemorial steveStatue = new ModelSteveStatueMemorial();
    public static ModelMemorial villagerStatue = new ModelVillagerMemorial();
    public static ModelMemorial angelStatue = new ModelAngelStatueMemorial();
    public static ModelMemorial dogStatue = new ModelDogStatueMemorial();
    public static ModelMemorial catStatue = new ModelCatStatueMemorial();
    public static ModelMemorial creeperStatue = new ModelCreeperStatueMemorial();
    public static ModelMemorial gibbet = new ModelGibbet();
    public static ModelMemorial stocks = new ModelStocks();
    public static ModelMemorial burningStake = new ModelBurningStake();

    //private static IModelCustom celticCross = AdvancedModelLoader.loadModel("/assets/gravestone/obj_models/CelticCross.obj");

    private final Map<EnumMemorials, ModelMemorial> MODELS_MAP = ImmutableMap.<EnumMemorials, ModelMemorial>builder()
            .put(EnumMemorials.STONE_CROSS, cross)
            .put(EnumMemorials.QUARTZ_OBELISK, obelisk)
            .put(EnumMemorials.STONE_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.STONE_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.STONE_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.STONE_DOG_STATUE, dogStatue)
            .put(EnumMemorials.STONE_CAT_STATUE, catStatue)
            .put(EnumMemorials.STONE_CREEPER_STATUE, creeperStatue)
                    // crosses
            .put(EnumMemorials.WOODEN_CROSS, cross)
            .put(EnumMemorials.SANDSTONE_CROSS, cross)
            .put(EnumMemorials.MOSSY_CROSS, cross)
            .put(EnumMemorials.IRON_CROSS, cross)
            .put(EnumMemorials.GOLDEN_CROSS, cross)
            .put(EnumMemorials.DIAMOND_CROSS, cross)
            .put(EnumMemorials.EMERALD_CROSS, cross)
            .put(EnumMemorials.LAPIS_CROSS, cross)
            .put(EnumMemorials.REDSTONE_CROSS, cross)
            .put(EnumMemorials.OBSIDIAN_CROSS, cross)
            .put(EnumMemorials.QUARTZ_CROSS, cross)
            .put(EnumMemorials.ICE_CROSS, cross)
                    // obelisks
            .put(EnumMemorials.WOODEN_OBELISK, obelisk)
            .put(EnumMemorials.SANDSTONE_OBELISK, obelisk)
            .put(EnumMemorials.STONE_OBELISK, obelisk)
            .put(EnumMemorials.MOSSY_OBELISK, obelisk)
            .put(EnumMemorials.IRON_OBELISK, obelisk)
            .put(EnumMemorials.GOLDEN_OBELISK, obelisk)
            .put(EnumMemorials.DIAMOND_OBELISK, obelisk)
            .put(EnumMemorials.EMERALD_OBELISK, obelisk)
            .put(EnumMemorials.LAPIS_OBELISK, obelisk)
            .put(EnumMemorials.REDSTONE_OBELISK, obelisk)
            .put(EnumMemorials.OBSIDIAN_OBELISK, obelisk)
            .put(EnumMemorials.ICE_OBELISK, obelisk)
                    // steve memorials
            .put(EnumMemorials.WOODEN_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.SANDSTONE_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.MOSSY_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.IRON_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.GOLDEN_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.DIAMOND_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.EMERALD_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.LAPIS_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.REDSTONE_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.OBSIDIAN_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.QUARTZ_STEVE_STATUE, steveStatue)
            .put(EnumMemorials.ICE_STEVE_STATUE, steveStatue)
                    // villagers memorials
            .put(EnumMemorials.WOODEN_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.SANDSTONE_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.MOSSY_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.IRON_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.GOLDEN_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.DIAMOND_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.EMERALD_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.LAPIS_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.REDSTONE_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.OBSIDIAN_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.QUARTZ_VILLAGER_STATUE, villagerStatue)
            .put(EnumMemorials.ICE_VILLAGER_STATUE, villagerStatue)
                    // angel memorials
            .put(EnumMemorials.WOODEN_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.SANDSTONE_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.MOSSY_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.IRON_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.GOLDEN_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.DIAMOND_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.EMERALD_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.LAPIS_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.REDSTONE_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.OBSIDIAN_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.QUARTZ_ANGEL_STATUE, angelStatue)
            .put(EnumMemorials.ICE_ANGEL_STATUE, angelStatue)
                    // dogs memorial
            .put(EnumMemorials.WOODEN_DOG_STATUE, dogStatue)
            .put(EnumMemorials.SANDSTONE_DOG_STATUE, dogStatue)
            .put(EnumMemorials.MOSSY_DOG_STATUE, dogStatue)
            .put(EnumMemorials.IRON_DOG_STATUE, dogStatue)
            .put(EnumMemorials.GOLDEN_DOG_STATUE, dogStatue)
            .put(EnumMemorials.DIAMOND_DOG_STATUE, dogStatue)
            .put(EnumMemorials.EMERALD_DOG_STATUE, dogStatue)
            .put(EnumMemorials.LAPIS_DOG_STATUE, dogStatue)
            .put(EnumMemorials.REDSTONE_DOG_STATUE, dogStatue)
            .put(EnumMemorials.OBSIDIAN_DOG_STATUE, dogStatue)
            .put(EnumMemorials.QUARTZ_DOG_STATUE, dogStatue)
            .put(EnumMemorials.ICE_DOG_STATUE, dogStatue)
                    // dogs memorial
            .put(EnumMemorials.WOODEN_CAT_STATUE, catStatue)
            .put(EnumMemorials.SANDSTONE_CAT_STATUE, catStatue)
            .put(EnumMemorials.MOSSY_CAT_STATUE, catStatue)
            .put(EnumMemorials.IRON_CAT_STATUE, catStatue)
            .put(EnumMemorials.GOLDEN_CAT_STATUE, catStatue)
            .put(EnumMemorials.DIAMOND_CAT_STATUE, catStatue)
            .put(EnumMemorials.EMERALD_CAT_STATUE, catStatue)
            .put(EnumMemorials.LAPIS_CAT_STATUE, catStatue)
            .put(EnumMemorials.REDSTONE_CAT_STATUE, catStatue)
            .put(EnumMemorials.OBSIDIAN_CAT_STATUE, catStatue)
            .put(EnumMemorials.QUARTZ_CAT_STATUE, catStatue)
            .put(EnumMemorials.ICE_CAT_STATUE, catStatue)
                    // creepers memorial
            .put(EnumMemorials.WOODEN_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.SANDSTONE_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.MOSSY_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.IRON_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.GOLDEN_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.DIAMOND_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.EMERALD_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.LAPIS_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.REDSTONE_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.OBSIDIAN_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.QUARTZ_CREEPER_STATUE, creeperStatue)
            .put(EnumMemorials.ICE_CREEPER_STATUE, creeperStatue)
            // gibbets
            .put(EnumMemorials.GIBBET, gibbet)
            // stocks
            .put(EnumMemorials.STOCKS, stocks)
            // burning stake
            .put(EnumMemorials.BURNING_STAKE, burningStake)
            .build();

    public static TileEntityGSMemorialRenderer instance;

    public TileEntityGSMemorialRenderer() {
        instance = this;
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) te;
        EnumMemorials memorialType = tileEntity.getMemorialType();
        int meta;

        boolean tewo_isNull = tileEntity.getWorldObj() != null;
        if (tewo_isNull) {
            meta = tileEntity.getBlockMetadata();
        } else {
            meta = 0;
        }

        bindTextureByName(memorialType.getTexture());

        //texture
        GL11.glPushMatrix();

        if (tewo_isNull) {
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            GL11.glScalef(1F, -1F, -1F);
        } else {
            if (memorialType==EnumMemorials.WOODEN_CROSS || memorialType==EnumMemorials.SANDSTONE_CROSS || memorialType==EnumMemorials.STONE_CROSS || memorialType==EnumMemorials.MOSSY_CROSS || memorialType==EnumMemorials.IRON_CROSS || memorialType==EnumMemorials.GOLDEN_CROSS || memorialType==EnumMemorials.DIAMOND_CROSS || memorialType==EnumMemorials.EMERALD_CROSS || memorialType==EnumMemorials.LAPIS_CROSS || memorialType==EnumMemorials.REDSTONE_CROSS || memorialType==EnumMemorials.OBSIDIAN_CROSS || memorialType==EnumMemorials.QUARTZ_CROSS || memorialType==EnumMemorials.ICE_CROSS || memorialType==EnumMemorials.WOODEN_OBELISK || memorialType==EnumMemorials.SANDSTONE_OBELISK || memorialType==EnumMemorials.STONE_OBELISK || memorialType==EnumMemorials.MOSSY_OBELISK || memorialType==EnumMemorials.IRON_OBELISK || memorialType==EnumMemorials.GOLDEN_OBELISK || memorialType==EnumMemorials.DIAMOND_OBELISK || memorialType==EnumMemorials.EMERALD_OBELISK || memorialType==EnumMemorials.LAPIS_OBELISK || memorialType==EnumMemorials.REDSTONE_OBELISK || memorialType==EnumMemorials.OBSIDIAN_OBELISK || memorialType==EnumMemorials.QUARTZ_OBELISK || memorialType==EnumMemorials.ICE_OBELISK) {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
                    GL11.glScalef(0.4F, -0.4F, -0.4F);
            } else {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 0.8F, (float) z + 0.5F);
                    GL11.glScalef(0.7F, -0.7F, -0.7F);
            }
        }

        if (meta==0) GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
        else if (meta==3) GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
        else if (meta==2) GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
        else GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
//
//        if (memorialType == 9) {
//            celticCross.renderAll();
//        } else
        ModelMemorial model = MODELS_MAP.get(memorialType);
        model.setPedestalTexture(memorialType.getPedestalTexture());
        if (memorialType==EnumMemorials.WOODEN_CREEPER_STATUE || memorialType==EnumMemorials.SANDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.STONE_CREEPER_STATUE || memorialType==EnumMemorials.MOSSY_CREEPER_STATUE || memorialType==EnumMemorials.IRON_CREEPER_STATUE || memorialType==EnumMemorials.GOLDEN_CREEPER_STATUE || memorialType==EnumMemorials.DIAMOND_CREEPER_STATUE || memorialType==EnumMemorials.EMERALD_CREEPER_STATUE || memorialType==EnumMemorials.LAPIS_CREEPER_STATUE || memorialType==EnumMemorials.REDSTONE_CREEPER_STATUE || memorialType==EnumMemorials.OBSIDIAN_CREEPER_STATUE || memorialType==EnumMemorials.QUARTZ_CREEPER_STATUE || memorialType==EnumMemorials.ICE_CREEPER_STATUE) {
                model.customRender(tileEntity.isEnchanted());
        } else if (memorialType==EnumMemorials.WOODEN_STEVE_STATUE || memorialType==EnumMemorials.SANDSTONE_STEVE_STATUE || memorialType==EnumMemorials.STONE_STEVE_STATUE || memorialType==EnumMemorials.MOSSY_STEVE_STATUE || memorialType==EnumMemorials.IRON_STEVE_STATUE || memorialType==EnumMemorials.GOLDEN_STEVE_STATUE || memorialType==EnumMemorials.DIAMOND_STEVE_STATUE || memorialType==EnumMemorials.EMERALD_STEVE_STATUE || memorialType==EnumMemorials.LAPIS_STEVE_STATUE || memorialType==EnumMemorials.REDSTONE_STEVE_STATUE || memorialType==EnumMemorials.OBSIDIAN_STEVE_STATUE || memorialType==EnumMemorials.QUARTZ_STEVE_STATUE || memorialType==EnumMemorials.ICE_STEVE_STATUE) {
                model.customRender(memorialType, tileEntity.isEnchanted());
        } else if (memorialType==EnumMemorials.GIBBET || memorialType==EnumMemorials.STOCKS || memorialType==EnumMemorials.BURNING_STAKE) {
                model.customRender(memorialType, tileEntity.getHangedMob(), tileEntity.getHangedVillagerProfession());
        } else {
                if (tileEntity.isEnchanted()) {
                    model.renderEnchanted();
                } else {
                    model.renderAll();
                }
        }

        GL11.glPopMatrix();
    }

    /**
     * Return grave direction by metadata
     */
    /*private static int getMemorialDirection(int meta) {
        switch (meta) {
            case 0: // S
                return 0;
            case 1: // N
                return 2;
            case 2: // E
                return 3;
            case 3: // W
                return 1;
            default:
                return 2;
        }
    }*/
}
