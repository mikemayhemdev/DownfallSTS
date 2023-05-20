package collector.patches.PyrePatches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import collector.cardmods.PyreMod;
import collector.cards.OnPyreCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.att;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard")
public class PyreAdditionalCostPatch {
    public static void Postfix(AbstractPlayer p, AbstractCard c, AbstractMonster m, int energyonuse) {
        if (!c.freeToPlayOnce && CardModifierManager.hasModifier(c, PyreMod.ID)) {
            for (AbstractCardModifier r : CardModifierManager.getModifiers(c, PyreMod.ID)) {
                if (r instanceof PyreMod) {
                    //TODO: Slightly gross code. Either make an exhaust callback action (iffy but not difficult) or always use the select (easy but uglier)
                    if (c instanceof OnPyreCard) { // On pyre is att
                        att(new SelectCardsInHandAction("TODO: Localize this", (cards) -> {
                            ((OnPyreCard) c).onPyred(cards.get(0));
                            att(new ExhaustSpecificCardAction(cards.get(0), AbstractDungeon.player.hand));
                        }));
                    } else {
                        att(new ExhaustAction(1, false));
                    }
                }
            }
        }
    }
}