package downfall.unlocks;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import gremlin.patches.GremlinEnum;

public class GremlinUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock {
    public static final String KEY = "Gremlin";

    public GremlinUnlock() {
        this.type = AbstractUnlock.UnlockType.CHARACTER;
        this.key = KEY;
        this.title = KEY;
    }

    public void onUnlockScreenOpen() {
        this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(GremlinEnum.GREMLIN);
        this.player.drawX = (Settings.WIDTH / 2.0F - 20.0F * Settings.scale);
        this.player.drawY = (Settings.HEIGHT / 2.0F - 150.0F * Settings.scale);
    }

}
