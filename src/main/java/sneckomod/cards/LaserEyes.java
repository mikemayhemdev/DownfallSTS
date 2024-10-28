package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class LaserEyes extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("SnekBeam");

    private static final int COST = 3;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 5;

    public LaserEyes() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "SnekBeam.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage to the targeted enemy
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        // Gain 1 energy
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE); // Increase base damage by 5 (to 20)
        }
    }
}
