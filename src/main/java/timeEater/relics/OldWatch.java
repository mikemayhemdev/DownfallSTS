package timeEater.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeEater.TimeEaterMod;
import timeEater.actions.TickAction;

public class OldWatch extends AbstractTimeRelic {
    public static final String ID = TimeEaterMod.makeID("OldWatch");

    public OldWatch() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        flash();
        addToBot(new TickAction());
    }
}
