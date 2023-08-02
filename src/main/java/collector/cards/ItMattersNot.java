
package collector.cards;

import collector.actions.DrawCardFromCollectionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.WeakHashMap;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class ItMattersNot extends AbstractCollectorCard {
    public final static String ID = makeID(ItMattersNot.class.getSimpleName());
    // intellij stuff skill, self, common, , , 8, 3, , 

    public ItMattersNot() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (AbstractMonster m2: AbstractDungeon.getCurrRoom().monsters.monsters
             ) {
            if (m2.hasPower(WeakPower.POWER_ID)){
                applyToEnemy(m, new WeakPower(m, magicNumber, false));
            }
            if (m2.hasPower(VulnerablePower.POWER_ID)){
                applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
            }
        }
    }

    public void upp() {
        upgradeBlock(3);
    }
}

