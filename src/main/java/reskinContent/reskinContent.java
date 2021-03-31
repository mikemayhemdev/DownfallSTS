package reskinContent;

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reskinContent.helpers.AssetLoader;
import reskinContent.skinCharacter.AbstractSkinCharacter;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static reskinContent.patches.CharacterSelectScreenPatches.characters;

@SpireInitializer
public class reskinContent implements
        EditStringsSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber {

    public static String MOD_ID = "reskinContent";

    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String assetPath(String path) {
        return MOD_ID + "/" + path;
    }

    public static final String MODNAME = "reskinContent";
    public static final String AUTHOR = "Rita";
    public static final String DESCRIPTION = "";
    //设置用变量


    public static boolean hexaghostMask = false;
    public static boolean unlockAllReskin = false;

    public static Properties reskinContentDefaults = new Properties();

    public static boolean foundmod_GuardianMod = false;
    public static final Logger logger = LogManager.getLogger(reskinContent.class.getSimpleName());


    public static AssetLoader assets = new AssetLoader();

    @SuppressWarnings("unused")
    public static void initialize() {
        new reskinContent();

        foundmod_GuardianMod = Loader.isModLoaded("Guardian");
        if (foundmod_GuardianMod) {
            logger.info("==========================守护者mod存在=============================");
        }

    }

    public reskinContent() {
        BaseMod.subscribe(this);
    }


    public static String getLanguageString() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
//            case ZHT:
//                language = "zht";
//                break;
//            case KOR:
//                language = "kor";
//                break;
//            case JPN:
//                language = "jpn";
//                break;
            default:
                language = "eng";
        }
        return language;
    }

    @Override
    public void receiveEditStrings() {
        String language;
        language = getLanguageString();
        String uiStrings = Gdx.files.internal("reskinContent/localization/" + language + "/UIStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }

    @Override
    public void receivePostInitialize() {
        loadSettings();
        //Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        //ModPanel settingsPanel = new ModPanel();
        //BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

    }

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("reskinContent", "reskinContentSaveData", reskinContentDefaults);

            config.setBool(CardCrawlGame.saveSlot + "hexaghostMask", hexaghostMask);

            for (int i = 0; i <= characters.length - 1; i++) {
                config.setBool(CardCrawlGame.saveSlot + "ReskinUnlock" + i, characters[i].reskinUnlock);
                config.setInt(CardCrawlGame.saveSlot + "reskinCount" + i, characters[i].reskinCount);
                for (int k = 0; k <= characters[i].skins.length - 1; k++)
                    config.setInt(CardCrawlGame.saveSlot + "portraitAnimationType" + i + "_" + k, characters[i].skins[k].portraitAnimationType);
            }


            System.out.println("==============reskin存入数据");
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("reskinContent", "reskinContentSaveData", reskinContentDefaults);
            config.load();
            System.out.println("==============reskin载入数据");

            hexaghostMask = config.getBool(CardCrawlGame.saveSlot + "hexaghostMask");

            for (int i = 0; i <= characters.length - 1; i++) {
                characters[i].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "ReskinUnlock" + i);
                characters[i].reskinCount = config.getInt(CardCrawlGame.saveSlot + "reskinCount" + i);
                for (int k = 0; k <= characters[i].skins.length - 1; k++) {
                    characters[i].skins[k].portraitAnimationType = config.getInt(CardCrawlGame.saveSlot + "portraitAnimationType" + i + "_" + k);

                    if (characters[i].skins[k].portraitAnimationType > 2 || characters[i].skins[k].portraitAnimationType < 0)
                        characters[i].skins[k].portraitAnimationType = 2;

                    if (!characters[i].skins[k].hasAnimation())
                        characters[i].skins[k].portraitAnimationType = 0;
                }

                if (characters[i].reskinCount > characters[i].skins.length -1) {
                    characters[i].reskinCount = 0;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
        saveSettings();
    }

    public static void unlockAllReskin() {

        for (AbstractSkinCharacter c : characters) {
            c.reskinUnlock = true;
        }
        saveSettings();
    }

    @Override
    public void receiveStartGame() {

    }
}
