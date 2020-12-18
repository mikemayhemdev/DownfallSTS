package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NullPointer extends AbstractBronzeCard {

    public final static String ID = makeID("NullPointer");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;

    private static final int BLOCK = 15;
    private static final int UPG_BLOCK = 5;

    public NullPointer() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);
        blck();
    }

    @Override
    public boolean onOutput() {
        //POOF!
        return false;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}