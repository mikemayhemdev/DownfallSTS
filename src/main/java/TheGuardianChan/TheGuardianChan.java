package TheGuardianChan;

import TheGuardianChan.helpers.AssetLoader;
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
public class TheGuardianChan
        implements EditStringsSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber
{

    public static String MOD_ID = "TheGuardianChan";
    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }
    public static String assetPath(String path) {
        return MOD_ID + "/" + path;
    }

    public static final String MODNAME = "TheGuardianChan";
    public static final String AUTHOR = "Rita";
    public static final String DESCRIPTION = "";
    //设置用变量
    public static boolean GuardianOriginalAnimation = true;
    public static boolean SlimeOriginalAnimation = true;
    public static boolean HexaghostOriginalAnimation = true;
    public static boolean SneckoOriginalAnimation = true;

    public static boolean hexaghostMask = false;
    public static boolean hexaghostSurroundDisplay = false;

    public static boolean disablePortraitAnimation = false;

    public static boolean displaySkin_Snecko = false;
    public static boolean displaySkin_Guardian = false;
    public static boolean displaySkin_Hexaghost = false;

    public static boolean displaySkin_SlimeBoss = false;
    public static boolean displaySkin_SlimeAcid_L = false;
    public static boolean displaySkin_SlimeAcid_M = false;
    public static boolean displaySkin_SlimeAcid_S = false;
    public static boolean displaySkin_SlimeSpike_L = false;
    public static boolean displaySkin_SlimeSpike_M = false;
    public static boolean displaySkin_SlimeSpike_S = false;
    //
    private static final float configRow = 50.0f;
    private static final float configColumn = 300.0f;

    public static Properties TheGuardianChanDefaults = new Properties();

    public static boolean foundmod_GuardianMod = false;
    public static final Logger logger = LogManager.getLogger(TheGuardianChan.class.getSimpleName());


    public static AssetLoader assets = new AssetLoader();

    @SuppressWarnings("unused")
    public static void initialize() {   new TheGuardianChan();

        foundmod_GuardianMod = Loader.isModLoaded("Guardian");
        if(foundmod_GuardianMod){logger.info("==========================守护者mod存在=============================");}

    }

    public TheGuardianChan() {
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
        String powerStrings = Gdx.files.internal("TheGuardianChan/localization/" + language + "/PowerStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String uiStrings = Gdx.files.internal("TheGuardianChan/localization/" + language + "/UIStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }

    @Override
    public void receivePostInitialize() {
        loadSettings();
        Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        ModLabeledToggleButton hexaghostMaskSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("modSettings")).TEXT[0],400.0f, 720.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,hexaghostMask, settingsPanel,
                (label) -> {}, (button) -> {hexaghostMask = button.enabled;saveSettings();});
        ModLabeledToggleButton hexaghostSurroundDisplaySwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("modSettings")).TEXT[1],400.0f, 720.0f-configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,hexaghostSurroundDisplay, settingsPanel,
                (label) -> {}, (button) -> {hexaghostSurroundDisplay = button.enabled;saveSettings();});
        ModLabeledToggleButton disablePortraitAnimationSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("modSettings")).TEXT[2],400.0f, 720.0f-2*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,disablePortraitAnimation, settingsPanel,
                (label) -> {}, (button) -> {disablePortraitAnimation = button.enabled;saveSettings();});

        ModLabeledToggleButton displaySkin_SneckoSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(Snecko.ID).NAME,400.0f, 720.0f-4*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_Snecko, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_Snecko = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_GuardianSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(TheGuardian.ID).NAME,400.0f+configColumn, 720.0f-4*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_Guardian, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_Guardian = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_HexaghostSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(Hexaghost.ID).NAME,400.0f+2*configColumn, 720.0f-4*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_Hexaghost, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_Hexaghost = button.enabled;saveSettings();});


        ModLabeledToggleButton displaySkin_SlimeBossSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(SlimeBoss.ID).NAME,400.0f+3*configColumn, 720.0f-4*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_SlimeBoss, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_SlimeBoss = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_SlimeAcid_LSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(AcidSlime_L.ID).NAME,400.0f, 720.0f-5*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_SlimeAcid_L, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_SlimeAcid_L = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_SlimeAcid_MSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(AcidSlime_M.ID).NAME,400.0f+configColumn, 720.0f-5*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_SlimeAcid_M, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_SlimeAcid_M = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_SlimeAcid_SSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(AcidSlime_S.ID).NAME,400.0f+2*configColumn, 720.0f-5*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_SlimeAcid_S, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_SlimeAcid_S = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_SlimeSpike_LSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(SpikeSlime_L.ID).NAME,400.0f, 720.0f-6*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_SlimeSpike_L, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_SlimeSpike_L = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_SlimeSpike_MSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(SpikeSlime_M.ID).NAME,400.0f+1*configColumn, 720.0f-6*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_SlimeSpike_M, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_SlimeSpike_M = button.enabled;saveSettings();});
        ModLabeledToggleButton displaySkin_SlimeSpike_SSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getMonsterStrings(SpikeSlime_S.ID).NAME,400.0f+2*configColumn, 720.0f-6*configRow, Settings.CREAM_COLOR, FontHelper.charDescFont,displaySkin_SlimeSpike_S, settingsPanel,
                (label) -> {}, (button) -> {displaySkin_SlimeSpike_S = button.enabled;saveSettings();});

        ModLabel displaySkinText = new ModLabel(CardCrawlGame.languagePack.getUIString(makeID("modSettings")).TEXT[3],400.0f, 720.0f-3*configRow, Settings.GOLD_COLOR, FontHelper.charDescFont,settingsPanel,(label) -> {});



        settingsPanel.addUIElement(hexaghostMaskSwitch);
        settingsPanel.addUIElement(hexaghostSurroundDisplaySwitch);
        settingsPanel.addUIElement(disablePortraitAnimationSwitch);


        settingsPanel.addUIElement(displaySkin_SneckoSwitch);
        settingsPanel.addUIElement(displaySkin_GuardianSwitch);
        settingsPanel.addUIElement(displaySkin_HexaghostSwitch);

        settingsPanel.addUIElement(displaySkin_SlimeBossSwitch);
        settingsPanel.addUIElement(displaySkin_SlimeAcid_LSwitch);
        settingsPanel.addUIElement(displaySkin_SlimeAcid_MSwitch);
        settingsPanel.addUIElement(displaySkin_SlimeAcid_SSwitch);
        settingsPanel.addUIElement(displaySkin_SlimeSpike_LSwitch);
        settingsPanel.addUIElement(displaySkin_SlimeSpike_MSwitch);
        settingsPanel.addUIElement(displaySkin_SlimeSpike_SSwitch);

        settingsPanel.addUIElement(displaySkinText);

    }

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("TheGuardianChanFix", "settings",TheGuardianChanDefaults);
            config.setBool("GuardianOriginalAnimation", GuardianOriginalAnimation);
            config.setBool("SlimeOriginalAnimation", SlimeOriginalAnimation);
            config.setBool("HexaghostOriginalAnimation", HexaghostOriginalAnimation);
            config.setBool("SneckoOriginalAnimation", SneckoOriginalAnimation);
            config.setBool("hexaghostMask", hexaghostMask);
            config.setBool("hexaghostSurroundDisplay", hexaghostSurroundDisplay);
            config.setBool("disablePortraitAnimation", disablePortraitAnimation);

            config.setBool("displaySkin_Snecko", displaySkin_Snecko);
            config.setBool("displaySkin_Guardian", displaySkin_Guardian);
            config.setBool("displaySkin_Hexaghost", displaySkin_Hexaghost);
            config.setBool("displaySkin_SlimeBoss", displaySkin_SlimeBoss);
            config.setBool("displaySkin_SlimeAcid_L", displaySkin_SlimeAcid_L);
            config.setBool("displaySkin_SlimeAcid_M", displaySkin_SlimeAcid_M);
            config.setBool("displaySkin_SlimeAcid_S", displaySkin_SlimeAcid_S);
            config.setBool("displaySkin_SlimeSpike_L", displaySkin_SlimeSpike_L);
            config.setBool("displaySkin_SlimeSpike_M", displaySkin_SlimeSpike_M);
            config.setBool("displaySkin_SlimeSpike_S", displaySkin_SlimeSpike_S);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("TheGuardianChanFix", "settings",TheGuardianChanDefaults);
            config.load();
            GuardianOriginalAnimation = config.getBool("GuardianOriginalAnimation");
            SlimeOriginalAnimation = config.getBool("SlimeOriginalAnimation");
            HexaghostOriginalAnimation = config.getBool("HexaghostOriginalAnimation");
            SneckoOriginalAnimation = config.getBool("SneckoOriginalAnimation");
            hexaghostMask = config.getBool("hexaghostMask");
            hexaghostSurroundDisplay = config.getBool("hexaghostSurroundDisplay");
            disablePortraitAnimation = config.getBool("disablePortraitAnimation");

            displaySkin_Snecko = config.getBool("displaySkin_Snecko");
            displaySkin_Guardian = config.getBool("displaySkin_Guardian");
            displaySkin_Hexaghost = config.getBool("displaySkin_Hexaghost");

            displaySkin_SlimeBoss = config.getBool("displaySkin_SlimeBoss");
            displaySkin_SlimeAcid_L = config.getBool("displaySkin_SlimeAcid_L");
            displaySkin_SlimeAcid_M = config.getBool("displaySkin_SlimeAcid_M");
            displaySkin_SlimeAcid_S = config.getBool("displaySkin_SlimeAcid_S");
            displaySkin_SlimeSpike_L = config.getBool("displaySkin_SlimeSpike_L");
            displaySkin_SlimeSpike_M = config.getBool("displaySkin_SlimeSpike_M");
            displaySkin_SlimeSpike_S = config.getBool("displaySkin_SlimeSpike_S");

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
