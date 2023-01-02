package awakenedOne.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemyTop;
import static awakenedOne.util.Wiz.atb;

public class ForeverFrozen extends AbstractAwakenedCard {
    public final static String ID = makeID(ForeverFrozen.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 4, 2, , , , 

    public ForeverFrozen() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageCallbackAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, (dealt -> {
            applyToEnemyTop(m, new WeakPower(m, dealt, false));
        })));
    }

    public void upp() {
        upgradeDamage(2);
    }
}