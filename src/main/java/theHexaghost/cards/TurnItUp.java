package theHexaghost.cards;

import automaton.actions.EasyXCostAction;
import automaton.powers.CloningPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import downfall.actions.PerformXAction;
import theHexaghost.actions.TurnItUpAction;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.LoseEnhanceInTurnsPower;

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
        atb(new EasyXCostAction(this, (effect, params) -> {
            addToTop(new ApplyPowerAction(p, p, new EnhancePower(5), 5));
            addToTop(new ApplyPowerAction(p, p, new LoseEnhanceInTurnsPower(effect + params[0], 5), effect+params[0]));
            return true;
        }, magicNumber));
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