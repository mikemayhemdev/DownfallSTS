package evilWithin;

/*

This package should contain all content additions strictly related to the
Evil Mode alternate gameplay run.  This includes Bosses, Events,
Event Override patches, and other things that only appear during Evil Runs.

 */

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import evilWithin.util.ReplaceData;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class EvilWithinMod implements
        EditStringsSubscriber
{
    public static final String modID = "evil-within";

    public static Settings.GameLanguage[] SupportedLanguages = {
            // Insert other languages here
            Settings.GameLanguage.ENG
    };
    public static ReplaceData[] wordReplacements;

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
        String langPath = getLangString();
        return assetPath("localization/" + langPath + "/" + filename + ".json");
    }

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages)
        {
            if (lang.equals(Settings.language))
            {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
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

        try {
            String lang = getLangString();

            Gson gson = new Gson();
            String json = Gdx.files.internal(assetPath("localization/" + lang + "/replacementStrings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
            wordReplacements = gson.fromJson(json, ReplaceData[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
