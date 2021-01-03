package downfall.unlocks;

import com.megacrit.cardcrawl.core.Settings;
import theHexaghost.TheHexaghost;

public class HexaghostUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock {
    public static final String KEY = "Hexaghost";

    public HexaghostUnlock() {
        this.type = UnlockType.CHARACTER;
        this.key = "Hexaghost";
        this.title = "Hexaghost";
    }

    public void onUnlockScreenOpen() {
        this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(TheHexaghost.Enums.THE_SPIRIT);
        this.player.drawX = (Settings.WIDTH / 2.0F + 20.0f * Settings.scale);
        this.player.drawY = (Settings.HEIGHT / 2.0F - 235.0F * Settings.scale);
        TheHexaghost hP = (TheHexaghost) this.player;
        hP.myBody.XOffset = 485F * Settings.scale;
        hP.myBody.YOffset = -40F * Settings.scale;


    }
}