package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.awakenedAmount;

public class AwokenStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(AwokenStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 5, 3, , , , 

    public AwokenStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmount();
        if (amt == 0) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        } else {
            isMultiDamage = true;
            calculateCardDamage(null);
            allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            if (amt >= 3) {
                allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            }
        }
    }

    public void upp() {
        upgradeDamage(3);
    }
}