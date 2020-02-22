package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import evilWithin.EvilWithinMod;

public class TeleportStone extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("TeleportStone");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/BanditContract.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/BanditContract.png"));

    public TeleportStone() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
        this.counter = 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onTrigger() {
        this.counter = 0;
        this.usedUp();
    }
}
