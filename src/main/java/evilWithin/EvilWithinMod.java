package evilWithin;

/*

This package should contain all content additions strictly related to the
Evil Mode alternate gameplay run.  This includes Bosses, Events,
Event Override patches, and other things that only appear during Evil Runs.

 */

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;

@SpireInitializer
public class EvilWithinMod implements
        EditStringsSubscriber
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
    }

    @Override
    public void receiveEditStrings()
    {
        loadLocalization(Settings.GameLanguage.ENG);
        if (Settings.language != Settings.GameLanguage.ENG) {
            loadLocalization(Settings.language);
        }
    }
}
