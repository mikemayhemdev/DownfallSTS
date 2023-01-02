package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class LifeLeech extends AbstractAwakenedCard {
    public final static String ID = makeID(LifeLeech.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 4, 2

    public LifeLeech() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmt();
        applyToEnemy(m, new StrengthPower(m, -magicNumber));
        if (m != null && !m.hasPower(ArtifactPower.POWER_ID)) {
            applyToEnemy(m, new GainStrengthPower(m, magicNumber));
        }

        if (amt >= 2) {
            applyToSelf(new StrengthPower(p, magicNumber));
            applyToSelf(new LoseStrengthPower(p, -magicNumber));
        }
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}