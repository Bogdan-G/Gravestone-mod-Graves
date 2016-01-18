package gravestone.config;

import gravestone.helper.GraveStoneHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GSConfig {

    private static Configuration config;
    private static GSConfig instance;
    private static String path;
    // CATEGORIES
    public static final String CATEGORY_COMPATIBILITY = "compatibility";
    public static final String CATEGORY_GRAVES = "graves";
    // renderer Id
    //TODO
//    public static int graveRenderID = RenderingRegistry.getNextAvailableRenderId();
//    public static int memorialRenderID = RenderingRegistry.getNextAvailableRenderId();
//    public static int spawnerRenderID = RenderingRegistry.getNextAvailableRenderId();
//    public static int skullCandleRenderID = RenderingRegistry.getNextAvailableRenderId();
//    public static int candleRenderID = RenderingRegistry.getNextAvailableRenderId();
//    public static int pileOfBonesRenderID = RenderingRegistry.getNextAvailableRenderId();

    private GSConfig(String path, File configFile) {
        this.config = new Configuration(configFile);
        this.path = path;
        getConfigs();
    }

    public static GSConfig getInstance(String path, String configFile) {
        if (instance == null) {
            return new GSConfig(path, new File(path + configFile));
        } else {
            return instance;
        }
    }

    public final void getConfigs() {
        config.load();
        gravesConfig();
        compatibilityConfigs();
        config.save();
        getGravesText();
    }



    // graves for entities
    public static boolean generatePlayerGraves;
    public static boolean generateVillagerGraves;
    public static boolean generatePetGraves;
    public static boolean generateGravesInLava;
    public static int graveItemsCount;
    public static int graveSpawnRate;
    public static boolean canPlaceGravesEveryWhere;
    public static boolean spawnMobsByGraves;
    public static boolean spawnMobAtGraveDestruction;
    public static boolean isFogEnabled;
    public static boolean generateSwordGraves;
    public static int spawnChance;
    public static boolean removeEmptyGraves;
    public static boolean showGravesRemovingMessages;
    public static boolean onlyOwnerCanLootGrave;

    public static List<GraveStoneHelper.RestrictedArea> restrictGraveGenerationInArea;

    private static void gravesConfig() {
        canPlaceGravesEveryWhere = config.get(CATEGORY_GRAVES, "AllowToPlaceGravesEveryWhere", false).getBoolean(false);
        generatePlayerGraves = config.get(CATEGORY_GRAVES, "GeneratePlayersGraves", true).getBoolean(true);
        generateVillagerGraves = config.get(CATEGORY_GRAVES, "GenerateVillagersGraves", true).getBoolean(true);
        generatePetGraves = config.get(CATEGORY_GRAVES, "GeneratePetsGraves", true).getBoolean(true);
        generateGravesInLava = config.get(CATEGORY_GRAVES, "GenerateGravesInLava", true).getBoolean(true);
        generateSwordGraves = config.get(CATEGORY_GRAVES, "GenerateSwordGraves", true).getBoolean(true);
        onlyOwnerCanLootGrave = config.get(CATEGORY_GRAVES, "OnlyOwnerCanLootGrave", false).getBoolean(false);


        // store items
        Property graveItemsCountProperty = config.get(CATEGORY_GRAVES, "SavedItemsCount", 40);
        graveItemsCountProperty.comment = "This value must be between 0 an 40(in this case all items will be stored)!";
        graveItemsCount = graveItemsCountProperty.getInt();

        if (graveItemsCount > 40 || graveItemsCount < 0) {
            graveItemsCount = 40;
        }

        // spawn rate
        Property graveSpawnRateProperty = config.get(CATEGORY_GRAVES, "GravesMobsSpawnRate", 1000);
        graveSpawnRateProperty.comment = "This value must be bigger than 600!";
        graveSpawnRate = graveSpawnRateProperty.getInt();

        if (graveSpawnRate < 600) {
            graveSpawnRate = 600;
        }

        spawnMobsByGraves = config.get(CATEGORY_GRAVES, "SpawnMobsByGraves", true).getBoolean(true);
        spawnMobAtGraveDestruction = config.get(CATEGORY_GRAVES, "SpawnMobAtGraveDestruction", true).getBoolean(true);
        spawnChance = config.get(CATEGORY_GRAVES, "GravesMobsSpawnChance", 80).getInt();

        removeEmptyGraves = config.get(CATEGORY_GRAVES, "RemoveEmptyGraves", false).getBoolean(false);
        showGravesRemovingMessages = config.get(CATEGORY_GRAVES, "ShowGravesRemovingMessages", true).getBoolean(true);

        isFogEnabled = config.get(CATEGORY_GRAVES, "CemeteryFogEnabled", true).getBoolean(true);


        Property restrictGraveGenerationInAreaProperty = config.get(CATEGORY_GRAVES, "RestrictGraveGenerationInArea", "");
        restrictGraveGenerationInAreaProperty.comment = "List of coordinates in which graves generation must be disabled. \"start_x,start_y,start_z,end_x,end_y,end_z;\"";
        String ar = restrictGraveGenerationInAreaProperty.getString();
        String[] areas = ar.split(";");
        restrictGraveGenerationInArea = new ArrayList<GraveStoneHelper.RestrictedArea>(areas.length);
        for (String area : areas) {
            GraveStoneHelper.RestrictedArea restrictedArea = GraveStoneHelper.RestrictedArea.getFromString(area);
            if (restrictedArea != null) {
                restrictGraveGenerationInArea.add(restrictedArea);
            }
        }
    }



    // COMPATIBILITY
    public static boolean storeBattlegearItems;
    public static boolean storeTheCampingModItems;
    public static boolean storeBaublesItems;
    public static boolean storeMaricultureItems;
    public static boolean storeTinkerConstructItems;
    public static boolean storeRpgInventoryItems;
    public static boolean storeGalacticraftItems;
    public static boolean storeBackpacksItems;
    public static boolean enableArsMagicaSoulbound;
    public static boolean enableEnderIOSoulbound;
    public static boolean enableTwilightForestKeeping;

    private static void compatibilityConfigs() {

        storeBattlegearItems = config.get(CATEGORY_COMPATIBILITY, "StoreBattlegearItems", true).getBoolean(true);
        storeTheCampingModItems = config.get(CATEGORY_COMPATIBILITY, "StoreTheCampingModItems", true).getBoolean(true);
        storeBaublesItems = config.get(CATEGORY_COMPATIBILITY, "StoreBaublesItems", true).getBoolean(true);
        storeMaricultureItems = config.get(CATEGORY_COMPATIBILITY, "StoreMaricultureItems", true).getBoolean(true);
        storeTinkerConstructItems = config.get(CATEGORY_COMPATIBILITY, "StoreTinkerConstructItems", true).getBoolean(true);
        storeRpgInventoryItems = config.get(CATEGORY_COMPATIBILITY, "StoreRpgInventoryItems", true).getBoolean(true);
        storeGalacticraftItems = config.get(CATEGORY_COMPATIBILITY, "StoreGalacticraftItems", true).getBoolean(true);
        storeBackpacksItems = config.get(CATEGORY_COMPATIBILITY, "StoreBackpacksItems", true).getBoolean(true);

        enableArsMagicaSoulbound = config.get(CATEGORY_COMPATIBILITY, "EnableArsMagicaSoulbound", true).getBoolean(true);
        enableEnderIOSoulbound = config.get(CATEGORY_COMPATIBILITY, "EnableEnderIOSoulbound", true).getBoolean(true);
        enableTwilightForestKeeping = config.get(CATEGORY_COMPATIBILITY, "EnableTwilightForestCharmsOfKeeping", true).getBoolean(true);
    }

    // grave names
    public static ArrayList<String> graveNames;
    public static ArrayList<String> graveDogsNames;
    public static ArrayList<String> graveCatsNames;
    public static ArrayList<String> graveDeathMessages;
    public static ArrayList<String> memorialText;
    public static ArrayList<String> dogsMemorialText;
    public static ArrayList<String> catsMemorialText;

    private void getGravesText() {
        graveNames = readStringsFromFile(path + "graveNames.txt", GravesDefaultText.NAMES);
        graveDogsNames = readStringsFromFile(path + "graveDogsNames.txt", GravesDefaultText.DOG_NAMES);
        graveCatsNames = readStringsFromFile(path + "graveCatsNames.txt", GravesDefaultText.CAT_NAMES);
        graveDeathMessages = readStringsFromFile(path + "graveDeathMessages.txt", GravesDefaultText.DEATH_TEXT);
        memorialText = readStringsFromFile(path + "memorialText.txt", GravesDefaultText.MEMORIAL_TEXT);
        dogsMemorialText = readStringsFromFile(path + "dogsMemorialText.txt", GravesDefaultText.DOGS_MEMORIAL_TEXT);
        catsMemorialText = readStringsFromFile(path + "catsMemorialText.txt", GravesDefaultText.CATS_MEMORIAL_TEXT);
    }

    /*
     * Read text from file if it exist or get default text
     */
    private static ArrayList<String> readStringsFromFile(String fileName, String[] defaultValues) {
        ArrayList<String> list = new ArrayList();
        /*boolean exception = false;
         File file = new File(fileName);

         if (file.exists() && file.canRead()) {
         try {
         BufferedReader reader = new BufferedReader(new FileReader(file));
         String line;

         while ((line = reader.readLine()) != null) {
         list.add(line);
         }

         reader.close();
         } catch (IOException e) {
         exception = true;
         e.printStackTrace();
         }
         } else {
         try {
         file.createNewFile();
         } catch (IOException e) {
         e.printStackTrace();
         }
         }

         if (list.isEmpty() || exception) {
         list = new ArrayList();
         list.addAll(Arrays.asList(defaultValues));

         if (file.canWrite()) {
         try {
         BufferedWriter writer = new BufferedWriter(new FileWriter(file));

         for (int i = 0; i < list.size(); i++) {
         writer.write(list.get(i));
         writer.newLine();
         }

         writer.close();
         } catch (IOException e) {
         e.printStackTrace();
         }
         }
         }
         */
        list.addAll(Arrays.asList(defaultValues));

        return list;
    }
}