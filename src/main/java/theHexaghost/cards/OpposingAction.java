package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.RetractAction;

public class OpposingAction extends AbstractHexaCard{
    public final static String ID = makeID("OpposingAction");

    public OpposingAction() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        HexaMod.loadJokeCardImage(this, "OpposingAction.png");
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction(1));

        if(!upgraded)
            atb(new GainEnergyAction(2));
        else
            atb(new GainEnergyAction(3));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
