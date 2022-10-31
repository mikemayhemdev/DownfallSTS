package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import javassist.CtBehavior;

public class AllyPowerPatch {
    @SpirePatch(clz = AbstractRoom.class, method = "applyEndOfTurnPreCardPowers")
    public static class DragonStartTurnPower {
        @SpirePostfixPatch
        public static void Postfix(AbstractRoom __instance) {
            TorchChar dragon = CollectorChar.getLivingTorchHead();
            if (dragon != null) {
                for (AbstractPower p : dragon.powers) {
                    p.atEndOfTurnPreEndTurnCards(true);
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "draw", paramtypez = int.class)
    public static class DragonDrawPower {
        @SpireInsertPatch(locator = DrawPowerLocator.class)
        public static void Insert(AbstractPlayer __instance, int numCards, AbstractCard ___c) {
            TorchChar dragon = CollectorChar.getLivingTorchHead();
            if (dragon != null) {
                for (AbstractPower p : dragon.powers) {
                    p.onCardDraw(___c);
                }
            }
        }
    }

    public static class DrawPowerLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    @SpirePatch(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class DragonOnUseCardPower {
        @SpirePostfixPatch
        public static void Postfix(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
            TorchChar dragon = CollectorChar.getLivingTorchHead();
            if (dragon != null) {
                for (AbstractPower p : dragon.powers) {
                    if (!card.dontTriggerOnUseCard) {
                        p.onUseCard(card, __instance);
                    }
                }
            }
        }
    }

    @SpirePatch(clz = PlayerTurnEffect.class, method = SpirePatch.CONSTRUCTOR)
    public static class DragonOnEnergyRechargePower {
        @SpirePostfixPatch
        public static void Postfix(PlayerTurnEffect __instance) {
            TorchChar dragon = CollectorChar.getLivingTorchHead();
            if (dragon != null) {
                for (AbstractPower p : dragon.powers) {
                    p.onEnergyRecharge();
                }
            }
        }
    }
}
