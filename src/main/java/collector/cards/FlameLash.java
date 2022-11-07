package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;

public class FlameLash extends AbstractCollectorCard {
    public final static String ID = makeID(FlameLash.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public FlameLash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageRandomEnemyAction(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), FIRE));

        for (AbstractCard c : p.hand.group
             ) {
            if (c instanceof Ember){
                atb(new DamageRandomEnemyAction(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), FIRE));
            }
        }
    }

    public void upp() {
        upgradeDamage(2);
    }
}