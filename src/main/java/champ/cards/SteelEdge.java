package champ.cards;

import champ.actions.SteelEdgeAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import downfall.actions.PerformXAction;

public class SteelEdge extends AbstractChampCard {

    public final static String ID = makeID("SteelEdge");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 10;
    private static final int BLOCK = 8;

    public SteelEdge() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        SteelEdgeAction r = new SteelEdgeAction(damage, block, magicNumber, m);
        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (bcombo() || dcombo() || gcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}