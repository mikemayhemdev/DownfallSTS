package champ.cards;

import champ.actions.LariatAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import downfall.actions.PerformXAction;

public class Lariat extends AbstractChampCard {

    public final static String ID = makeID("Lariat");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 1;

    public Lariat() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        LariatAction r = new LariatAction(magicNumber, block, dcombo() ? 1 : 0);
        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        upgradeMagicNumber(UPG_MAGIC);
    }
}