package downfall.unlocks;

import awakenedOne.AwakenedOneChar;
import collector.CollectorChar;
import com.megacrit.cardcrawl.core.Settings;

public class AwakenedUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock {
    public static final String KEY = "Awakened";

    public AwakenedUnlock() {
        this.type = UnlockType.CHARACTER;
        this.key = "Awakened";
        this.title = "Awakened";
    }

    public void onUnlockScreenOpen() {
        this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(AwakenedOneChar.Enums.AWAKENED_ONE);
        this.player.drawX = (Settings.WIDTH / 2.0F - 20.0F * Settings.scale);
        this.player.drawY = (Settings.HEIGHT / 2.0F - 118.0F * Settings.scale);
    }
}