package charbosses.actions.vfx.cardmanip;

import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;
import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class EnemyShowCardAndAddToDiscardEffect extends AbstractGameEffect
{
    private static final float EFFECT_DUR = 1.5f;
    private AbstractCard card;
    private static final float PADDING;
    
    public EnemyShowCardAndAddToDiscardEffect(final AbstractCard srcCard, final float x, final float y) {
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = 1.5f;
        this.card.target_x = x;
        this.card.target_y = y;
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.75f;
        this.card.targetDrawScale = 0.75f;
        CardCrawlGame.sound.play("CARD_OBTAIN");
        if (this.card.type != AbstractCard.CardType.CURSE && this.card.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            this.card.upgrade();
        }
        AbstractCharBoss.boss.discardPile.addToTop(srcCard);
    }
    
    public EnemyShowCardAndAddToDiscardEffect(final AbstractCard card) {
        this.card = card;
        this.duration = 1.5f;
        this.identifySpawnLocation(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01f;
        card.targetDrawScale = 1.0f;
        if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            card.upgrade();
        }
        AbstractCharBoss.boss.discardPile.addToTop(card);
    }
    
    private void identifySpawnLocation(final float x, final float y) {
        int effectCount = 0;
        for (final AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof EnemyShowCardAndAddToDiscardEffect) {
                ++effectCount;
            }
        }
        this.card.target_y = Settings.HEIGHT * 0.5f;
        switch (effectCount) {
            case 0: {
                this.card.target_x = Settings.WIDTH * 0.5f;
                break;
            }
            case 1: {
                this.card.target_x = Settings.WIDTH * 0.5f - EnemyShowCardAndAddToDiscardEffect.PADDING - AbstractCard.IMG_WIDTH;
                break;
            }
            case 2: {
                this.card.target_x = Settings.WIDTH * 0.5f + EnemyShowCardAndAddToDiscardEffect.PADDING + AbstractCard.IMG_WIDTH;
                break;
            }
            case 3: {
                this.card.target_x = Settings.WIDTH * 0.5f - (EnemyShowCardAndAddToDiscardEffect.PADDING + AbstractCard.IMG_WIDTH) * 2.0f;
                break;
            }
            case 4: {
                this.card.target_x = Settings.WIDTH * 0.5f + (EnemyShowCardAndAddToDiscardEffect.PADDING + AbstractCard.IMG_WIDTH) * 2.0f;
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
            //AbstractDungeon.getCurrRoom().souls.discard(this.card, true);
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
    
    static {
        PADDING = 30.0f * Settings.scale;
    }
}
