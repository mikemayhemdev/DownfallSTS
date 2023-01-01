package awakenedOne.patches;

import awakenedOne.powers.ThoughtshieldPower;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ThoughtshieldPatches {
    @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = "update")
    public static class NoStatusShuffleIn {
        public static SpireReturn Prefix(MakeTempCardInDrawPileAction __instance) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, MakeTempCardInDrawPileAction.class, "cardToMake");
            if (AbstractDungeon.actionManager.turnHasEnded && (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.CURSE)) {
                AbstractPower p = AbstractDungeon.player.getPower(ThoughtshieldPower.POWER_ID);
                if (p != null) {
                    p.flash();
                    __instance.isDone = true;
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MakeTempCardInDiscardAction.class, method = "update")
    public static class NoStatusShuffleIntoDiscard {
        public static SpireReturn Prefix(MakeTempCardInDiscardAction __instance) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, MakeTempCardInDiscardAction.class, "c");
            if (AbstractDungeon.actionManager.turnHasEnded && (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.CURSE)) {
                AbstractPower p = AbstractDungeon.player.getPower(ThoughtshieldPower.POWER_ID);
                if (p != null) {
                    p.flash();
                    __instance.isDone = true;
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MakeTempCardInDiscardAndDeckAction.class, method = "update")
    public static class NoStatusShuffleOrbWalker {
        public static SpireReturn Prefix(MakeTempCardInDiscardAndDeckAction __instance) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, MakeTempCardInDiscardAndDeckAction.class, "cardToMake");
            if (AbstractDungeon.actionManager.turnHasEnded && (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.CURSE)) {
                AbstractPower p = AbstractDungeon.player.getPower(ThoughtshieldPower.POWER_ID);
                if (p != null) {
                    p.flash();
                    __instance.isDone = true;
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AddCardToDeckAction.class, method = "update")
    public static class WrithingMassBlownOutForever {
        public static SpireReturn Prefix(AddCardToDeckAction __instance) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, MakeTempCardInDiscardAndDeckAction.class, "cardToObtain");
            if (AbstractDungeon.actionManager.turnHasEnded && (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.CURSE)) {
                AbstractPower p = AbstractDungeon.player.getPower(ThoughtshieldPower.POWER_ID);
                if (p != null) {
                    p.flash();
                    __instance.isDone = true;
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }
}
