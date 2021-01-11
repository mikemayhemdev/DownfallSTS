package charbosses.actions.vfx.cardmanip;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class EnemyShowCardAndAddToDrawPileEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.5f;
    private static final float PADDING;

    static {
        PADDING = 30.0f * Settings.scale;
    }

    private AbstractCard card;
    private boolean randomSpot;
    private boolean cardOffset;

    public EnemyShowCardAndAddToDrawPileEffect(final AbstractCard srcCard, final float x, final float y, final boolean randomSpot, final boolean cardOffset, final boolean toBottom) {
        this.randomSpot = false;
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.cardOffset = cardOffset;
        this.duration = 1.5f;
        this.randomSpot = randomSpot;
        if (cardOffset) {
            this.identifySpawnLocation(x, y);
        } else {
            this.card.target_x = x;
            this.card.target_y = y;
        }
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01f;
        this.card.targetDrawScale = 1.0f;
        if (this.card.type != AbstractCard.CardType.CURSE && this.card.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.card.upgrade();
        }
        CardCrawlGame.sound.play("CARD_OBTAIN");

    }

    public EnemyShowCardAndAddToDrawPileEffect(final AbstractCard srcCard, final float x, final float y, final boolean randomSpot, final boolean cardOffset) {
        this(srcCard, x, y, randomSpot, cardOffset, false);
    }

    public EnemyShowCardAndAddToDrawPileEffect(final AbstractCard srcCard, final float x, final float y, final boolean randomSpot) {
        this(srcCard, x, y, randomSpot, false);
    }

    public EnemyShowCardAndAddToDrawPileEffect(final AbstractCard srcCard, final boolean randomSpot, final boolean toBottom) {
        this.randomSpot = false;
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = 1.5f;
        this.randomSpot = randomSpot;
        this.card.target_x = MathUtils.random(Settings.WIDTH * 0.1f, Settings.WIDTH * 0.9f);
        this.card.target_y = MathUtils.random(Settings.HEIGHT * 0.8f, Settings.HEIGHT * 0.2f);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01f;
        this.card.targetDrawScale = 1.0f;
    }

    private void identifySpawnLocation(final float x, final float y) {
        int effectCount = 0;
        if (this.cardOffset) {
            effectCount = 1;
        }
        for (final AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof EnemyShowCardAndAddToDrawPileEffect) {
                ++effectCount;
            }
        }
        this.card.current_x = x;
        this.card.current_y = y;
        this.card.target_y = Settings.HEIGHT * 0.5f;
        switch (effectCount) {
            case 0: {
                this.card.target_x = Settings.WIDTH * 0.5f;
                break;
            }
            case 1: {
                this.card.target_x = Settings.WIDTH * 0.5f - EnemyShowCardAndAddToDrawPileEffect.PADDING - AbstractCard.IMG_WIDTH;
                break;
            }
            case 2: {
                this.card.target_x = Settings.WIDTH * 0.5f + EnemyShowCardAndAddToDrawPileEffect.PADDING + AbstractCard.IMG_WIDTH;
                break;
            }
            case 3: {
                this.card.target_x = Settings.WIDTH * 0.5f - (EnemyShowCardAndAddToDrawPileEffect.PADDING + AbstractCard.IMG_WIDTH) * 2.0f;
                break;
            }
            case 4: {
                this.card.target_x = Settings.WIDTH * 0.5f + (EnemyShowCardAndAddToDrawPileEffect.PADDING + AbstractCard.IMG_WIDTH) * 2.0f;
                break;
            }
            default: {
                this.card.target_x = MathUtils.random(Settings.WIDTH * 0.1f, Settings.WIDTH * 0.9f);
                this.card.target_y = MathUtils.random(Settings.HEIGHT * 0.2f, Settings.HEIGHT * 0.8f);
                break;
            }
        }
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0f) {
            this.isDone = true;
            this.card.shrink();
            //AbstractDungeon.getCurrRoom().souls.onToDeck(this.card, this.randomSpot, true);
        }
    }

    @Override
    public void render(final SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }
    }

    @Override
    public void dispose() {
    }
}
