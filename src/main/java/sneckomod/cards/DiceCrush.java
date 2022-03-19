package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.NoApplyRandomDamageAction;

public class DiceCrush extends AbstractSneckoCard {

    public final static String ID = makeID("DiceCrush");

    public DiceCrush() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 16;
        baseMagicNumber = magicNumber = 10;
        tags.add(SneckoMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new NoApplyRandomDamageAction(m, magicNumber, damage, 1, AbstractGameAction.AttackEffect.SMASH, this, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void applyPowers() {
        int CURRENT_MAGIC_NUMBER = baseMagicNumber;
        int CURRENT_DMG = baseDamage;
        baseDamage = CURRENT_MAGIC_NUMBER;
        super.applyPowers(); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = magicNumber != baseMagicNumber;

        // repeat so damage holds the second condition's damage
        baseDamage = CURRENT_DMG;
        super.applyPowers();
        isDamageModified = baseDamage != damage;
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
        isDamageModified = baseDamage != damage;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            upgradeDamage(4);
        }
    }
}