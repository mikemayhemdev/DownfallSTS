package hermit.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import hermit.util.TextureLoader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hermit.powers.BigShotPower;

import static hermit.HermitMod.makeID;

public class AttackEffectPatch {

    private static Texture GhostFireTexture = TextureLoader.getTexture("hermitResources/images/vfx/HermitGhostFire.png");

    @SpirePatch(clz = FlashAtkImgEffect.class, method = "loadImage")
    public static class vfx
    {
        @SpirePrefixPatch
        public static SpireReturn Prefix(FlashAtkImgEffect e, AbstractGameAction.AttackEffect ___effect)
        {
            if (___effect == EnumPatch.HERMIT_GUN0 || ___effect == EnumPatch.HERMIT_GUN || ___effect == EnumPatch.HERMIT_GUN2 || ___effect == EnumPatch.HERMIT_GUN3) {
                return SpireReturn.Return(ImageMaster.ATK_BLUNT_LIGHT);
            }

            if (___effect == EnumPatch.HERMIT_GHOSTFIRE)
            {
                return SpireReturn.Return(new TextureAtlas.AtlasRegion(GhostFireTexture, 0, 0, GhostFireTexture.getWidth(), GhostFireTexture.getHeight()));
            }

            return SpireReturn.Continue();
        }

    }
    @SpirePatch(clz = FlashAtkImgEffect.class, method = "playSound")
    public static class sfx
    {
        @SpirePrefixPatch
        public static SpireReturn Prefix(FlashAtkImgEffect e, AbstractGameAction.AttackEffect effect)
        {
            if (effect == EnumPatch.HERMIT_GUN) {
                CardCrawlGame.sound.playV(makeID("GUN1"), 1.25f); // Sound Effect
            }
            else if (effect == EnumPatch.HERMIT_GUN2) {
                CardCrawlGame.sound.playV(makeID("GUN2"), 1.25f); // Sound Effect
            }
            else if (effect == EnumPatch.HERMIT_GUN3) {
                CardCrawlGame.sound.playV(makeID("GUN3"), 1.25f); // Sound Effect
            }
            else if (effect == EnumPatch.HERMIT_GHOSTFIRE) {
                CardCrawlGame.sound.playV("ATTACK_FIRE", 1.25f); // Sound Effect
            }
            else {
                return SpireReturn.Continue();
            }

            return SpireReturn.Return(null);
        }

    }


}
