package collector.cards.CollectorCards.Powers;

import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Omen extends AbstractCollectorCard {
    public final static String ID = makeID("Omen");

    public Omen() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = 25;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new collector.powers.Omen(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(5);
    }
}