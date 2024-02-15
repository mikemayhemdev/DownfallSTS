package collector.cards;

import automaton.actions.EasyXCostAction;
import collector.actions.ScorchingRayAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class ScorchingRay extends AbstractCollectorCard {
    public final static String ID = makeID(ScorchingRay.class.getSimpleName());
    // intellij stuff attack, enemy, common, 4, 1, , , 4, 

    public ScorchingRay() {
        super(ID, -2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                atb(new ScorchingRayAction(this));
            }
            return true;
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}