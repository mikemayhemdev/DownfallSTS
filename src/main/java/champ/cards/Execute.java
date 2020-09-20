package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Execute extends AbstractChampCard {

    public final static String ID = makeID("Execute");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 2;
    private static final int HP_LOSS = 4;

    public Execute() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = HP_LOSS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        loseHP(this.magicNumber);
        applyToSelf(new ResolvePower(4));
        finisher();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}