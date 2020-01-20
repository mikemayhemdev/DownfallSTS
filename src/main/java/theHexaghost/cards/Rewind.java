package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.RetractAction;

public class Rewind extends AbstractHexaCard {

    public final static String ID = makeID("Rewind");

    //stupid intellij stuff SKILL, SELF, COMMON

    public Rewind() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction());
        atb(new GainEnergyAction(1));
        if (upgraded) {
            atb(new RetractAction());
            atb(new GainEnergyAction(1));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}