package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class GreaterPyromancy extends AbstractSpellCard {
    public final static String ID = makeID(GreaterPyromancy.class.getSimpleName());
    // intellij stuff attack, all_enemy, 14, 4, , , , 

    public GreaterPyromancy() {
        super(ID, CardType.ATTACK, CardTarget.ALL_ENEMY);
        baseDamage = 14;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeDamage(4);
    }
}