package automaton.cards;

import automaton.cardmods.EncodeMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Goto extends AbstractBronzeCard {

    public final static String ID = makeID("Goto");

    //stupid intellij stuff skill, self, basic

    public Goto() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}