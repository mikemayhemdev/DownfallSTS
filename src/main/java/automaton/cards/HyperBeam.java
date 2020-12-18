package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.powers.StunnedPower;

public class HyperBeam extends AbstractBronzeCard {

    public final static String ID = makeID("HyperBeam");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 45;
    private static final int UPG_DAMAGE = 5;

    public HyperBeam() {
        super(ID, 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;
        isMultiDamage = true;
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        if (player.hasPower(StrengthPower.POWER_ID) && this.magicNumber > 1) {
            bonus = player.getPower(StrengthPower.POWER_ID).amount * (this.magicNumber - 1);
        }
        return tmp + bonus;
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, float tmp) {
        int bonus = 0;
        if (player.hasPower(StrengthPower.POWER_ID)) {
            bonus = player.getPower(StrengthPower.POWER_ID).amount * (this.magicNumber + 1);
        }
        return tmp + bonus;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE); //todo: copy the BLAAAAAAM
        applyToSelf(new StunnedPower(p));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(1);
    }
}