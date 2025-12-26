package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hermit.HermitMod;
import hermit.actions.ObtainPotionNotBlockedByCombatEndAction;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BartenderGlass extends CustomRelic {
    public static final String ID = HermitMod.makeID("BartenderGlass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bartenders_glass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bartenders_glass.png"));


    //variables
    private static final int POTION_LIMIT = 2;

    public BartenderGlass() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
        counter = -1;
    }

    public void obtain() {
        super.obtain();

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            counter = POTION_LIMIT;
        else
            counter = -1;
    }

    @Override
    public void atBattleStart() {
        flash();
        this.counter = POTION_LIMIT;
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.grayscale = false;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    public void onUsePotion() {
        this.flash();
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !grayscale) {
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ObtainPotionNotBlockedByCombatEndAction(AbstractDungeon.returnRandomPotion(true)));
            this.counter--;
            if (counter <= 0)
                grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        if ((Settings.language != Settings.GameLanguage.ZHS && Settings.language != Settings.GameLanguage.ZHT)) {
            return DESCRIPTIONS[0] + POTION_LIMIT + DESCRIPTIONS[2] + POTION_LIMIT + DESCRIPTIONS[3];
        } else {
            return DESCRIPTIONS[0] + POTION_LIMIT + DESCRIPTIONS[1];
        }
    }

}
