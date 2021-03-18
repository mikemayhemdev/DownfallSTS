package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.NoApplyRandomDamageAction;

public class SnakeSap extends AbstractSneckoCard {

    public final static String ID = makeID("SnakeSap");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public SnakeSap() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseSilly = silly = 1;
        tags.add(SneckoMod.SNEKPROOF);
        tags.add(SneckoMod.RNG);
    }

    @Override
    public void applyPowers() {
        int CURRENT_SILLY = baseSilly;
        int CURRENT_DAMAGE = baseDamage;
        baseDamage = CURRENT_SILLY;
        super.applyPowers();
        silly = damage;
        isSillyModified = damage != baseDamage;

        baseDamage = CURRENT_DAMAGE;
        super.applyPowers();
        this.exhaust = true;
    }

    @Override
    public void calculateCardDamage(final AbstractMonster m) {
        int CURRENT_SILLY = baseSilly;
        int CURRENT_DAMAGE = baseDamage;
        baseDamage = CURRENT_SILLY;
        super.calculateCardDamage(m);
        silly = damage;
        isSillyModified = damage != baseDamage;

        baseDamage = CURRENT_DAMAGE;
        super.calculateCardDamage(m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new NoApplyRandomDamageAction(m, silly, damage, 1, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, this, DamageInfo.DamageType.NORMAL));
        int x = getRandomNum(1, magicNumber, this);
        if (x > 0)
            atb(new GainEnergyAction(x));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            upgradeDamage(1);
        }
    }
}