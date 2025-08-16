package champ.util;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TechniqueMod extends AbstractCardModifier {

    public static String ID = ChampMod.makeID("TechniqueMod");

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
       return rawDescription + CardCrawlGame.languagePack.getUIString(ChampMod.makeID("TechniqueMod")).TEXT[0] ;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        if (AbstractDungeon.player.stance instanceof AbstractChampStance)
            ((AbstractChampStance) AbstractDungeon.player.stance).techique();
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TechniqueMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
