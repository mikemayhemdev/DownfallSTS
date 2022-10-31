package downfall.unlocks;

import com.megacrit.cardcrawl.core.Settings;
import sneckomod.TheSnecko;

public class SneckoUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock {
    public static final String KEY = "Snecko";

    public SneckoUnlock() {
        this.type = UnlockType.CHARACTER;
        this.key = "Snecko";
        this.title = "Snecko";
    }

    public void onUnlockScreenOpen() {
        this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(TheSnecko.Enums.THE_SNECKO);
        this.player.drawX = (Settings.WIDTH / 2.0F - 20.0F * Settings.scale);
        this.player.drawY = (Settings.HEIGHT / 2.0F - 118.0F * Settings.scale);
    }
}