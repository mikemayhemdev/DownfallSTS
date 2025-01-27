package collector.cards;

import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class FuelTheFire extends AbstractCollectorCard {
    public final static String ID = makeID(FuelTheFire.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 2, 1

    public FuelTheFire() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new NextTurnReservePower(magicNumber));
        if (upgraded) {
            applyToSelf(new DrawCardNextTurnPower(p, 1));
        }
    }

    public void upp() {
        uDesc();
    }
}