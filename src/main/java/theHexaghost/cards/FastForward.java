package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;

public class FastForward extends AbstractHexaCard {

    public final static String ID = makeID("FastForward");

    //stupid intellij stuff SKILL, SELF, COMMON

    public FastForward() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        isEthereal = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ExtinguishCurrentFlameAction());
        atb(new ChargeCurrentFlameAction());
        atb(new AdvanceAction(false));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(1);
            this.isEthereal = false;

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}