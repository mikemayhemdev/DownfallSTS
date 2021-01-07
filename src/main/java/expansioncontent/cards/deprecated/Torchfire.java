package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class Torchfire extends AbstractExpansionCard {
    public final static String ID = makeID("Torchfire");

    private static final int DAMAGE = 14;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public Torchfire() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        tags.add(expansionContentMod.STUDY_COLLECTOR);
        tags.add(expansionContentMod.STUDY);

        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = DAMAGE;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));


    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;

        for (AbstractPower p : mo.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                baseDamage += this.magicNumber;
            }
        }

        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }// 79

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

}

