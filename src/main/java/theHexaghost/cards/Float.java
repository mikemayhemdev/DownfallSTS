package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
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
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AnimateHopAction(p));
        if (upgraded) {
            if (GhostflameHelper.activeGhostFlame.charged) {
                atb(new GainEnergyAction(1));
            } else {
                atb(new DrawCardAction(1));
            }
        }
        atb(new AdvanceAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}