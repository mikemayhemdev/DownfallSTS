package reskinContent;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
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
    public static boolean guardianReskinAnimation = false;
    public static boolean slimeReskinAnimation = false;
    public static boolean hexaghostReskinAnimation = false;
    public static boolean sneckoReskinAnimation = false;

    public static boolean guardianReskinUnlock = false;
    public static boolean slimeReskinUnlock = false;
    public static boolean hexaghostReskinUnlock = false;
    public static boolean sneckoReskinUnlock = false;

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
        //Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        //ModPanel settingsPanel = new ModPanel();
        //BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

    }

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig( "reskinContent", "reskinContentSaveData",reskinContentDefaults);
            config.setBool(CardCrawlGame.saveSlot + "guardianReskinAnimation", guardianReskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "slimeReskinAnimation", slimeReskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "hexaghostReskinAnimation", hexaghostReskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "sneckoReskinAnimation", sneckoReskinAnimation);

            config.setBool(CardCrawlGame.saveSlot + "guardianReskinUnlock", guardianReskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "slimeReskinUnlock", slimeReskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "hexaghostReskinUnlock", hexaghostReskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "sneckoReskinUnlock", sneckoReskinUnlock);

            config.setBool(CardCrawlGame.saveSlot + "hexaghostMask", hexaghostMask);


            config.setInt(CardCrawlGame.saveSlot + "portraitAnimationType", portraitAnimationType);


            System.out.println("==============reskin存入数据");
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("reskinContent", "reskinContentSaveData",reskinContentDefaults);
            config.load();
            System.out.println("==============reskin载入数据");


            guardianReskinAnimation = config.getBool(CardCrawlGame.saveSlot + "guardianReskinAnimation");
            slimeReskinAnimation = config.getBool(CardCrawlGame.saveSlot +"slimeReskinAnimation");
            hexaghostReskinAnimation = config.getBool(CardCrawlGame.saveSlot +"hexaghostReskinAnimation");
            sneckoReskinAnimation = config.getBool(CardCrawlGame.saveSlot + "sneckoReskinAnimation");

            guardianReskinUnlock = config.getBool(CardCrawlGame.saveSlot + "guardianReskinUnlock");
            slimeReskinUnlock = config.getBool(CardCrawlGame.saveSlot +"slimeReskinUnlock");
            hexaghostReskinUnlock = config.getBool(CardCrawlGame.saveSlot +"hexaghostReskinUnlock");
            sneckoReskinUnlock = config.getBool(CardCrawlGame.saveSlot + "sneckoReskinUnlock");

            hexaghostMask = config.getBool(CardCrawlGame.saveSlot +"hexaghostMask");

            portraitAnimationType = config.getInt(CardCrawlGame.saveSlot +"portraitAnimationType");

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
