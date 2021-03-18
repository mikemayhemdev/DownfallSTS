package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.actions.NoApplyRandomDamageAction;
import sneckomod.cards.unknowns.AbstractUnknownCard;

public class DiceCrush extends AbstractSneckoCard {

    public final static String ID = makeID("DiceCrush");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;

    private static final int MAGIC = 1;

    public DiceCrush() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
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
        for (AbstractCard q : AbstractDungeon.player.masterDeck.group) {
            if (q instanceof AbstractUnknownCard)
                baseDamage += 1;
        }
        super.applyPowers();
        baseDamage = CURRENT_DMG;
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
        for (AbstractCard q : AbstractDungeon.player.masterDeck.group) {
            if (q instanceof AbstractUnknownCard)
                baseDamage += 1;
        }
        super.calculateCardDamage(mo);
        baseDamage = CURRENT_DMG;
        isDamageModified = baseDamage != damage;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}