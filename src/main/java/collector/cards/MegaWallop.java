package collector.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class MegaWallop extends AbstractCollectorCard {
    public final static String ID = makeID(MegaWallop.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 25, 5, , , , 

    public MegaWallop() {
        super(ID, 5, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 25;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageCallbackAction(m, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY, (unblocked) -> {
            att(new AddTemporaryHPAction(p, p, unblocked));
        }));
    }

    public void upp() {
        upgradeDamage(5);
    }
}