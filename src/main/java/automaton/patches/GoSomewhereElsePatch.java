package automaton.patches;

import automaton.FunctionHelper;
import automaton.cardmods.EncodeMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import static automaton.FunctionHelper.cardPositions;
import static automaton.FunctionHelper.doStuff;

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
        if (CardModifierManager.hasModifier(card, EncodeMod.ID) && FunctionHelper.held.size() < FunctionHelper.max()) {
            card.target_x = cardPositions[FunctionHelper.held.size()].x;
            card.target_y = cardPositions[FunctionHelper.held.size()].y;
            AbstractDungeon.player.limbo.addToTop(card);
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.player.limbo.removeCard(card);
                    FunctionHelper.addToSequence(card);
                }
            });
            return false;
        }
        return true;
    }
}