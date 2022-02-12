package champ.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampMod;
import champ.cards.AbstractChampCard;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TechniqueMod extends AbstractCardModifier {


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof AbstractChampCard){
            return rawDescription;
        }
        else return rawDescription + CardCrawlGame.languagePack.getUIString(ChampMod.makeID("TechniqueMod")).TEXT[0] ;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(ChampMod.TECHNIQUE);
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
}
