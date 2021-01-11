package automaton.cardmods;

import automaton.actions.RepeatCardAction;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayMeTwiceCardmod extends BronzeCardMod {

    public static String ID = "bronze:DoublePlayModifier";

    protected static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + uiStrings.TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!card.purgeOnUse) {
            atb(new RepeatCardAction(target instanceof AbstractMonster ? (AbstractMonster) target : null, card));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PlayMeTwiceCardmod();
    }
}
