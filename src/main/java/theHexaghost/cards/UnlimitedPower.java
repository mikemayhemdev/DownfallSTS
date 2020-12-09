package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class UnlimitedPower extends AbstractHexaCard {

    public final static String ID = makeID("UnlimitedPower");

    //stupid intellij stuff ATTACK, ALL, RARE


    public UnlimitedPower() {
        super(ID, 5, CardType.SKILL, CardRarity.RARE, CardTarget.ALL);
        exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            atb(new ExtinguishAction(gf));
            atb(new ChargeAction(gf));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(4);
        }
    }
}