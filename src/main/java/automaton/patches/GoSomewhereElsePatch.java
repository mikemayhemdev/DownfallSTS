package automaton.patches;

import automaton.AutomatonMod;
import automaton.MechaHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
        if (card.hasTag(AutomatonMod.BLASTER)) {
            AbstractDungeon.player.limbo.addToTop(card);
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.player.limbo.removeCard(card);
                    MechaHelper.blasters.addToTop(card);
                }
            });
            return false;
        }
        else if (card.hasTag(AutomatonMod.SHIELD)) {
            AbstractDungeon.player.limbo.addToTop(card);
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.player.limbo.removeCard(card);
                    MechaHelper.blasters.addToTop(card);
                }
            });
            return false;
        }
        else if (card.hasTag(AutomatonMod.CORE)) {
            AbstractDungeon.player.limbo.addToTop(card);
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.player.limbo.removeCard(card);
                    MechaHelper.blasters.addToTop(card);
                }
            });
            return false;
        }
        return true;
    }
}