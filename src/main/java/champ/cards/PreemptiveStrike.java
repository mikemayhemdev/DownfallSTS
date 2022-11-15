package champ.cards;

import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.w3c.dom.css.Counter;

public class PreemptiveStrike extends AbstractChampCard {

    public final static String ID = makeID("PreemptiveStrike");

    private static final int DAMAGE = 8;

    public PreemptiveStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        tags.add(CardTags.STRIKE);
       
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(CounterPower.POWER_ID)) {
            for (int i = 0; i < p.getPower(CounterPower.POWER_ID).amount; i++) {
                allDmg(AbstractGameAction.AttackEffect.SLASH_VERTICAL);
            }
        }
    }

    public void upp() {
        upgradeDamage(3);
    }
}