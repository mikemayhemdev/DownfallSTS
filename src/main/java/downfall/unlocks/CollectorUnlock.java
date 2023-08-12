package downfall.unlocks;

import collector.CollectorChar;
import com.megacrit.cardcrawl.core.Settings;

public class CollectorUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock {
    public static final String KEY = "Collector";

    public CollectorUnlock() {
        this.type = UnlockType.CHARACTER;
        this.key = "Collector";
        this.title = "Collector";
    }

    public void onUnlockScreenOpen() {
        this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(CollectorChar.Enums.THE_COLLECTOR);
        this.player.drawX = (Settings.WIDTH / 2.0F - 20.0F * Settings.scale);
        this.player.drawY = (Settings.HEIGHT / 2.0F - 118.0F * Settings.scale);
    }
}