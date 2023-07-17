package collector.cards.collectibles;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.InvinciblePower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class FinalBossCard extends AbstractCollectibleCard {
    public final static String ID = makeID(FinalBossCard.class.getSimpleName());
    // intellij stuff skill, enemy, rare, , , , , , 

    public FinalBossCard() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RemoveSpecificPowerAction(m, p, InvinciblePower.POWER_ID));
        atb(new StunMonsterAction(m, p));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}