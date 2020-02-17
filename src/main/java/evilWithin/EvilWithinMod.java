package evilWithin;

/*

This package should contain all content additions strictly related to the
Evil Mode alternate gameplay run.  This includes Bosses, Events,
Event Override patches, and other things that only appear during Evil Runs.

 */

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import charbosses.bosses.Ironclad.CharBossIronclad;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.DrugDealer;
import com.megacrit.cardcrawl.events.exordium.DeadAdventurer;
import com.megacrit.cardcrawl.events.exordium.LivingWall;
import com.megacrit.cardcrawl.events.exordium.ShiningLight;
import com.megacrit.cardcrawl.events.shrines.*;
import com.megacrit.cardcrawl.events.shrines.FaceTrader;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import eventUtil.EventUtils;
import evilWithin.events.*;
import evilWithin.monsters.*;
import evilWithin.potions.CursedFountainPotion;
import evilWithin.util.ReplaceData;
import guardian.patches.GuardianEnum;
import guardian.potions.AcceleratePotion;
import guardian.potions.BlockOnCardUsePotion;
import guardian.potions.DefensiveModePotion;
import guardian.potions.StasisDiscoveryPotion;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class EvilWithinMod implements
        EditStringsSubscriber, PostInitializeSubscriber {
    public static final String modID = "evil-within";

    public static final boolean EXPERIMENTAL_FLIP = false;
    public static Settings.GameLanguage[] SupportedLanguages = {
            // Insert other languages here
            Settings.GameLanguage.ENG
    };
    public static ReplaceData[] wordReplacements;

    public static void initialize() {
        new EvilWithinMod();
    }

    public EvilWithinMod() {
        BaseMod.subscribe(this);
    }

    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static String assetPath(String path) {
        return "evilWithinResources/" + path;
    }

    private String makeLocalizationPath(Settings.GameLanguage language, String filename) {
        String langPath = getLangString();
        return assetPath("localization/" + langPath + "/" + filename + ".json");
    }

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    private void loadLocalization(Settings.GameLanguage language, Class<?> stringType) {
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName()));
    }

    private void loadLocalization(Settings.GameLanguage language) {

        loadLocalization(language, UIStrings.class);
        loadLocalization(language, EventStrings.class);
        loadLocalization(language, RelicStrings.class);
        loadLocalization(language, MonsterStrings.class);
        loadLocalization(language, PotionStrings.class);
    }

    @Override
    public void receiveEditStrings() {
        loadLocalization(Settings.GameLanguage.ENG);
        if (Settings.language != Settings.GameLanguage.ENG) {
            loadLocalization(Settings.language);
        }

        try {
            String lang = getLangString();

            Gson gson = new Gson();
            String json = Gdx.files.internal(assetPath("localization/" + lang + "/replacementStrings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
            wordReplacements = gson.fromJson(json, ReplaceData[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receivePostInitialize() {

        this.initializeMonsters();
        this.addPotions();
        this.initializeEvents();

    }

    private void initializeEvents(){
        EventUtils.registerEvent(
                //Event ID//
                GremlinMatchGame_Evil.ID, GremlinMatchGame_Evil.class, true,
                //Event ID to Override//
                GremlinMatchGame.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                GremlinWheelGame_Evil.ID, GremlinWheelGame_Evil.class, true,
                //Event ID to Override//
                GremlinWheelGame.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event only used in Gremlin Wheel relic.  Is not initialized into any Act.
                GremlinWheelGame_Rest.ID, GremlinWheelGame_Rest.class, new String[]{""});

        EventUtils.registerEvent(
                //Event ID//
                WomanInBlue_Evil.ID, WomanInBlue_Evil.class, true,
                //Event ID to Override//
                WomanInBlue.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                LivingWall_Evil.ID, LivingWall_Evil.class, true,
                //Event ID to Override//
                LivingWall.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                Augmenter_Evil.ID, Augmenter_Evil.class, true,
                //Event ID to Override//
                DrugDealer.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                BonfireSpirits_Evil.ID, BonfireSpirits_Evil.class, true,
                //Event ID to Override//
                Bonfire.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                GoldenShrine_Evil.ID, GoldenShrine_Evil.class, true,
                //Event ID to Override//
                GoldShrine.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                FaceTrader_Evil.ID, FaceTrader_Evil.class, true,
                //Event ID to Override//
                FaceTrader.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                CursedFountain.ID, CursedFountain.class, true,
                //Event ID to Override//
                FountainOfCurseRemoval.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                WeMeetAgain_Evil.ID, WeMeetAgain_Evil.class, true,
                //Event ID to Override//
                WeMeetAgain.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                Designer_Evil.ID, Designer_Evil.class, true,
                //Event ID to Override//
                Designer.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);


        EventUtils.registerEvent(
                //Event ID//
                DeadGuy_Evil.ID, DeadGuy_Evil.class, true,
                //Event ID to Override//
                DeadAdventurer.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);

        EventUtils.registerEvent(
                //Event ID//
                ShiningLight_Evil.ID, ShiningLight_Evil.class, true,
                //Event ID to Override//
                ShiningLight.ID,
                //Event Type//
                EventUtils.EventType.FULL_REPLACE);
    }

    private void initializeMonsters() {

        BaseMod.addMonster(LadyInBlue.ID, LadyInBlue::new);

        BaseMod.addMonster(Augmenter.ID, Augmenter::new);

        BaseMod.addMonster("EvilWithin:Heads", "Living Wall Heads", () -> new MonsterGroup(
                new AbstractMonster[] {
                        new ChangingTotem(),
                        new ForgetfulTotem(),
                        new GrowingTotem(),
                }));

        BaseMod.addMonster("EvilWithin:CharBossIronclad", () -> new MonsterGroup(new AbstractMonster[]{new CharBossIronclad()}));
        //BaseMod.addMonster("EvilWithin:CharBossSilent", () -> new MonsterGroup(new AbstractMonster[] { new CharBossSilent() }));
        for (int i = 0; i < 20; i++) {
            BaseMod.addBoss("Exordium", "EvilWithin:CharBossIronclad", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
            BaseMod.addBoss("TheCity", "EvilWithin:CharBossIronclad", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
            BaseMod.addBoss("TheBeyond", "EvilWithin:CharBossIronclad", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
            //BaseMod.addBoss("Exordium", "EvilWithin:CharBossSilent", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
            //BaseMod.addBoss("TheCity", "EvilWithin:CharBossSilent", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
            //BaseMod.addBoss("TheBeyond", "EvilWithin:CharBossSilent", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
        }


    }

    public void addPotions() {

        BaseMod.addPotion(CursedFountainPotion.class, Color.PURPLE, Color.MAROON, Color.BLACK, CursedFountainPotion.POTION_ID);

    }
}
