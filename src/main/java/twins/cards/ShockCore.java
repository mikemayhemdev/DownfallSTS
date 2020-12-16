package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShockCore extends AbstractTwinsCard {

    public final static String ID = makeID("ShockCore");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 2;

    public ShockCore() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        tags.add(DonuDecaMod.CORE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.LIGHTNING);
        atb(new GainEnergyAction(1));
    }

    public void upp() {
        upgradeDamage(2);
    }
}