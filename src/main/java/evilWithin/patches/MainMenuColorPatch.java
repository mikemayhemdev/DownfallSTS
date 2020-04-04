package evilWithin.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.scenes.TitleCloud;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MainMenuColorPatch {
    public static final String assetPath = EvilWithinMod.assetPath("images/ui/mainmenu/");
    public static final String atlasPath = getAsset("title.atlas");

    private static String getAsset(String assetName) {
        return assetPath + assetName;
    }

    @SpirePatch(clz = TitleBackground.class, method = SpirePatch.CONSTRUCTOR)
    public static class TitleBackgroundReplacementPatch {

        @SpirePostfixPatch
        public static void evilWithinTitleBackgroundAtlasReplacer(TitleBackground __instance) {
            setMainMenuBG(__instance);
        }
    }

    public static void setMainMenuBG() {
        setMainMenuBG(null);
    }

    public static void setMainMenuBG(TitleBackground __instance) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPath));

        if (__instance == null) {
            __instance = CardCrawlGame.mainMenuScreen.bg;
        }

        setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
        setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
        setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
        setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
        setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
        setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");

        setClouds(__instance, atlas);
    }

    private static void setClouds(TitleBackground menu, TextureAtlas atlas) {
        ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
        ArrayList<TitleCloud> newMidClouds = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            newTopClouds.add(new TitleCloud(
                    atlas.findRegion("topCloud" + (i + 1)),
                    MathUtils.random(10.0f, 50.0f) * Settings.scale,
                    MathUtils.random(-1920.0f, 1920.0f) * Settings.scale)
            );
        }

        for (int i = 0; i < 12; i++) {
            newTopClouds.add(new TitleCloud(
                    atlas.findRegion("midCloud" + (i + 1)),
                    MathUtils.random(-50.0f, -10.0f) * Settings.scale,
                    MathUtils.random(-1920.0f, 1920.0f) * Settings.scale)
            );
        }

        ReflectionHacks.setPrivate(menu, TitleBackground.class, "topClouds", newTopClouds);
        ReflectionHacks.setPrivate(menu, TitleBackground.class, "midClouds", newMidClouds);
    }

    private static void setTitleBackgroundAtlasRegion(TitleBackground menu, TextureAtlas newAtlas, String classVarName, String srcRegionName) {
        ReflectionHacks.setPrivate(menu, TitleBackground.class, classVarName, newAtlas.findRegion(srcRegionName));
    }
}
