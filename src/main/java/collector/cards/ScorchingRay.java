package collector.cards;

import collector.actions.ScorchingRayAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class ScorchingRay extends AbstractCollectorCard {
    public final static String ID = makeID(ScorchingRay.class.getSimpleName());
    // intellij stuff attack, enemy, common, 4, 1, , , 4, 

    public ScorchingRay() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new ScorchingRayAction(this));
        }
    }

    public void upp() {
        upgradeDamage(2);
    }
}