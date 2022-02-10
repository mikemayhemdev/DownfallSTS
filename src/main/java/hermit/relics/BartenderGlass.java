package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import hermit.HermitMod;
import hermit.powers.RyeStalkPower;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BartenderGlass extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("BartenderGlass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bartenders_glass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bartenders_glass.png"));

    public BartenderGlass() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
        counter=-1;
    }

    public void obtain() {
        super.obtain();

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            counter = 2;
        else
            counter = -1;
    }

    @Override
    public void atBattleStart() {
        flash();
        this.counter=2;
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.grayscale=false;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale=false;
    }

    public void onUsePotion() {
        this.flash();
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !grayscale) {
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
            this.counter--;
            if (counter <= 0)
                grayscale=true;
        } //else {
        //    AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(AbstractDungeon.returnRandomPotion()));
        //}
    }


    // Description
    @Override
    public String getUpdatedDescription() {
            return DESCRIPTIONS[0];
    }

}
