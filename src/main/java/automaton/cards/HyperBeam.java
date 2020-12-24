package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.powers.StunnedPower;

public class HyperBeam extends AbstractBronzeCard {

    public final static String ID = makeID("HyperBeam");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 45;
    private static final int UPG_DAMAGE = 9;

    public HyperBeam() {
        super(ID, 6, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        selfRetain = true;
    }

    public void onRetained() {
        this.addToBot(new ReduceCostAction(this));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE); //todo: copy the BLAAAAAAM
        applyToSelf(new StunnedPower(p));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}