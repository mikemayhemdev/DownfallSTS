package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.BurnAction;
import theHexaghost.powers.BurnPower;

public class Incineration extends AbstractHexaCard {

    public final static String ID = makeID("Incineration");

    //stupid intellij stuff ATTACK, ALL_ENEMY, COMMON

    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Incineration() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    addToTop(new DamageAction(m, makeInfo(), AttackEffect.FIRE));
                    addToTop(new BurnAction(m, 1));
                }
            });
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}