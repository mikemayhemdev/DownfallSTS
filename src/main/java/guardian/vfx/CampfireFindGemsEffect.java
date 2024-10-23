package guardian.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;

import java.util.ArrayList;
import java.util.Arrays;

public class CampfireFindGemsEffect extends AbstractGameEffect {
    private static final float DUR = 2.0F;
    private boolean got = false;

    private Color screenColor;

    public CampfireFindGemsEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.0F;
        this.screenColor.a = 0.0F;
        ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.updateBlackScreenColor();

        if (this.duration < 1.0F) {
            if(!got) {
                got = true;
                CardCrawlGame.sound.play("SHOVEL");
                ArrayList<AbstractCard> gems = new ArrayList<>(Arrays.asList(GuardianMod.getSingleRewardGemWithWeight())) ;
                if (gems != null && !gems.isEmpty()) {
                    AbstractDungeon.cardRewardScreen.open(gems, null, "");
                }

//                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(gems.get(0), (float) (Settings.WIDTH * 0.35), (float) (Settings.HEIGHT / 2)));
//                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(gems.get(1), (float) (Settings.WIDTH * 0.7), (float) (Settings.HEIGHT / 2)));
            }
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
            ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
//            AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
        }

    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.5F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
        } else if (this.duration < 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.screenColor.a = 1.0F;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
    }

    public void dispose() {
    }
}
