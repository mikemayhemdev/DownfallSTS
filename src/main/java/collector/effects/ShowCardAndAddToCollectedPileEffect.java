package collector.effects;

import collector.patches.CollectiblesPatches.SoulsPatches;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class ShowCardAndAddToCollectedPileEffect extends AbstractGameEffect {
    private AbstractCard card;

    public ShowCardAndAddToCollectedPileEffect(AbstractCard srcCard) {
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = 1.5F;
        this.card.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float) Settings.WIDTH * 0.9F);
        this.card.target_y = MathUtils.random((float) Settings.HEIGHT * 0.8F, (float) Settings.HEIGHT * 0.2F);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;
        AbstractDungeon.player.drawPile.addToRandomSpot(srcCard);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
            SoulsPatches.REDIRECT_SOULS_TO_COLLECTION = true;
            AbstractDungeon.getCurrRoom().souls.onToDeck(this.card, true, true);
            SoulsPatches.REDIRECT_SOULS_TO_COLLECTION = false;
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }

    }

    public void dispose() {
    }
}
