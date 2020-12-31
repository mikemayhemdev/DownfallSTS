package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHexaghost.actions.EmbersAction;
import downfall.actions.PerformXAction;

public class RainOfEmbers extends AbstractHexaCard {

    public final static String ID = makeID("RainOfEmbers");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 7;
    private static final int MAGIC = 0;

    public RainOfEmbers() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseBurn = burn = 7;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }

        EmbersAction r = new EmbersAction(magicNumber, p, m, damage, damageTypeForTurn, burn);
        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));

        if (energyOnUse >= 3){

            for (AbstractMonster q : monsterList()) {
                if (upgraded){
                    applyToEnemy(q, autoWeak(q, 2));
                    applyToEnemy(q, autoVuln(q, 2));
                } else {
                    applyToEnemy(q, autoWeak(q, 1));
                    applyToEnemy(q, autoVuln(q, 1));
                }
            }

        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeBurn(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}