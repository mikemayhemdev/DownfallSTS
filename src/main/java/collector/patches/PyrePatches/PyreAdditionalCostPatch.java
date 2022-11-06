package collector.patches.PyrePatches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import collector.cardmods.PyreMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.att;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard")
public class PyreAdditionalCostPatch {
    public static void Postfix(AbstractPlayer p, AbstractCard c, AbstractMonster m, int energyonuse) {
        if (!c.freeToPlayOnce && CardModifierManager.hasModifier(c, PyreMod.ID)) {
            for (AbstractCardModifier r : CardModifierManager.getModifiers(c, PyreMod.ID)) {
                if (r instanceof PyreMod) {
                    int x = ((PyreMod) r).exhaustAmt;
                    att(new ExhaustAction(x, false));
                }
            }
        }
    }
}