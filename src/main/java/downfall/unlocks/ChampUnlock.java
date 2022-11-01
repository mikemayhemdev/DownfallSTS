package downfall.unlocks;

import com.megacrit.cardcrawl.core.Settings;
import downfall.downfallMod;

public class ChampUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock {
    public static final String KEY = "Champ";

    public ChampUnlock() {
        this.type = UnlockType.CHARACTER;
        this.key = "Champ";
        this.title = "Champ";
    }

    public void onUnlockScreenOpen() {
        this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(downfallMod.Enums.THE_CHAMP);
        this.player.drawX = (Settings.WIDTH / 2.0F - 20.0F * Settings.scale);
        this.player.drawY = (Settings.HEIGHT / 2.0F - 150.0F * Settings.scale);
    }
}