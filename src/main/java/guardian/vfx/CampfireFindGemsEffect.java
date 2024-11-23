package guardian.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class CampfireFindGemsEffect extends AbstractGameEffect {
    private static final float EFFECT_DURATION = 1.0f; // Short duration for free actions
    private final Color screenColor;

    public CampfireFindGemsEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = EFFECT_DURATION;
        this.screenColor.a = 0.0F;

        // Stop the campfire sound
        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
        }
    }

    @Override
    public void update() {
        this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        updateBlackScreenColor();

        // Effect completion logic
        if (this.duration < 0.0F) {
            CardCrawlGame.sound.play("SHOVEL");

            // Obtain gem cards
            ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 2);
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
                    gems.get(0),
                    Settings.WIDTH * 0.35F,
                    Settings.HEIGHT / 2.0F
            ));
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
                    gems.get(1),
                    Settings.WIDTH * 0.7F,
                    Settings.HEIGHT / 2.0F
            ));

            CampfireUI campfire = ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI;


                campfire.somethingSelected = false;
                campfire.touchOption = null;
                campfire.confirmButton.hide();
                campfire.confirmButton.hideInstantly();
                campfire.confirmButton.isDisabled = true;

                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;

            this.isDone = true; // Mark effect as completed
        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > 0.5F) {
            screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - 0.5F) * 2.0F);
        } else {
            screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, duration);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    @Override
    public void dispose() {
        // Cleanup resources (if any)
    }
}
