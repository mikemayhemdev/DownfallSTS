package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.actions.TriggerStartOfTurnEffectsAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class BeginAgain extends AbstractTimeEaterCard {
    public final static String ID = makeID("BeginAgain");
    // intellij stuff skill, self, uncommon, , , , , , 

    public BeginAgain() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new TriggerStartOfTurnEffectsAction(p));
    }

    public void upp() {
        exhaust = false;
        uDesc();
    }
}