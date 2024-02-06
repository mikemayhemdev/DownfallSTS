package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import downfall.actions.PerformXAction;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.TurnItUpAction;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.LoseIntesityPower;

public class TurnItUp extends AbstractHexaCard {

    public final static String ID = makeID("TurnItUp");

    //stupid intellij stuff SKILL, ENEMY, RARE

    public TurnItUp() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
//        this.exhaust = true;
        this.retain = false;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "TurnItUp.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new EnhancePower(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseIntesityPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
//        if (energyOnUse < EnergyPanel.totalCount) {
//            energyOnUse = EnergyPanel.totalCount;
//        }
//        TurnItUpAction r = new TurnItUpAction(magicNumber);
//        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
//        if (upgraded) {
//            atb(new GainEnergyAction(1));
//        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeMagicNumber(1);
            this.retain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}