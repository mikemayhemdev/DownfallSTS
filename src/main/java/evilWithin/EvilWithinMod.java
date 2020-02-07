package evilWithin;

/*

This package should contain all content additions strictly related to the
Evil Mode alternate gameplay run.  This includes Bosses, Events,
Event Override patches, and other things that only appear during Evil Runs.

 */

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

import charbosses.bosses.*;
import eventUtil.EventUtils;
import evilWithin.events.GremlinMatchGame_Evil;
import guardian.characters.GuardianCharacter;
import guardian.events.GemMine;

@SpireInitializer
public class EvilWithinMod implements
        EditStringsSubscriber, PostInitializeSubscriber
{
    public static final String modID = "evil-within";

    public static void initialize()
    {
        new EvilWithinMod();
    }

    public EvilWithinMod()
    {
        BaseMod.subscribe(this);
    }

    public static String makeID(String id)
    {
        return modID + ":" + id;
    }

    public static String assetPath(String path)
    {
        return "evilWithinResources/" + path;
    }

    private String makeLocalizationPath(Settings.GameLanguage language, String filename)
    {
        String langPath;
        switch (language) {
            // Insert other languages here
            default:
                langPath = "eng";
                break;
        }
        return assetPath("localization/" + langPath + "/" + filename + ".json");
    }

    private void loadLocalization(Settings.GameLanguage language, Class<?> stringType)
    {
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName()));
    }

    private void loadLocalization(Settings.GameLanguage language)
    {

        loadLocalization(language, UIStrings.class);
        loadLocalization(language, EventStrings.class);
    }
    @Override
    public void receiveEditStrings()
    {
        loadLocalization(Settings.GameLanguage.ENG);
        if (Settings.language != Settings.GameLanguage.ENG) {
            loadLocalization(Settings.language);
        }
    }
    public void receivePostInitialize() {

        this.initializeMonsters();

        EventUtils.registerEvent(
                //Event ID//
                GremlinMatchGame_Evil.ID, GremlinMatchGame_Evil.class, true, GremlinMatchGame.ID, EventUtils.EventType.FULL_REPLACE);
    }
    private void initializeMonsters() {
        BaseMod.addMonster("EvilWithin:CharBossIronclad", () -> new MonsterGroup(new AbstractMonster[] { new CharBossIronclad() }));
        for (int i=0; i < 20; i++) {
        	BaseMod.addBoss("Exordium", "EvilWithin:CharBossIronclad", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
            BaseMod.addBoss("TheCity", "EvilWithin:CharBossIronclad", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
            BaseMod.addBoss("TheBeyond", "EvilWithin:CharBossIronclad", "images/ui/map/boss/champ.png", "images/ui/map/bossOutline/champ.png");
        }
    }
}
