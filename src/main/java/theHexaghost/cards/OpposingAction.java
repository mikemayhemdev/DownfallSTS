package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHexaghost.actions.RetractAction;

public class OpposingAction extends AbstractHexaCard{
    public final static String ID = makeID("OpposingAction");

    public OpposingAction() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, AbstractCard.CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

//        if (this.energyOnUse < EnergyPanel.totalCount) {
//            this.energyOnUse = EnergyPanel.totalCount;
//        }
//        if (p.hasRelic("Chemical X")) {
//            this.energyOnUse += 2;
//            p.getRelic("Chemical X").flash();
//        }
//
//        int times = this.energyOnUse;
//        if(upgraded){times += 1;}
//
//        for (int i = 0; i < times; i++) {
//            atb(new DrawCardAction(1));
//        }
        atb(new RetractAction(1));

//        if (!this.freeToPlayOnce) {
//            p.energy.use(EnergyPanel.totalCount);
//        }
        atb(new GainEnergyAction(1));
        if(upgraded) atb(new GainEnergyAction(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
