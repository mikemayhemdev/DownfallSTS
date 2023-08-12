package collector.patches;

import collector.cards.InevitableDemise;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import theHexaghost.actions.LimboToHandAction;

@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class NoDiscardPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals(CardGroup.class.getName()) && m.getMethodName().equals("moveToDiscardPile")) {
                    m.replace("if (" + NoDiscardPatch.class.getName() + ".Do($1)) {" +
                            "$_ = $proceed($$);" +
                            "}");
                }
            }
        };
    }

    @SuppressWarnings("unused")
    public static boolean Do(AbstractCard card) {
        if (card instanceof InevitableDemise && ((InevitableDemise) card).returnToYou) {
            card.cost = 1;
            card.costForTurn = 1;
            card.freeToPlayOnce = false;
            AbstractDungeon.player.limbo.addToTop(card);
            AbstractDungeon.actionManager.addToTop(new LimboToHandAction(card));
            AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(card.uuid, card.magicNumber));
            return false;
        }
        return true;
    }
}