package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
@Deprecated
@CardIgnore
public class Disintegrate extends AbstractAwakenedCard {
    public final static String ID = makeID(Disintegrate.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Disintegrate() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseSecondMagic = secondMagic = 5;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //HexCurse(secondMagic, m, p);
    }

    public void upp() {
        upgradeSecondMagic(5);
    }
}