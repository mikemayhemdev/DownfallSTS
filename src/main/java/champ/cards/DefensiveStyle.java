package champ.cards;

import champ.powers.DefensiveStylePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefensiveStyle extends AbstractChampCard {

    public final static String ID = makeID("DefensiveStyle");

    //stupid intellij stuff power, self, uncommon

    public DefensiveStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DefensiveStylePower(5));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}