package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class RetractBonusDamage extends AbstractHexaCard{
    public final static String ID = makeID("retract_bonus_damage");

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 3;

    public RetractBonusDamage() {
        super(ID, 1, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;

//        HexaMod.loadJokeCardImage(this, "Strike.png");
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * HexaMod.number_of_times_of_retracts_during_the_combat;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * HexaMod.number_of_times_of_retracts_during_the_combat;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE); //TODO: fancy unique vfx
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(UPG_DAMAGE);
        }
    }
}
