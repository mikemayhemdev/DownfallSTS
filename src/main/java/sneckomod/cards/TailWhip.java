package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TailWhip extends AbstractSneckoCard {

    public final static String ID = makeID("TailWhip");

    //stupid intellij stuff ATTACK, ENEMY, BASIC

    private static final int DAMAGE = 10;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public TailWhip() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        int x = getRandomNum(0, magicNumber);
        if (x > 0)
            applyToEnemy(m, autoWeak(m, x));
        int y = getRandomNum(0, magicNumber);
        if (y > 0)
            applyToEnemy(m, autoVuln(m, getRandomNum(0, magicNumber)));
        // atb(new MuddleHandAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}