package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Execute extends AbstractChampCard {

    public final static String ID = makeID("Execute");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 3;
    private static final int HP_LOSS = 5;

    public Execute() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = HP_LOSS;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        loseHP(this.magicNumber);
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        defensiveStance();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}