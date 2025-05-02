package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static awakenedOne.util.Wiz.applyToEnemy;

public class Screech extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Screech.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Screech() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    @Override
    public void upp() {
        selfRetain = true;
    }
}