package champ.cards;

import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldThrow extends AbstractChampCard {

    public final static String ID = makeID("ShieldThrow");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 6;

    public ShieldThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        // tags.add(ChampMod.COMBO);
        //  tags.add(ChampMod.COMBOBERSERKER);
        //  tags.add(ChampMod.COMBODEFENSIVE);
       
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new WallopAction(m, makeInfo()));
    }

    /*
    @Override
    public void triggerOnGlowCheck() {
        glowColor = (dcombo() || bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

     */

    public void upp() {
        upgradeDamage(3);
    }
}