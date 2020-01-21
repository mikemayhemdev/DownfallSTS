package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.FutureUpgradePower;

public class SixthSeal extends AbstractSealCard {
    public final static String ID = makeID("SixthSeal");

    public SixthSeal() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FutureUpgradePower(1));
    }
}