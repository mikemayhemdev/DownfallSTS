package timeEater.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import timeEater.ClockHelper;
import timeEater.TimeEaterMod;

public class OldWatch extends AbstractTimeRelic implements OnTickRelic {
    public static final String ID = TimeEaterMod.makeID("OldWatch");

    public OldWatch() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onTick() {
        if (ClockHelper.clock == 12) {
            flash();
            applyToSelf(new StrengthPower(AbstractDungeon.player, 1));
        }
    }
}
