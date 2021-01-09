package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class ShipIt extends AbstractBronzeCard {

    public final static String ID = makeID("ShipIt");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 5;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public ShipIt() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public static int countCards() {
        int count = 0;
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c.type == CardType.STATUS) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c.type == CardType.STATUS) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.discardPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c.type == CardType.STATUS) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.exhaustPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c.type == CardType.STATUS) {
                ++count;
            }
        }

        return count;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}