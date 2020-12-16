package twins.cards;

import twins.DonuDecaMod;
import twins.TwinsHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArrayShot extends AbstractTwinsCard {

    public final static String ID = makeID("ArrayShot");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 12;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public ArrayShot() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(DonuDecaMod.BLASTER);
        tags.add(DonuDecaMod.BURNOUT);
    }

    @java.lang.Override //Yes, I made a card called Override specifically to make it so these Overrides look silly
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * TwinsHelper.blasters.size();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @java.lang.Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * TwinsHelper.blasters.size();
        super.calculateCardDamage(mo);
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