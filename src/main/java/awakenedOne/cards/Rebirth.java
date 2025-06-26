package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.powers.AwakenDeathPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Rebirth extends AbstractAwakenedCard {
    public final static String ID = makeID(Rebirth.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 4, 2

    public Rebirth() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 7;
        tags.add(CardTags.HEALING);
        loadJokeCardImage(this, makeBetaCardPath(Rebirth.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new AwakenDeathPower(p, p, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}