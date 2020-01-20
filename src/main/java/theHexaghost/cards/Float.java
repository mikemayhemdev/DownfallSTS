package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.AdvanceAction;

public class Float extends AbstractHexaCard {

    public final static String ID = makeID("Float");

    //stupid intellij stuff SKILL, SELF, BASIC

    public Float() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded && !GhostflameHelper.activeGhostFlame.charged) {
            atb(new GainEnergyAction(1));
        }
        atb(new AdvanceAction());
        atb(new DrawCardAction(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}