package theHexaghost.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import javassist.CtBehavior;
import theHexaghost.HexaMod;
import theHexaghost.vfx.AfterlifeBlurEffect;
import theHexaghost.vfx.SpookyEmberEffect;

@SpirePatch(
        clz = ExhaustCardEffect.class,
        method = "update"
)
public class ExhaustCardEffectPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn Insert(ExhaustCardEffect __instance) {
        AbstractCard thisCard = ReflectionHacks.getPrivate(__instance, ExhaustCardEffect.class, "c");
        if (thisCard.hasTag(HexaMod.AFTERLIFE)) {
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
            for (int i = 0; i < 90; ++i) {
                AbstractDungeon.effectsQueue.add(new AfterlifeBlurEffect(thisCard.current_x, thisCard.current_y));
            }

            for (int i = 0; i < 50; ++i) {
                AbstractDungeon.effectsQueue.add(new SpookyEmberEffect(thisCard.current_x, thisCard.current_y));
            }

            __instance.duration -= Gdx.graphics.getDeltaTime();
            if (!thisCard.fadingOut && __instance.duration < 0.7F && !AbstractDungeon.player.hand.contains(thisCard)) {
                thisCard.fadingOut = true;
            }

            if (__instance.duration < 0.0F) {
                __instance.isDone = true;
                thisCard.resetAttributes();
            }
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(SoundMaster.class, "play");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}