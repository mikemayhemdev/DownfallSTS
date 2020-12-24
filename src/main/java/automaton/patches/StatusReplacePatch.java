package automaton.patches;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.cardmods.EncodeMod;
import automaton.relics.BronzeIdol;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import slimebound.SlimeboundMod;

import static automaton.FunctionHelper.cardPositions;

@SpirePatch(
        clz = AbstractCard.class,
        method = "makeStatEquivalentCopy"
)
public class StatusReplacePatch {

    @SpirePrefixPatch
    public static SpireReturn<AbstractCard> Prefix(AbstractCard abstractCard) {
       // SlimeboundMod.logger.info("making stat equivalent copy");
        if (abstractCard.type == AbstractCard.CardType.STATUS) {
            //SlimeboundMod.logger.info("IS STATUS!");
            if (AbstractDungeon.player.hasRelic(BronzeIdol.ID)) {
                if (!abstractCard.hasTag(AutomatonMod.GOOD_STATUS)) {

                    AbstractCard newStatus = AutomatonMod.getGoodStatus(abstractCard);
                  //  SlimeboundMod.logger.info("replacing with " + newStatus.name);
                    return SpireReturn.Return(newStatus);
                }
            }
        }
        return SpireReturn.Continue();

    }
}
