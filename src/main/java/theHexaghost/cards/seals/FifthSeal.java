package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.SealPostCombatPower;

public class FifthSeal extends AbstractSealCard {
    public final static String ID = makeID("FifthSeal");

    public FifthSeal() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SealPostCombatPower(1));
    }
}