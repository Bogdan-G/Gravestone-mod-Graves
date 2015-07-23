package gravestone.block.enums;

import net.minecraft.util.IStringSerializable;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public enum EnumSkullCandle implements IBlockEnum, IStringSerializable {

    SKELETON_SKULL("block.skull_candle.skeleton", "skeleton_skull_candle"),
    WITHER_SKULL("block.skull_candle.wither_skeleton", "wither_skull_candle"),
    ZOMBIE_SKULL("block.skull_candle.zombie", "zombie_skull_candle");
    private String name;
    private String blockModelName;

    private EnumSkullCandle(String name, String blockModelName) {
        this.name = name;
        this.blockModelName = blockModelName;
    }

    @Override
    public String getUnLocalizedName() {
        return this.name;
    }

    @Override
    public String getName() {
        return blockModelName;
    }

    public static EnumSkullCandle getById(int id) {
        if (id < values().length) {
            return values()[id];
        }
        return SKELETON_SKULL;
    }
}
