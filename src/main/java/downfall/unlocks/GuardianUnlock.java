package downfall.unlocks;

import com.megacrit.cardcrawl.core.Settings;
import guardian.patches.GuardianEnum;

public class GuardianUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock {
    public static final String KEY = "Guardian";

    public GuardianUnlock() {
        this.type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CHARACTER;
        this.key = "Guardian";
        this.title = "Guardian";
    }

    public void onUnlockScreenOpen() {
        this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(GuardianEnum.GUARDIAN);
        this.player.drawX = (Settings.WIDTH / 2.0F - 20.0F * Settings.scale);
        this.player.drawY = (Settings.HEIGHT / 2.0F - 118.0F * Settings.scale);
    }
}