package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.NoApplyRandomDamageAction;

public class DiceBoulder extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBoulder");

    public DiceBoulder() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 31;
        baseMagicNumber = magicNumber = 1;
        tags.add(SneckoMod.RNG);
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
        this.upgradeDamage(3 + timesUpgraded);// 49
        upgradeMagicNumber(7 + timesUpgraded);
        ++this.timesUpgraded;// 50
        this.upgraded = true;// 51
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;// 52
        this.initializeTitle();// 53
    }
}