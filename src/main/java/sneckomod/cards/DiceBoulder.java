package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.CardIgnore;
import sneckomod.actions.NoApplyRandomDamageAction;

@CardIgnore
public class DiceBoulder extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBoulder");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 30;
    private static final int MAGIC = 1;

    public DiceBoulder(int eugene) {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        timesUpgraded = eugene;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new NoApplyRandomDamageAction(m, magicNumber, damage, 1, AbstractGameAction.AttackEffect.BLUNT_HEAVY, this));
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        int CURRENT_MAGIC_NUMBER = baseMagicNumber;
        int CURRENT_DMG = baseDamage;
        baseDamage = CURRENT_MAGIC_NUMBER;
        super.calculateCardDamage(mo); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = magicNumber != baseMagicNumber;

        // repeat so damage holds the second condition's damage
        baseDamage = CURRENT_DMG;
        super.calculateCardDamage(mo);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DiceBoulder(this.timesUpgraded);
    }

    public void upgrade() {
        this.upgradeDamage(4);// 49
        upgradeMagicNumber(8);

        ++this.timesUpgraded;// 50
        this.upgraded = true;// 51
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;// 52
        this.initializeTitle();// 53
    }
}