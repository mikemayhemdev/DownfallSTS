package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class DamperWave extends AbstractAwakenedCard {
    public final static String ID = makeID(DamperWave.class.getSimpleName());
    // intellij stuff skill, all_enemy, uncommon, , , , , 1, 1

    public DamperWave() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(q -> applyToEnemy(q, new WeakPower(q, magicNumber, false)));
        forAllMonstersLiving(q -> {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    if (q.hasPower(WeakPower.POWER_ID)) {
                        int x = q.getPower(WeakPower.POWER_ID).amount;
                        att(new LoseHPAction(q, p, x));
                    }
                }
            });
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}