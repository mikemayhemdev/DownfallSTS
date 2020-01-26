package slimebound.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import slimebound.characters.SlimeboundCharacter;
import slimebound.orbs.SpawnedSlime;

import java.util.ArrayList;

@SpirePatch(clz= TheCityScene.class,method=SpirePatch.CONSTRUCTOR)
public class CityBGPatch {

    @SpirePostfixPatch
    public static void Postfix(TheCityScene TheCityScene_instance) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("slimeboundResources/SlimeboundImages/scenes/Cityscene.atlas"));

            ReflectionHacks.setPrivate(TheCityScene_instance, TheCityScene.class, "mg", atlas.findRegion("mod/mg1"));
            ReflectionHacks.setPrivate(TheCityScene_instance, TheCityScene.class, "mgGlow", atlas.findRegion("mod/mg1Glow"));
            ReflectionHacks.setPrivate(TheCityScene_instance, TheCityScene.class, "mg2", atlas.findRegion("mod/mg2"));

            ReflectionHacks.setPrivate(TheCityScene_instance, TheCityScene.class, "fg", atlas.findRegion("mod/fg"));

        }
    }
}
