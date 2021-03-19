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
import reskinContent.skinCharacter.*;

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
            SpireConfig config = new SpireConfig("reskinContent", "reskinContentSaveData", reskinContentDefaults);
            config.setBool(CardCrawlGame.saveSlot + "guardianReskinAnimation", characters[0].reskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "slimeReskinAnimation", characters[1].reskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "hexaghostReskinAnimation", characters[2].reskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "sneckoReskinAnimation", characters[3].reskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "champReskinAnimation", characters[4].reskinAnimation);
            config.setBool(CardCrawlGame.saveSlot + "bronzeReskinAnimation", characters[5].reskinAnimation);

            config.setBool(CardCrawlGame.saveSlot + "guardianReskinUnlock", characters[0].reskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "slimeReskinUnlock", characters[1].reskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "hexaghostReskinUnlock", characters[2].reskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "sneckoReskinUnlock", characters[3].reskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "champReskinUnlock", characters[4].reskinUnlock);
            config.setBool(CardCrawlGame.saveSlot + "bronzeReskinUnlock", characters[5].reskinUnlock);

            config.setBool(CardCrawlGame.saveSlot + "hexaghostMask", hexaghostMask);

            for(int i = 0; i <= characters.length - 1; i++) {
                config.setInt(CardCrawlGame.saveSlot + "portraitAnimationType" + i, characters[i].portraitAnimationType);
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


            characters[0].reskinAnimation = config.getBool(CardCrawlGame.saveSlot + "guardianReskinAnimation");
            characters[1].reskinAnimation = config.getBool(CardCrawlGame.saveSlot + "slimeReskinAnimation");
            characters[2].reskinAnimation = config.getBool(CardCrawlGame.saveSlot + "hexaghostReskinAnimation");
            characters[3].reskinAnimation = config.getBool(CardCrawlGame.saveSlot + "sneckoReskinAnimation");
            characters[4].reskinAnimation = config.getBool(CardCrawlGame.saveSlot + "champReskinAnimation");
            characters[5].reskinAnimation = config.getBool(CardCrawlGame.saveSlot + "bronzeReskinAnimation");

            characters[0].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "guardianReskinUnlock");
            characters[1].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "slimeReskinUnlock");
            characters[2].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "hexaghostReskinUnlock");
            characters[3].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "sneckoReskinUnlock");
            characters[4].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "champReskinUnlock");
            characters[5].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "bronzeReskinUnlock");

            hexaghostMask = config.getBool(CardCrawlGame.saveSlot + "hexaghostMask");

            for(int i = 0; i <= characters.length - 1; i++) {
                characters[i].portraitAnimationType = config.getInt(CardCrawlGame.saveSlot + "portraitAnimationType" + i);

                if (characters[i].portraitAnimationType > 2 || characters[i].portraitAnimationType < 0)
                    characters[i].portraitAnimationType = 2;
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
