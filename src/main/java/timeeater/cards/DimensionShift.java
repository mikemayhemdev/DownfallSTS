package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.DimensionShiftPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class DimensionShift extends AbstractTimeEaterCard {
    public final static String ID = makeID("DimensionShift");
    // intellij stuff attack, enemy, uncommon, 10, , , , 1, 1

    public DimensionShift() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applyToSelf(new DimensionShiftPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}