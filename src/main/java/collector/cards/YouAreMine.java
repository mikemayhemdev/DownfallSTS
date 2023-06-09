package collector.cards;

import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class YouAreMine extends AbstractCollectorCard {
    public final static String ID = makeID(YouAreMine.class.getSimpleName());
    // intellij stuff skill, enemy, rare, , , , , 4, 2

    public YouAreMine() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.currentBlock > 0) {
            atb(new RemoveAllBlockAction(m, p));
        }
        if (m.hasPower(ArtifactPower.POWER_ID)) {
            atb(new RemoveSpecificPowerAction(m, p, ArtifactPower.POWER_ID));
        }
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}