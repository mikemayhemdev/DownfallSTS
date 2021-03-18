package saveData;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import downfall.downfallMod;
import downfall.events.Cleric_Evil;
import downfall.monsters.FleeingMerchant;
import downfall.monsters.NeowBossFinal;
import downfall.patches.EvilModeCharacterSelect;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.relics.BrokenWingStatue;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveData {
    public static final String EVIL_MODE_KEY = "EVIL_MODE";
    public static final String RED_KEY_CONSUMED = "RED_KEY_CONSUMED";
    public static final String GREEN_KEY_CONSUMED = "GREEN_KEY_CONSUMED";
    public static final String BLUE_KEY_CONSUMED = "BLUE_KEY_CONSUMED";
    public static final String KILLED_CLERIC = "KILLED_CLERIC";
    public static final String ENCOUNTERED_CLERIC = "ENCOUNTERED_CLERIC";
    public static final String UPCOMING_BOSSES = "UPCOMING_BOSSES";
    public static final String MERCHANT_HEALTH = "MERCHANT_HEALTH";
    public static final String MERCHANT_STRENGTH = "MERCHANT_STRENGTH";
    public static final String MERCHANT_SOULS = "MERCHANT_SOULS";
    public static final String MERCHANT_DEAD = "MERCHANT_DEAD";
    public static final String MERCHANT_ESCAPED = "MERCHANT_ESCAPED";
    public static final String WING_GIVEN = "WING_GIVEN";
    public static final String ACT_1_BOSS_SLAIN = "ACT_1_BOSS_SLAIN";
    public static final String ACT_2_BOSS_SLAIN = "ACT_2_BOSS_SLAIN";
    public static final String ACT_3_BOSS_SLAIN = "ACT_3_BOSS_SLAIN";
    public static final String VALID_COLORS = "VALID_COLORS";

    private static Logger saveLogger = LogManager.getLogger("downfallSaveData");
    //data is stored here in addition to the actual location
    //when data is "saved" it is saved here, and written to the actual save file slightly later
    private static boolean evilMode;

    private static boolean consumedRed;
    private static boolean consumedGreen;
    private static boolean consumedBlue;

    private static boolean killedCleric;
    private static boolean encounteredCleric;

    private static ArrayList<String> myVillains;

    private static int merchantHealth;
    private static int merchantStrength;
    private static int merchantSouls;

    private static boolean merchantDead;
    private static boolean merchantEscaped;

    private static boolean brokenWingGiven;

    private static String act1BossSlain;
    private static String act2BossSlain;
    private static String act3BossSlain;

    private static ArrayList<AbstractCard.CardColor> saveCacheColors;

    //Save data whenever SaveFile is constructed
    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class SaveTheSaveData {
        @SpirePostfixPatch
        public static void saveAllTheSaveData(SaveFile __instance, SaveFile.SaveType type) {
            evilMode = EvilModeCharacterSelect.evilMode;

            consumedRed = AddBustKeyButtonPatches.KeyFields.bustedRuby.get(AbstractDungeon.player);
            consumedGreen = AddBustKeyButtonPatches.KeyFields.bustedEmerald.get(AbstractDungeon.player);
            consumedBlue = AddBustKeyButtonPatches.KeyFields.bustedSapphire.get(AbstractDungeon.player);

            killedCleric = Cleric_Evil.heDead;
            encounteredCleric = Cleric_Evil.encountered;

            myVillains = downfallMod.possEncounterList;

            merchantHealth = FleeingMerchant.CURRENT_HP;
            merchantStrength = FleeingMerchant.CURRENT_STRENGTH;
            merchantSouls = FleeingMerchant.CURRENT_SOULS;

            merchantDead = FleeingMerchant.DEAD;
            merchantEscaped = FleeingMerchant.ESCAPED;

            brokenWingGiven = BrokenWingStatue.GIVEN;

            act1BossSlain = downfallMod.Act1BossFaced;
            act2BossSlain = downfallMod.Act2BossFaced;
            act3BossSlain = downfallMod.Act3BossFaced;
            System.out.println(act1BossSlain);
            System.out.println(act2BossSlain);
            System.out.println(act3BossSlain);

            saveCacheColors = SneckoMod.validColors;

            saveLogger.info("Saved Evil Mode: " + evilMode);
        }
    }

    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "save",
            paramtypez = {SaveFile.class}
    )
    public static class SaveDataToFile {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"params"}
        )
        public static void addCustomSaveData(SaveFile save, HashMap<Object, Object> params) {
            params.put(EVIL_MODE_KEY, evilMode);
            params.put(RED_KEY_CONSUMED, consumedRed);
            params.put(GREEN_KEY_CONSUMED, consumedGreen);
            params.put(BLUE_KEY_CONSUMED, consumedBlue);
            params.put(KILLED_CLERIC, killedCleric);
            params.put(ENCOUNTERED_CLERIC, encounteredCleric);
            params.put(UPCOMING_BOSSES, myVillains);
            params.put(MERCHANT_HEALTH, merchantHealth);
            params.put(MERCHANT_STRENGTH, merchantStrength);
            params.put(MERCHANT_SOULS, merchantSouls);
            params.put(MERCHANT_DEAD, merchantDead);
            params.put(MERCHANT_ESCAPED, merchantEscaped);
            params.put(WING_GIVEN, brokenWingGiven);
            params.put(ACT_1_BOSS_SLAIN, act1BossSlain);
            params.put(ACT_2_BOSS_SLAIN, act2BossSlain);
            params.put(ACT_3_BOSS_SLAIN, act3BossSlain);
            params.put(VALID_COLORS, saveCacheColors);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(GsonBuilder.class, "create");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "loadSaveFile",
            paramtypez = {String.class}
    )
    public static class LoadDataFromFile {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"gson", "savestr"}
        )
        public static void loadCustomSaveData(String path, Gson gson, String savestr) {
            try {
                downfallSaveData data = gson.fromJson(savestr, downfallSaveData.class);

                evilMode = data.EVIL_MODE;

                consumedRed = data.RED_KEY_CONSUMED;
                consumedGreen = data.GREEN_KEY_CONSUMED;
                consumedBlue = data.BLUE_KEY_CONSUMED;

                killedCleric = data.KILLED_CLERIC;
                encounteredCleric = data.ENCOUNTERED_CLERIC;

                myVillains = data.UPCOMING_BOSSES;

                merchantHealth = data.MERCHANT_HEALTH;
                merchantStrength = data.MERCHANT_STRENGTH;
                merchantSouls = data.MERCHANT_SOULS;

                merchantDead = data.MERCHANT_DEAD;
                merchantEscaped = data.MERCHANT_ESCAPED;

                brokenWingGiven = data.WING_GIVEN;

                act1BossSlain = data.ACT_1_BOSS_SLAIN;
                act2BossSlain = data.ACT_2_BOSS_SLAIN;
                act3BossSlain = data.ACT_3_BOSS_SLAIN;

                saveCacheColors = data.VALID_COLORS;

                saveLogger.info("Loaded downfall save data successfully.");
            } catch (Exception e) {
                saveLogger.error("Failed to load downfall save data.");
                e.printStackTrace();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Gson.class, "fromJson");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    //Ensure data is loaded/generated
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "loadSave"
    )
    public static class loadSave {
        @SpirePostfixPatch
        public static void loadSave(AbstractDungeon __instance, SaveFile file) {
            //Some data, after loading into this file, will need to actually be assigned here.
            //When the game loads, the SaveFile is instantiated first, which loads data from save into itself.
            //AbstractDungeon then loads that data from the SaveFile.

            EvilModeCharacterSelect.evilMode = evilMode;

            AddBustKeyButtonPatches.KeyFields.bustedRuby.set(AbstractDungeon.player, consumedRed);
            AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(AbstractDungeon.player, consumedGreen);
            AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(AbstractDungeon.player, consumedBlue);

            Cleric_Evil.heDead = killedCleric;
            Cleric_Evil.encountered = encounteredCleric;

            downfallMod.possEncounterList = myVillains;

            FleeingMerchant.CURRENT_HP = merchantHealth;
            FleeingMerchant.CURRENT_STRENGTH = merchantStrength;
            FleeingMerchant.CURRENT_SOULS = merchantSouls;

            System.out.println(merchantDead);
            FleeingMerchant.DEAD = merchantDead;
            System.out.println(merchantEscaped);
            FleeingMerchant.ESCAPED = merchantEscaped;


            BrokenWingStatue.GIVEN = brokenWingGiven;


            System.out.println(act1BossSlain);
            System.out.println(act2BossSlain);
            System.out.println(act3BossSlain);
            downfallMod.Act1BossFaced = act1BossSlain;
            downfallMod.Act2BossFaced = act2BossSlain;
            downfallMod.Act3BossFaced = act3BossSlain;

            SneckoMod.validColors = saveCacheColors;
            SneckoMod.updateAllUnknownReplacements();

            saveLogger.info("Save loaded.");
            //Anything that triggers on load goes here

            System.out.println(file.room_x);
            if (file.room_x == -2) {
                System.out.println("WE GOT ONE!");
               loadIntoNeowDoubleInstead();
            }
        }
    }

    public static void loadIntoNeowDoubleInstead() {
        AbstractDungeon.bossKey = NeowBossFinal.ID;
        MapRoomNode node = new MapRoomNode(-2, 5);
        node.room = new MonsterRoomBoss();
    }
}
