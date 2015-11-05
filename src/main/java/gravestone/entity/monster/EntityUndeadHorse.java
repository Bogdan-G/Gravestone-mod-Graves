package gravestone.entity.monster;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class EntityUndeadHorse extends EntityHorse {

    protected String texturePrefix;
    protected String variantTexturePaths;
    protected static final String[] horseArmorTextures = new String[]{
            null,
            "textures/entity/horse/armor/horse_armor_iron.png",
            "textures/entity/horse/armor/horse_armor_gold.png",
            "textures/entity/horse/armor/horse_armor_diamond.png"
    };
    protected static final String[] horseArmorPrefix = new String[]{"", "meo", "goo", "dio"};


    protected boolean field_175508_bO = false;

    public static enum EnumHorseType {
        ZOMBIE_HORSE_TYPE(3),
        SKELETON_HORSE_TYPE(4);

        private int id;

        EnumHorseType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public EntityUndeadHorse(World worldIn) {
        super(worldIn);

        Iterator taskIterator = this.tasks.taskEntries.iterator();
        while (taskIterator.hasNext()) {
            boolean removeTask = false;
            EntityAIBase task = ((EntityAITasks.EntityAITaskEntry) taskIterator.next()).action;
            if (task instanceof EntityAIPanic) {
                removeTask = true;
            } else if (task instanceof EntityAIRunAroundLikeCrazy) {
                removeTask = true;
            } else if (task instanceof EntityAIMate) {
                removeTask = true;
            } else if (task instanceof EntityAIFollowParent) {
                removeTask = true;
            }

            if (removeTask) {
                taskIterator.remove();
            }
        }

        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1, false));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (entity instanceof EntityLivingBase) {
            f += EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase) entity).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
            if (i > 0) {
                entity.addVelocity((double) (-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180F) * (float) i * 0.5F), 0.1, (double) (MathHelper.cos(this.rotationYaw * (float) Math.PI / 180F) * (float) i * 0.5F));
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0) {
                entity.setFire(j * 4);
            }

            this.func_174815_a(this, entity);
        }

        return flag;
    }

    @Override
    public boolean isUndead() {
        return true;
    }

    @Override
    public boolean isSterile() {
        return true;
    }

    @Override
    public boolean canWearArmor() {
        return true;
    }

    public boolean hasArmor() {
        return false;//TODO !!!!!!!!!!!!!!!
    }

    @Override
    public boolean allowLeashing() {
        return !this.getLeashed();
    }

    public abstract EnumHorseType getUndeadHorseType();

    public void setHorseType(EnumHorseType horseType) {
        super.setHorseType(horseType.getId());
    }

    @Override
    protected boolean canDespawn() {
        return !this.isTame();
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData livingData) {
        Object entityLivingData = super.func_180482_a(difficulty, livingData);
        int horseType = getUndeadHorseType().getId();

        if (entityLivingData instanceof EntityHorse.GroupData) {
            ((EntityHorse.GroupData) entityLivingData).field_111107_a = horseType;
        } else {
            entityLivingData = new EntityHorse.GroupData(horseType, 0);
        }

        this.setHorseType(horseType);
        return (IEntityLivingData) entityLivingData;
    }

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() == Items.spawn_egg) {
            return super.interact(player);
        } else if (this.isTame() && this.isAdultHorse() && player.isSneaking()) {
            this.openGUI(player);
            return true;
        } else if (this.func_110253_bW() && this.riddenByEntity != null) {
            return super.interact(player);
        } else {
            if (itemstack != null) {
                boolean flag = false;
                if (this.canWearArmor()) {
                    byte armorType = -1;
                    if (itemstack.getItem() == Items.iron_horse_armor) {
                        armorType = 1;
                    } else if (itemstack.getItem() == Items.golden_horse_armor) {
                        armorType = 2;
                    } else if (itemstack.getItem() == Items.diamond_horse_armor) {
                        armorType = 3;
                    }

                    if (armorType >= 0) {
                        if (!this.isTame()) {
                            this.makeHorseRearWithSound();
                            return true;
                        }

                        this.openGUI(player);
                        return true;
                    }
                }

                float healedHealth = 0;
                short growth = 0;
                if (itemstack.getItem() == Items.bone) {
                    healedHealth = 2;
                    growth = 20;
                } else if (itemstack.getItem() == Items.rotten_flesh) {
                    healedHealth = 4;
                    growth = 60;
                }

                if (this.getHealth() < this.getMaxHealth() && healedHealth > 0) {
                    this.heal(healedHealth);
                    flag = true;
                }

                if (!this.isAdultHorse() && growth > 0) {
                    this.addGrowth(growth);
                    flag = true;
                }

                if (flag) {
                    if (!player.capabilities.isCreativeMode && --itemstack.stackSize == 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }

                    return true;
                } else {
                    if (!this.isTame()) {
                        if (itemstack.interactWithEntity(player, this)) {
                            return true;
                        }

                        this.makeHorseRearWithSound();
                        return true;
                    }

                    if (this.func_110253_bW() && !this.isHorseSaddled() && itemstack.getItem() == Items.saddle) {
                        this.openGUI(player);
                        return true;
                    }
                }
            }

            if (this.func_110253_bW() && this.riddenByEntity == null) {
                if (itemstack != null && itemstack.interactWithEntity(player, this)) {
                    return true;
                } else if (this.isTame()) {
                    this.mountHorse(player);
                    return true;
                }
            } else {
                return super.interact(player);
            }
        }
        return false;
    }

    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
            float brightness = this.getBrightness(1);
            BlockPos blockpos = new BlockPos(this.posX, (double) Math.round(this.posY), this.posZ);
            if (brightness > 0.5 && this.rand.nextFloat() * 30 < (brightness - 0.4) * 2 && this.worldObj.canSeeSky(blockpos) && !this.hasArmor()) {
                this.setFire(8);
            }
        }

        super.onLivingUpdate();
    }

    protected void mountHorse(EntityPlayer player) {
        player.rotationYaw = this.rotationYaw;
        player.rotationPitch = this.rotationPitch;
        this.setEatingHaystack(false);
        this.setRearing(false);
        if (!this.worldObj.isRemote) {
            player.mountEntity(this);
        }
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.riddenByEntity != null && this.isHorseSaddled() ? true : this.isEatingHaystack() || this.isRearing();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean func_175507_cI() {
        return this.field_175508_bO;
    }

    @SideOnly(Side.CLIENT)
    private void setHorseTexturePaths() {
        this.texturePrefix = "horse/";
        int horseType = this.getHorseType();

        this.texturePrefix = this.texturePrefix + horseType + "_";
        int horseArmorTextureNum = this.func_110241_cb();

        if (horseArmorTextureNum >= horseArmorTextures.length) {
            this.field_175508_bO = false;
        } else {
            this.variantTexturePaths = horseArmorTextures[horseArmorTextureNum];
            this.texturePrefix = this.texturePrefix + horseArmorPrefix[horseArmorTextureNum];
            this.field_175508_bO = true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getHorseTexture() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.texturePrefix;
    }

    @SideOnly(Side.CLIENT)
    public String getArmorTexturePaths() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.variantTexturePaths;
    }

    @Override
    public void setHorseType(int horseType) {
        this.dataWatcher.updateObject(19, (byte) horseType);
        this.resetTexturePrefix();
    }

    @Override
    public void setHorseVariant(int horseVariant) {
        this.dataWatcher.updateObject(20, horseVariant);
        this.resetTexturePrefix();
    }

    @Override
    public void setHorseArmorStack(ItemStack itemStack) {
        this.dataWatcher.updateObject(22, this.getHorseArmorIndex(itemStack));
        this.resetTexturePrefix();
    }

    protected void resetTexturePrefix() {
        this.texturePrefix = null;
    }

    public int getHorseArmorIndex(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        } else {
            Item item = itemStack.getItem();
            return item == Items.iron_horse_armor ? 1 : (item == Items.golden_horse_armor ? 2 : (item == Items.diamond_horse_armor ? 3 : 0));
        }
    }

    @Override
    public void onUpdate() {
        if (this.worldObj.isRemote && this.dataWatcher.hasObjectChanged()) {
            this.resetTexturePrefix();
        }
        super.onUpdate();
    }
}
