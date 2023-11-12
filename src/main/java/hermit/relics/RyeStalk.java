package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class RyeStalk extends CustomRelic {
    public static final String ID = HermitMod.makeID("RyeStalk");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("rye_stalk.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("rye_stalk.png"));

    public RyeStalk() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    public void wasHPLost(int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0 && AbstractDungeon.actionManager.turnHasEnded) {
            this.flash();
            this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
