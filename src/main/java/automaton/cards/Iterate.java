package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Iterate extends AbstractBronzeCard {

    public final static String ID = makeID("Iterate");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Iterate() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}