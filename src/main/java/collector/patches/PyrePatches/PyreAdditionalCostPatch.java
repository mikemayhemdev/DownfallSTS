package collector.patches.PyrePatches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import collector.cardmods.PyreMod;
import collector.cards.OnPyreCard;
import collector.powers.OnPyrePower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static collector.util.Wiz.att;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard")
public class PyreAdditionalCostPatch {
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("collector:PyreCostSpendScreen");

    public static void Postfix(AbstractPlayer p, AbstractCard c, AbstractMonster m, int energyonuse) {
        if (CardModifierManager.hasModifier(c, PyreMod.ID)) {
            for (AbstractCardModifier r : CardModifierManager.getModifiers(c, PyreMod.ID)) {
                if (r instanceof PyreMod) {

                    att(new SelectCardsInHandAction(uiStrings.TEXT[0], (cards) -> {
                        for (AbstractPower pow : AbstractDungeon.player.powers) {
                            if (pow instanceof OnPyrePower) {
                                ((OnPyrePower) pow).onPyre(cards.get(0));
                            }
                        }
                        if (c instanceof OnPyreCard)
                            ((OnPyreCard) c).onPyred(cards.get(0));
                        att(new ExhaustSpecificCardAction(cards.get(0), AbstractDungeon.player.hand));
                    }));

                }
            }
        }
    }
}