package twins.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import twins.DonuDecaMod;
import twins.TwinsHelper;

@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class GoSomewhereElsePatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals(CardGroup.class.getName()) && m.getMethodName().equals("moveToDiscardPile")) {
                    m.replace("if (" + GoSomewhereElsePatch.class.getName() + ".Do($1)) {" +
                            "$_ = $proceed($$);" +
                            "}");
                }
            }
        };
    }

    @SuppressWarnings("unused")
    public static boolean Do(AbstractCard card) {
        if (card.purgeOnUse) {
            return true;
        }
        if (card.hasTag(DonuDecaMod.DONU_CARD)) {
            if (TwinsHelper.donuInFront) {
                card.target_x = TwinsHelper.FRONT_CARDS_LOCATION;
                card.targetTransparency = TwinsHelper.FRONT_CARDS_OPACITY;
            } else {
                card.target_x = TwinsHelper.BACK_CARDS_LOCATION;
                card.targetTransparency = TwinsHelper.BACK_CARDS_OPACITY;
            }
            AbstractDungeon.player.limbo.addToTop(card);
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.player.limbo.removeCard(card);
                    TwinsHelper.donuCards.addToTop(card);
                }
            });
            return false;
        } else if (card.hasTag(DonuDecaMod.DECA_CARD)) {
            if (!TwinsHelper.donuInFront) {
                card.target_x = TwinsHelper.FRONT_CARDS_LOCATION;
                card.targetTransparency = TwinsHelper.FRONT_CARDS_OPACITY;
            } else {
                card.target_x = TwinsHelper.BACK_CARDS_LOCATION;
                card.targetTransparency = TwinsHelper.BACK_CARDS_OPACITY;
            }
            AbstractDungeon.player.limbo.addToTop(card);
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.player.limbo.removeCard(card);
                    TwinsHelper.decaCards.addToTop(card);
                }
            });
            return false;
        }
        return true;
    }
}