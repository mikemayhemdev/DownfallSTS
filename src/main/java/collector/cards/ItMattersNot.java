package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class ItMattersNot extends AbstractCollectorCard {
    public final static String ID = makeID(ItMattersNot.class.getSimpleName());
    // intellij stuff skill, self, common, , , 8, 3, , 

    public ItMattersNot() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 13;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m2.hasPower(WeakPower.POWER_ID)) {
                applyToEnemy(m2, new WeakPower(m2, magicNumber, false));
            }
            if (m2.hasPower(VulnerablePower.POWER_ID)) {
                applyToEnemy(m2, new VulnerablePower(m2, magicNumber, false));
            }
        }
    }

    public void upp() {
        upgradeBlock(4);
    }
}

