package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BranchHit extends AbstractBronzeCard {

    public final static String ID = makeID("BranchHit");

    //stupid intellij stuff attack, self_and_enemy, common

    private static final int DAMAGE = 9;

    public BranchHit() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    public void upp() {
        upgradeDamage(2);
    }
}