package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeAction;
import theHexaghost.actions.ExtinguishAction;

public class CatchUp extends AbstractHexaCard {

    public final static String ID = makeID("CatchUp");

    //stupid intellij stuff SKILL, SELF, COMMON

    public CatchUp() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(HexaMod.GHOSTWHEELCARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ExtinguishAction(GhostflameHelper.getPreviousGhostFlame()));
        atb(new ChargeAction(GhostflameHelper.getPreviousGhostFlame()));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}