package collector.patches;

import basemod.ReflectionHacks;
import collector.CollectorCollection;
import collector.Interfaces.PerpetualCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class PerpetualPatch {
    @SpirePatch(
            clz = UseCardAction.class,
            method = SpirePatch.CLASS
    )

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class UseCardActionInsertPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(UseCardAction __instance) {
            //quick fix, remove cards from limbo for multi-play effects
            AbstractDungeon.player.limbo.removeCard((AbstractCard) ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard"));

            //if the played card was transmuted, handle separately from regular UseCardAction logic
            if (ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard") instanceof PerpetualCard) {
                AbstractCard card = ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard");
                card.freeToPlayOnce = false;
                card.isInAutoplay = false;
                card.exhaustOnUseOnce = false;
                card.dontTriggerOnUseCard = false;
                AbstractDungeon.player.hand.removeCard(card);
                AbstractDungeon.player.limbo.removeCard(card);
                if (AbstractDungeon.player.hoveredCard == card) {
                    AbstractDungeon.player.releaseCard();
                }
                AbstractDungeon.actionManager.removeFromQueue(card);
                card.unhover();
                card.untip();
                card.stopGlowing();
                card.shrink();
                card.darken(false);
                ((PerpetualCard)card).PerpetualBonus();
                CollectorCollection.combatCollection.addToBottom(card);
                __instance.isDone = true;
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "freeToPlayOnce");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
