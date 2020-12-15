package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpreadShot extends AbstractBronzeCard {

    public final static String ID = makeID("SpreadShot");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public SpreadShot() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        tags.add(AutomatonMod.BLASTER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
         allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}