package awakenedOne.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.powers.Drained;

public class DrainingMod extends AbstractCardModifier {

    public static String ID = "awakened:DrainingMod";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString("awakened:DrainingMod").TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Drained(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.updateCost(-1);
        card.initializeDescription();
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.updateCost(+1);
        card.initializeDescription();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DrainingMod();
    }

}