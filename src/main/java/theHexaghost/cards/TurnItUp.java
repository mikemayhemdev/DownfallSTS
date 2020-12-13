package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import downfall.actions.PerformXAction;
import theHexaghost.actions.TurnItUpAction;

public class TurnItUp extends AbstractHexaCard {

    public final static String ID = makeID("TurnItUp");

    //stupid intellij stuff SKILL, ENEMY, RARE

    public TurnItUp() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        this.exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        TurnItUpAction r = new TurnItUpAction(magicNumber);
        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}