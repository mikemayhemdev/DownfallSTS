package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class DiceCrush extends AbstractSneckoCard {

    public final static String ID = makeID("DiceCrush");

    //stupid intellij stuff ATTACK, ENEMY, BASIC

    private static final int DAMAGE = 18;
    private static final int MAGIC = 2;
    private static final int UPG_DAMAGE = 4;

    public DiceCrush() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "DiceCrush.png");
        this.tags.add(SneckoMod.OVERFLOW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (isOverflowActive(this)) {
            atb(new DrawCardAction(magicNumber));
        }
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}