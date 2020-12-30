package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Omen extends AbstractCollectorCard {
    public final static String ID = makeID("Omen");

    public Omen() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 5;
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