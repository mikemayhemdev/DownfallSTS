package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.PotionPostCombatPower;

public class FourthSeal extends AbstractSealCard {
    public final static String ID = makeID("FourthSeal");

    public FourthSeal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PotionPostCombatPower(1));
    }
}