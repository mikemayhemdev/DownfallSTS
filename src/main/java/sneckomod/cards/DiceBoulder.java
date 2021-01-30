package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.actions.NoApplyRandomDamageAction;

public class DiceBoulder extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBoulder");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 30;
    private static final int MAGIC = 1;

    public DiceBoulder() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new NoApplyRandomDamageAction(m, magicNumber, damage, 1, AbstractGameAction.AttackEffect.BLUNT_HEAVY, this, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void applyPowers() {
        int maxDamage = baseDamage;
        baseDamage = baseMagicNumber;
        super.applyPowers();

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = isDamageModified;

        baseDamage = maxDamage;
        super.applyPowers();

        // just-in-case-but-this-should-be-impossible code
        if (magicNumber > damage)
        {
            magicNumber = damage;
            isMagicNumberModified = magicNumber != baseMagicNumber;
        }
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        int maxDamage = baseDamage;
        baseDamage = baseMagicNumber;
        super.calculateCardDamage(mo); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = isDamageModified;

        // repeat so damage holds the second condition's damage
        baseDamage = maxDamage;
        super.calculateCardDamage(mo);

        // just-in-case-but-this-should-be-impossible code
        if (magicNumber > damage)
        {
            magicNumber = damage;
            isMagicNumberModified = magicNumber != baseMagicNumber;
        }
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DiceBoulder();
    }

    public void upgrade() {
        this.upgradeDamage(4);// 49
        upgradeMagicNumber(8);
        if (baseMagicNumber > baseDamage) {
            baseMagicNumber = baseDamage;
            magicNumber = baseMagicNumber;
        }

        ++this.timesUpgraded;// 50
        this.upgraded = true;// 51
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;// 52
        this.initializeTitle();// 53
    }
}