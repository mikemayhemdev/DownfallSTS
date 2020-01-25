package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ChargeAction;
import theHexaghost.actions.ExtinguishAction;

public class SkipABeat extends AbstractHexaCard {

    public final static String ID = makeID("SkipABeat");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public SkipABeat() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AdvanceAction());
        atb(new ExtinguishAction(1));
        atb(new ChargeAction(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}