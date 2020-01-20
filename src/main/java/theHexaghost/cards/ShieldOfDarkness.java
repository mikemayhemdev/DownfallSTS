package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;

public class ShieldOfDarkness extends AbstractHexaCard {

    public final static String ID = makeID("ShieldOfDarkness");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 5;

    public ShieldOfDarkness() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!GhostflameHelper.activeGhostFlame.charged) {
            atb(new GainEnergyAction(1));
        }
        blck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}