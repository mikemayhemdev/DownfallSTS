package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemy;
import static awakenedOne.util.Wiz.applyToSelf;

public class Punishment extends AbstractAwakenedCard {
    public final static String ID = makeID(Punishment.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 14, 2, , , 2, 1

    public Punishment() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 16;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (m.hasPower(WeakPower.POWER_ID) && m.hasPower(VulnerablePower.POWER_ID)) {
            applyToEnemy(m, new StrengthPower(m, -magicNumber));
            applyToSelf(new StrengthPower(p, magicNumber));
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}