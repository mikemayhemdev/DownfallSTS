package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class IllTakeThat extends AbstractCollectorCard {
    public final static String ID = makeID(IllTakeThat.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 3, , , , 

    public IllTakeThat() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (m.currentBlock > 0) {
            int held = m.currentBlock;
            atb(new RemoveAllBlockAction(m, p));
            atb(new GainBlockAction(p, held));
        }
    }

    public void upp() {
        upgradeDamage(3);
    }
}