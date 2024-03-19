package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHexaghost.actions.EmbersAction;
import downfall.actions.PerformXAction;

public class RainOfEmbers extends AbstractHexaCard {

    public final static String ID = makeID("RainOfEmbers");

    public RainOfEmbers() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 5;
//        baseBurn = burn = 6;
        baseMagicNumber = magicNumber = 1;
        HexaMod.loadJokeCardImage(this, "RainOfEmbers.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        if(upgraded){
            EmbersAction r = new EmbersAction(1, p, m, damage, damageTypeForTurn, 0, 1);
            atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
        }else {
            EmbersAction r = new EmbersAction(0, p, m, damage, damageTypeForTurn, 0, 1);
            atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
//            upgradeDamage(3);
//            upgradeBurn(2);
        }
    }
}