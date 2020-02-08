package guardian.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;


public class AddCardToStasisEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.5F;
    private static final float PADDING;

    static {
        PADDING = 30.0F * Settings.scale;
    }

    private AbstractCard card;
    private StasisOrb o;
    private boolean halfwayHit;
    private boolean glowStartHit;

    public AddCardToStasisEffect(AbstractCard srcCard, StasisOrb o, float startx, float starty, float targetx, float targety) {
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = 1.5F;
        this.card.current_x = startx;
        this.card.current_y = starty;
        this.card.target_x = targetx;
        this.card.target_y = targety;
        this.o = o;
        //AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.1F;
        this.card.targetDrawScale = 0.75F;
        //CardCrawlGame.sound.play("CARD_OBTAIN");
        //AbstractDungeon.player.discardPile.addToTop(srcCard);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();

        ;
        if (this.duration < 1.1F) {
            if (!glowStartHit) {
                this.card.beginGlowing();
                this.card.tags.add(GuardianMod.STASISGLOW);
                this.card.superFlash(Color.GOLDENROD);
                glowStartHit = true;
            }

            if (this.duration < 0.75F) {
                //this.card.update();
                if (!halfwayHit) {
                    this.card.target_x = o.tX;
                    this.card.target_y = o.tY;
                    this.card.targetDrawScale = GuardianMod.stasisCardRenderScale;
                    halfwayHit = true;
                }

                if (this.duration < 0.0F) {
                    this.isDone = true;
                    //this.card.drawScale();
                    //AbstractDungeon.getCurrRoom().souls.discard(this.card, true);
                }
            }

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
