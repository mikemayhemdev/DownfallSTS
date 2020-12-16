package twins.cards;

import twins.DonuDecaMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShiningCore extends AbstractTwinsCard {

    public final static String ID = makeID("ShiningCore");

    //stupid intellij stuff skill, self, rare

    public ShiningCore() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(DonuDecaMod.CORE);
        tags.add(DonuDecaMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(1));
        if (inFire) {
            atb(new StunMonsterAction(m, p));
        }
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}