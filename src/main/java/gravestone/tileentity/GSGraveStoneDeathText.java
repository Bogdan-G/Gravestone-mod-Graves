package gravestone.tileentity;

import gravestone.block.GraveStoneHelper;
import gravestone.block.enums.EnumGraves;
import gravestone.block.enums.EnumMemorials;
import gravestone.config.GraveStoneConfig;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Random;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSGraveStoneDeathText {

    // grave text
    private String name = "";
    private String deathText = "";
    private String killerName = "";
    private boolean isLocalized = false;
    private TileEntityGSGrave tileEntity;

    public GSGraveStoneDeathText(TileEntityGSGrave tileEntity) {
        this.tileEntity = tileEntity;
    }

    public void readText(NBTTagCompound nbtTag) {
        if (nbtTag.hasKey("isLocalized")) {
            isLocalized = nbtTag.getBoolean("isLocalized");
        }

        if (isLocalized) {
            name = nbtTag.getString("name");
            deathText = nbtTag.getString("DeathText");
            killerName = nbtTag.getString("KillerName");
        } else {
            deathText = nbtTag.getString("DeathText");
        }
    }

    public void saveText(NBTTagCompound nbtTag) {
        if (isLocalized) {
            nbtTag.setString("name", name);
            nbtTag.setString("DeathText", deathText);
            nbtTag.setString("KillerName", killerName);
        } else {
            nbtTag.setString("DeathText", deathText);
        }

        nbtTag.setBoolean("isLocalized", isLocalized);
    }

    public boolean isLocalized() {
        return isLocalized;
    }

    public void setLocalized() {
        isLocalized = true;
    }

    public String getName() {
        return name;
    }

    public String getDeathText() {
        return deathText;
    }

    public String getKillerName() {
        return killerName;
    }

    public void setName(String name) {
        this.name = (name == null) ? "" : name;
    }

    public void setDeathText(String text) {
        deathText = (text == null) ? "" : text;
    }

    public void setKillerName(String name) {
        killerName = (name == null) ? "" : name;
    }

    public void setRandomDeathTextAndName(Random random, byte grave, boolean isMemorial, boolean changeGraveType) {
        isLocalized = true;
        EnumGraves graveType = EnumGraves.getByID(grave);
        EnumMemorials memorialType = EnumMemorials.getByID(grave);

        if (isMemorial) {
            if (memorialType==EnumMemorials.STONE_DOG_STATUE) {
                    getRandomMemorialContent(random, GraveStoneConfig.graveDogsNames, GraveStoneConfig.dogsMemorialText);
            } else if (memorialType==EnumMemorials.STONE_CAT_STATUE) {
                    getRandomMemorialContent(random, GraveStoneConfig.graveCatsNames, GraveStoneConfig.catsMemorialText);
            } else if (memorialType==EnumMemorials.STONE_CREEPER_STATUE) {
                    deathText = "Sssssssssssssss...";
            } else {
                    getRandomMemorialContent(random, GraveStoneConfig.graveNames, GraveStoneConfig.memorialText);
            }
        } else {
            if (getDeathMessage(random)) {
                byte newGraveType;
                if (graveType==EnumGraves.WOODEN_DOG_STATUE || graveType==EnumGraves.SANDSTONE_DOG_STATUE || graveType==EnumGraves.STONE_DOG_STATUE || graveType==EnumGraves.MOSSY_DOG_STATUE || graveType==EnumGraves.IRON_DOG_STATUE || graveType==EnumGraves.GOLDEN_DOG_STATUE || graveType==EnumGraves.DIAMOND_DOG_STATUE || graveType==EnumGraves.EMERALD_DOG_STATUE || graveType==EnumGraves.LAPIS_DOG_STATUE || graveType==EnumGraves.REDSTONE_DOG_STATUE || graveType==EnumGraves.OBSIDIAN_DOG_STATUE || graveType==EnumGraves.QUARTZ_DOG_STATUE || graveType==EnumGraves.ICE_DOG_STATUE) {
                        name = this.getValue(random, GraveStoneConfig.graveDogsNames);
                        if (changeGraveType) {
                            newGraveType = GraveStoneHelper.getRandomGrave(GraveStoneHelper.getDogGraveForDeath(null, deathText), random);
                            if (newGraveType != 0) {
                                tileEntity.setGraveType(newGraveType);
                            }
                        }
                } else if (graveType==EnumGraves.WOODEN_CAT_STATUE || graveType==EnumGraves.SANDSTONE_CAT_STATUE || graveType==EnumGraves.STONE_CAT_STATUE || graveType==EnumGraves.MOSSY_CAT_STATUE || graveType==EnumGraves.IRON_CAT_STATUE || graveType==EnumGraves.GOLDEN_CAT_STATUE || graveType==EnumGraves.DIAMOND_CAT_STATUE || graveType==EnumGraves.EMERALD_CAT_STATUE || graveType==EnumGraves.LAPIS_CAT_STATUE || graveType==EnumGraves.REDSTONE_CAT_STATUE || graveType==EnumGraves.OBSIDIAN_CAT_STATUE || graveType==EnumGraves.QUARTZ_CAT_STATUE || graveType==EnumGraves.ICE_CAT_STATUE) {
                        name = this.getValue(random, GraveStoneConfig.graveCatsNames);
                        if (changeGraveType) {
                            newGraveType = GraveStoneHelper.getRandomGrave(GraveStoneHelper.getCatGraveForDeath(null, deathText), random);
                            if (newGraveType != 0) {
                                tileEntity.setGraveType(newGraveType);
                            }
                        }
                } else {
                        name = this.getValue(random, GraveStoneConfig.graveNames);
                        if (changeGraveType) {
                            newGraveType = GraveStoneHelper.getRandomGrave(GraveStoneHelper.getPlayerGraveForDeath(null, deathText), random);
                            if (newGraveType != 0) {
                                tileEntity.setGraveType(newGraveType);
                            }
                        }
                }
            }
        }
        if (changeGraveType) {
            tileEntity.setEnchanted(GraveStoneHelper.isMagicDamage(null, deathText));
        }
    }

    /**
     * Get memorial epitaph or death message for memorial block
     */
    private void getRandomMemorialContent(Random random, ArrayList<String> nameList, ArrayList<String> textList) {
        //if (random.nextInt(5) > 2) {
        //    deathText = this.getValue(random, textList);
        //} else {
            if (getDeathMessage(random)) {
                name = this.getValue(random, nameList);
            }
        //}
    }

    /**
     * Get death message
     */
    private boolean getDeathMessage(Random random) {
        DeathMessageInfo deathMessageInfo = DeathMessageInfo.getRandomDeathMessage(random);
        deathText = deathMessageInfo.getDeathMessage();
        killerName = deathMessageInfo.getKillerNameForTE();
        if (deathMessageInfo.getName().length() != 0) {
            name = deathMessageInfo.getName();
            return false;
        }
        return true;
    }

    private String getValue(Random random, ArrayList<String> list) {
        if (list != null && list.size() > 0) {
            return list.get(random.nextInt(list.size()));
        } else {
            return "";
        }
    }
}
