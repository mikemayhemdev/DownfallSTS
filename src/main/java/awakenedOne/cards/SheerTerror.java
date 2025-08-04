package awakenedOne.cards;

import awakenedOne.powers.RetainingGivesBlockPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;

public class SheerTerror extends AbstractAwakenedCard {
    public final static String ID = makeID(SheerTerror.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public SheerTerror() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(SheerTerror.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new RetainingGivesBlockPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}