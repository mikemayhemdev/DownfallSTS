package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.CommonPostCombatPower;

public class ThirdSeal extends AbstractSealCard {
    public final static String ID = makeID("ThirdSeal");

    public ThirdSeal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        HexaMod.loadJokeCardImage(this, "ThirdSeal.png");
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CommonPostCombatPower(1));
    }
}