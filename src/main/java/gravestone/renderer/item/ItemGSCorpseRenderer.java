package gravestone.renderer.item;

//import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.VillagerRegistry;
import gravestone.core.Resources;
import gravestone.item.corpse.CatCorpseHelper;
import gravestone.item.corpse.HorseCorpseHelper;
import gravestone.item.corpse.VillagerCorpseHelper;
import gravestone.item.enums.EnumCorpse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.Map;
import java.util.*;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemGSCorpseRenderer implements IItemRenderer {

    private static final Map horsesTexturesMap = new HashMap();
    private static final ModelVillager villagerModel = new ModelVillager(0);
    private static final ModelWolf dogModel = new ModelWolf();
    private static final ModelOcelot catModel = new ModelOcelot();
    private static final ModelHorse horseModel = new ModelHorse();
    private static EntityWolf dog;
    private static EntityHorse horse;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glRotatef(180, 1, 0, 0);

        byte corpseType = (byte) item.getItemDamage();
        float xz = 0.0625F;
        EnumCorpse corpse = EnumCorpse.getById(corpseType);
        if (corpse==EnumCorpse.VILLAGER) {
                GL11.glTranslatef(0, -0.5F, 0);
                int profession = VillagerCorpseHelper.getVillagerType(item.getTagCompound());
                if (profession==0) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.VILLAGER_FARMER);
                else if (profession==1) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.VILLAGER_LIBRARIAN);
                else if (profession==2) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.VILLAGER_PRIEST);
                else if (profession==3) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.VILLAGER_SMITH);
                else if (profession==4) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.VILLAGER_BUTCHER);
                else Minecraft.getMinecraft().renderEngine.bindTexture(VillagerRegistry.getVillagerSkin(profession, Resources.VILLAGER));
                villagerModel.render(null, xz, xz, xz, xz, xz, xz);
        } else if (corpse==EnumCorpse.DOG) {
                GL11.glTranslatef(0, -1, 0);
                Minecraft.getMinecraft().renderEngine.bindTexture(Resources.WOLF);
                if (dog == null) {
                    dog = new EntityWolf(Minecraft.getMinecraft().theWorld);
                }
                dogModel.setLivingAnimations(dog, 0, 0, 0);
                dogModel.render(dog, xz, xz, xz, xz, xz, xz);
        } else if (corpse==EnumCorpse.CAT) {
                GL11.glTranslatef(0, -1, 0);
                int catType = CatCorpseHelper.getCatType(item.getTagCompound());
                if (catType==1) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.BLACK_CAT);
                else if (catType==2) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.RED_CAT);
                else if (catType==3) Minecraft.getMinecraft().renderEngine.bindTexture(Resources.SIAMESE_CAT);
                else Minecraft.getMinecraft().renderEngine.bindTexture(Resources.OCELOT);//catType==0 include in default switch
                catModel.render(null, xz, xz, xz, xz, xz, xz);
        } else if (corpse==EnumCorpse.HORSE) {
                GL11.glTranslatef(0, -0.6F, 0);
                if (horse == null) {
                    horse = new EntityHorse(Minecraft.getMinecraft().theWorld);
                }
                horse.setHorseType(HorseCorpseHelper.getHorseType(item.getTagCompound()));
                horse.setHorseVariant(HorseCorpseHelper.getHorseVariant(item.getTagCompound()));

                int horseType = HorseCorpseHelper.getHorseType(item.getTagCompound());
                if (horseType==0) {
                        String horseTexturePath = horse.getHorseTexture();
                        ResourceLocation horseResourceLocation = (ResourceLocation) horsesTexturesMap.get(horseTexturePath);
                        if (horseResourceLocation == null) {
                            horseResourceLocation = new ResourceLocation(horseTexturePath);
                            Minecraft.getMinecraft().getTextureManager().loadTexture(horseResourceLocation, new LayeredTexture(horse.getVariantTexturePaths()));
                            horsesTexturesMap.put(horseTexturePath, horseResourceLocation);
                        }
                        Minecraft.getMinecraft().renderEngine.bindTexture(horseResourceLocation);
                } else if (horseType==1) {
                        Minecraft.getMinecraft().renderEngine.bindTexture(Resources.DONKEY);
                } else if (horseType==2) {
                        Minecraft.getMinecraft().renderEngine.bindTexture(Resources.MULE);
                } else if (horseType==3) {
                        Minecraft.getMinecraft().renderEngine.bindTexture(Resources.ZOMBIE_HORSE);
                } else if (horseType==4) {
                        Minecraft.getMinecraft().renderEngine.bindTexture(Resources.SKELETON_HORSE);
                }

                horseModel.setLivingAnimations(horse, 0, 0, 0);
                horseModel.render(horse, xz, xz, xz, xz, xz, xz);
        }
        GL11.glPopMatrix();
    }
}
