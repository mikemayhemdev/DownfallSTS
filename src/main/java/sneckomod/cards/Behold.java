package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Behold extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Behold");

    private static final int BASE_DAMAGE = 6;
    private static final int EXTRA_DAMAGE = 1;
    private static final int UPGRADE_EXTRA_DAMAGE = 1;

    public Behold() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        baseMagicNumber = magicNumber = EXTRA_DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;

        // Calculate additional damage based on the number of cards in hand
        int additionalDamage = (AbstractDungeon.player.hand.size() - 1) * this.magicNumber;
        this.baseDamage += additionalDamage;

        super.applyPowers();

        // Reset baseDamage to avoid permanent modification
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;

        // Calculate additional damage based on the number of cards in hand
        int additionalDamage = (AbstractDungeon.player.hand.size() - 1) * this.magicNumber;
        this.baseDamage += additionalDamage;

        super.calculateCardDamage(mo);

        // Reset baseDamage to avoid permanent modification
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_EXTRA_DAMAGE);
        }
    }
}
