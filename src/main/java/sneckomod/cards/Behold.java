package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
        int orig = baseDamage;
        int additionalDamage = (p.hand.size() - 1) * magicNumber;
        int totalDamage = this.baseDamage + additionalDamage;
        baseDamage = totalDamage;
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        baseDamage = orig;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_EXTRA_DAMAGE);
        }
    }
}
