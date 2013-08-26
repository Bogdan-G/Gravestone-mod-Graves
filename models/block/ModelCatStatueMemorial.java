package GraveStone.models.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@SideOnly(Side.CLIENT)
public class ModelCatStatueMemorial extends ModelGraveStone {

    /**
     * The back left leg model for the Ocelot.
     */
    ModelRenderer ocelotBackLeftLeg;
    /**
     * The back right leg model for the Ocelot.
     */
    ModelRenderer ocelotBackRightLeg;
    /**
     * The front left leg model for the Ocelot.
     */
    ModelRenderer ocelotFrontLeftLeg;
    /**
     * The front right leg model for the Ocelot.
     */
    ModelRenderer ocelotFrontRightLeg;
    /**
     * The head model for the Ocelot.
     */
    ModelRenderer ocelotHead;
    /**
     * The body model for the Ocelot.
     */
    ModelRenderer ocelotBody;
    /**
     * The tail model for the Ocelot.
     */
    ModelRenderer ocelotTail;
    /**
     * The second part of tail model for the Ocelot.
     */
    ModelRenderer ocelotTail2;
    ModelRenderer Pedestal1;
    ModelRenderer Pedestal2;
    ModelRenderer Pedestal3;
    ModelRenderer Pedestal4;
    ModelRenderer Pedestal5;
    ModelRenderer Sign;

