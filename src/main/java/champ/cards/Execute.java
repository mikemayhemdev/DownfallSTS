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
        myHpLossCost = 4;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        finisher();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        loseHP(this.magicNumber);
        applyToSelf(new ResolvePower(4));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}