package champ.cards;

import champ.powers.CalledShotPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CalledShot extends AbstractChampCard {

    public final static String ID = makeID("CalledShot");

    //stupid intellij stuff power, self, uncommon

    public CalledShot() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CalledShotPower(2));
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}