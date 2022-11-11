package champ.cards.stancecards;

import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.ChangeGoldAction;

public class AdoringFans extends AbstractChampCard {

    public final static String ID = makeID("AdoringFans");

    public AdoringFans() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 10;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChangeGoldAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(5);
    }
}