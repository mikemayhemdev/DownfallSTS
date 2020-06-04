package reskinContent;

import reskinContent.helpers.AssetLoader;
import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.city.Snecko;
import com.megacrit.cardcrawl.monsters.exordium.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Properties;


@SpireInitializer
public class reskinContent implements
        EditStringsSubscriber,
        PostInitializeSubscriber,
         StartGameSubscriber
{

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
    public static boolean guardianOriginalAnimation = true;
    public static boolean slimeOriginalAnimation = true;
    public static boolean hexaghostOriginalAnimation = true;
    public static boolean sneckoOriginalAnimation = true;

    public static boolean hexaghostMask = false;

    public static int portraitAnimationType = 2;

    public static Properties reskinContentDefaults = new Properties();

    public static boolean foundmod_GuardianMod = false;
    public static final Logger logger = LogManager.getLogger(reskinContent.class.getSimpleName());


    public static AssetLoader assets = new AssetLoader();

    @SuppressWarnings("unused")
    public static void initialize() {   new reskinContent();

        foundmod_GuardianMod = Loader.isModLoaded("Guardian");
        if(foundmod_GuardianMod){logger.info("==========================守护者mod存在=============================");}

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
            case ZHT:
                language = "zht";
                break;
            case KOR:
                language = "kor";
                break;
            case JPN:
                language = "jpn";
                break;
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
        Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

    }

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("reskinContentFix", "settings",reskinContentDefaults);
            config.setBool("guardianOriginalAnimation", guardianOriginalAnimation);
            config.setBool("slimeOriginalAnimation", slimeOriginalAnimation);
            config.setBool("hexaghostOriginalAnimation", hexaghostOriginalAnimation);
            config.setBool("sneckoOriginalAnimation", sneckoOriginalAnimation);
            config.setBool("hexaghostMask", hexaghostMask);


            config.setInt("portraitAnimationType", portraitAnimationType);

            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("reskinContentFix", "settings",reskinContentDefaults);
            config.load();
            guardianOriginalAnimation = config.getBool("guardianOriginalAnimation");
            slimeOriginalAnimation = config.getBool("slimeOriginalAnimation");
            hexaghostOriginalAnimation = config.getBool("hexaghostOriginalAnimation");
            sneckoOriginalAnimation = config.getBool("sneckoOriginalAnimation");
            hexaghostMask = config.getBool("hexaghostMask");

            portraitAnimationType = config.getInt("portraitAnimationType");

        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
        saveSettings();
    }

    @Override
    public void receiveStartGame() {

    }
}
