package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class SpectralAdrenaline extends AbstractHexaCard {

    public final static String ID = makeID("SpectralAdrenaline");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public SpectralAdrenaline() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            if (gf.charged) {
                atb(new ExtinguishAction(gf));
                atb(new GainEnergyAction(1));
                atb(new DrawCardAction(1));
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}