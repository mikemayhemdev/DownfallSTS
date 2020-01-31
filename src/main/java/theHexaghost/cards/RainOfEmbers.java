package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHexaghost.actions.EmbersAction;
import theHexaghost.actions.PerformXAction;

public class RainOfEmbers extends AbstractHexaCard {

    public final static String ID = makeID("RainOfEmbers");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 4;
    private static final int MAGIC = 0;

    public RainOfEmbers() {
        super(ID, -1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        EmbersAction r = new EmbersAction(magicNumber, p, m, damage, damageTypeForTurn);
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