    public ModelCatStatueMemorial() {
        textureWidth = 64;
        textureHeight = 64;
        this.setTextureOffset("head.main", 0, 0);
        this.setTextureOffset("head.nose", 0, 24);
        this.setTextureOffset("head.ear1", 0, 10);
        this.setTextureOffset("head.ear2", 6, 10);
        this.ocelotHead = new ModelRenderer(this, "head");
        this.ocelotHead.addBox("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
        this.ocelotHead.addBox("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
        this.ocelotHead.addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
        this.ocelotHead.addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
        this.ocelotHead.setRotationPoint(0.0F, 15.0F, -9.0F);
        this.ocelotBody = new ModelRenderer(this, 20, 0);
        this.ocelotBody.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
        this.ocelotBody.setRotationPoint(0.0F, 12.0F, -10.0F);
        this.ocelotTail = new ModelRenderer(this, 0, 15);
        this.ocelotTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
        this.ocelotTail.rotateAngleX = 0.9F;
        this.ocelotTail.setRotationPoint(0.0F, 15.0F, 8.0F);
        this.ocelotTail2 = new ModelRenderer(this, 4, 15);
        this.ocelotTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
        this.ocelotTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
        this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
        this.ocelotBackLeftLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
        this.ocelotBackLeftLeg.setRotationPoint(1.1F, 18.0F, 5.0F);
        this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
        this.ocelotBackRightLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
        this.ocelotBackRightLeg.setRotationPoint(-1.1F, 18.0F, 5.0F);
        this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
        this.ocelotFrontLeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
        this.ocelotFrontLeftLeg.setRotationPoint(1.2F, 13.8F, -5.0F);
        this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
        this.ocelotFrontRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
        this.ocelotFrontRightLeg.setRotationPoint(-1.2F, 13.8F, -5.0F);
        Pedestal1 = new ModelRenderer(this, 0, 32);
        Pedestal1.addBox(0F, 0F, 0F, 16, 1, 16);
        Pedestal1.setRotationPoint(-8F, 23F, -8F);
        Pedestal1.setTextureSize(64, 64);
        Pedestal1.mirror = true;
        setRotation(Pedestal1, 0F, 0F, 0F);
        Pedestal2 = new ModelRenderer(this, 0, 32);
        Pedestal2.addBox(0F, 0F, 0F, 16, 1, 16);
        Pedestal2.setRotationPoint(-8F, 8F, -8F);
        Pedestal2.setTextureSize(64, 64);
        Pedestal2.mirror = true;
        setRotation(Pedestal2, 0F, 0F, 0F);
        Pedestal3 = new ModelRenderer(this, 0, 32);
        Pedestal3.addBox(0F, 0F, 0F, 14, 1, 14);
        Pedestal3.setRotationPoint(-7F, 22F, -7F);
        Pedestal3.setTextureSize(64, 64);
        Pedestal3.mirror = true;
        setRotation(Pedestal3, 0F, 0F, 0F);
        Pedestal4 = new ModelRenderer(this, 0, 32);
        Pedestal4.addBox(0F, 0F, 0F, 14, 1, 14);
        Pedestal4.setRotationPoint(-7F, 9F, -7F);
        Pedestal4.setTextureSize(64, 64);
        Pedestal4.mirror = true;
        setRotation(Pedestal4, 0F, 0F, 0F);
        Pedestal5 = new ModelRenderer(this, 0, 32);
        Pedestal5.addBox(0F, 0F, 0F, 12, 12, 12);
        Pedestal5.setRotationPoint(-6F, 10F, -6F);
        Pedestal5.setTextureSize(64, 64);
        Pedestal5.mirror = true;
        setRotation(Pedestal5, 0F, 0F, 0F);
        Sign = new ModelRenderer(this, 0, 56);
        Sign.addBox(0F, 0F, 0F, 10, 5, 1);
        Sign.setRotationPoint(-5F, 13F, -6.5F);
        Sign.setTextureSize(64, 64);
        Sign.mirror = true;
        setRotation(Sign, 0F, 0F, 0F);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are
     * used for animating the movement of arms and legs, where par1 represents
     * the time(so that arms and legs swing back and forth) and par2 represents
     * how "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float par1, float par2, float par4, float par5) {
        this.ocelotHead.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.ocelotHead.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.ocelotBody.rotateAngleX = ((float) Math.PI / 4F);
        this.ocelotBody.rotationPointY = 8F;
        this.ocelotBody.rotationPointZ = -5F;
        this.ocelotHead.rotationPointY = 11.7F;
        this.ocelotHead.rotationPointZ = -8F;
        this.ocelotTail.rotationPointY = 23F;
        this.ocelotTail.rotationPointZ = 6F;
        this.ocelotTail2.rotationPointY = 22F;
        this.ocelotTail2.rotationPointZ = 13.2F;
        this.ocelotTail.rotateAngleX = 1.7278761F;
        this.ocelotTail2.rotateAngleX = 2.670354F;
        this.ocelotFrontLeftLeg.rotateAngleX = this.ocelotFrontRightLeg.rotateAngleX = -0.15707964F;
        this.ocelotFrontLeftLeg.rotationPointY = this.ocelotFrontRightLeg.rotationPointY = 15.8F;
        this.ocelotFrontLeftLeg.rotationPointZ = this.ocelotFrontRightLeg.rotationPointZ = -7.0F;
        this.ocelotBackLeftLeg.rotateAngleX = this.ocelotBackRightLeg.rotateAngleX = -((float) Math.PI / 2F);
        this.ocelotBackLeftLeg.rotationPointY = this.ocelotBackRightLeg.rotationPointY = 21.0F;
        this.ocelotBackLeftLeg.rotationPointZ = this.ocelotBackRightLeg.rotationPointZ = 1.0F;
    }

    @Override
    public void renderAll() {
        this.setRotationAngles(0.0625F, 0.0625F, 0.0625F, 0.0625F);
        float par7 = 0.0625F;
        Pedestal1.render(par7);
        Pedestal2.render(par7);
        Pedestal3.render(par7);
        Pedestal4.render(par7);
        Pedestal5.render(par7);
        Sign.render(par7);
        GL11.glTranslated(0, -1, 0.1);
        this.ocelotHead.render(par7);
        this.ocelotBody.render(par7);
        this.ocelotBackLeftLeg.render(par7);
        this.ocelotBackRightLeg.render(par7);
        this.ocelotFrontLeftLeg.render(par7);
        this.ocelotFrontRightLeg.render(par7);
        this.ocelotTail.render(par7);
        this.ocelotTail2.render(par7);
    }
}
