package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class TheDivineGem extends AbstractAwakenedCard {
    public final static String ID = makeID(TheDivineGem.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 12, 4, , , 2, 1

    public TheDivineGem() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 12;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmount();
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        if (amt >= 1) {
            applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        }
        if (amt >= 3) {
            atb(new GainEnergyAction(1));
            atb(new DrawCardAction(1));
        }
        if (amt >= 5) {
            applyToSelf(new StrengthPower(p, magicNumber));
        }
    }

    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}