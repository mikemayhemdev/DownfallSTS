package champ.cards;

import champ.powers.CounterPower;
import champ.powers.ParryPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Parry extends AbstractChampCard {

    public final static String ID = makeID("Parry");

    public Parry() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new ParryPower(1));
    }

    public void upp() {
        upgradeBlock(3);
    }
}