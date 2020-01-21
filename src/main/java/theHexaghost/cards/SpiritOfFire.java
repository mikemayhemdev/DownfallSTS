package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class SpiritOfFire extends AbstractHexaCard {

    public final static String ID = makeID("SpiritOfFire");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public SpiritOfFire() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            if (gf.charged)
                atb(new GainEnergyAction(1));
        }
        if (upgraded)
            atb(new DrawCardAction(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}