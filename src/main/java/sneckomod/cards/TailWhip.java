package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class TailWhip extends AbstractSneckoCard {

    public final static String ID = makeID("TailWhip");

    //stupid intellij stuff ATTACK, ENEMY, BASIC

    private static final int DAMAGE = 10;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int UPG_DAMAGE = 2;

    public TailWhip() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "TailWhip.png");
        this.tags.add(SneckoMod.OVERFLOW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        int x = 0;
        // this is probably bad practice but it works
        if (isOverflowActive(this)) {
            x = magicNumber;
        }
        if (x > 0)
            applyToEnemy(m, autoWeak(m, x));
        int y = 0;
        if (isOverflowActive(this)) {
            y = magicNumber;
        }
        if (y > 0)
            applyToEnemy(m, autoVuln(m, y));
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            upgradeDamage(UPG_DAMAGE);
        }
    }
}