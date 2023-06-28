package collector.cards;

import collector.actions.GainReservesAction;
import collector.util.NewReserves;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class StashAway extends AbstractCollectorCard {
    public final static String ID = makeID(StashAway.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public StashAway() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainReservesAction(1));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                att(new GainReservesAction(NewReserves.reserveCount()));
            }
        });
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}