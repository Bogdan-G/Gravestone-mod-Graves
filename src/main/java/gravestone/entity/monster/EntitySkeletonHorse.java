package gravestone.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class EntitySkeletonHorse extends EntityUndeadHorse {

    public EntitySkeletonHorse(World worldIn) {
        super(worldIn);
    }

    @Override
    public EnumHorseType getUndeadHorseType() {
        return EnumHorseType.SKELETON_HORSE_TYPE;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3);
    }
}
