package collector.cards;

import collector.actions.GainReservesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class Spark extends AbstractCollectorCard {
    public final static String ID = makeID(Spark.class.getSimpleName());
    // intellij stuff skill, self, special, , , , , , 

    public Spark() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainReservesAction(1));
        if (upgraded) {
            atb(new DrawCardAction(1));
        }
    }

    public void upp() {
        uDesc();
    }
}