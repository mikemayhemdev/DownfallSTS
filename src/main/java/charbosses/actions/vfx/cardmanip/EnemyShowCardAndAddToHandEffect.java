package charbosses.actions.vfx.cardmanip;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class EnemyShowCardAndAddToHandEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.8f;
    private static final float PADDING;

    static {
        PADDING = 25.0f * Settings.scale;
    }

    private AbstractCard card;

    public EnemyShowCardAndAddToHandEffect(final AbstractCard card, final float offsetX, final float offsetY) {
        this.card = card;
        UnlockTracker.markCardAsSeen(card.cardID);
        card.current_x = Settings.WIDTH / 2.0f;
        card.current_y = Settings.HEIGHT / 2.0f;
        card.target_x = offsetX;
        card.target_y = offsetY;
        this.duration = 0.8f;
        card.drawScale = 0.75f;
        card.targetDrawScale = 0.75f;
        card.transparency = 0.01f;
        card.targetTransparency = 1.0f;
        card.fadingOut = false;
        this.playCardObtainSfx();
        if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            card.upgrade();
        }
        card.untip();
        AbstractCharBoss.boss.hand.group.add(0, card);
        card.triggerWhenCopied();
        AbstractCharBoss.boss.hand.refreshHandLayout();
        AbstractCharBoss.boss.hand.applyPowers();
        AbstractCharBoss.boss.onCardDrawOrDiscard();
        if (AbstractCharBoss.boss.hasPower("Corruption") && card.type == AbstractCard.CardType.SKILL) {
            card.setCostForTurn(-9);
        }
    }

    public EnemyShowCardAndAddToHandEffect(final AbstractCard card) {
        this.card = card;
        this.identifySpawnLocation(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f);
        this.duration = 0.8f;
        card.drawScale = 0.75f;
        card.targetDrawScale = 0.75f;
        card.transparency = 0.01f;
        card.targetTransparency = 1.0f;
        card.fadingOut = false;
        if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            card.upgrade();
        }
        card.untip();
        AbstractCharBoss.boss.hand.group.add(0, card);
        card.triggerWhenCopied();
        AbstractCharBoss.boss.hand.refreshHandLayout();
        AbstractCharBoss.boss.hand.applyPowers();
        AbstractCharBoss.boss.onCardDrawOrDiscard();
        if (AbstractCharBoss.boss.hasPower("Corruption") && card.type == AbstractCard.CardType.SKILL) {
            card.setCostForTurn(-9);
        }
    }

    private void playCardObtainSfx() {
        int effectCount = 0;
        for (final AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof EnemyShowCardAndAddToHandEffect) {
                ++effectCount;
            }
        }
        if (effectCount < 2) {
            CardCrawlGame.sound.play("CARD_OBTAIN");
        }
    }

    private void identifySpawnLocation(final float x, final float y) {
        int effectCount = 0;
        for (final AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof EnemyShowCardAndAddToHandEffect) {
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
                this.card.target_x = Settings.WIDTH * 0.5f - EnemyShowCardAndAddToHandEffect.PADDING - AbstractCard.IMG_WIDTH;
                break;
            }
            case 2: {
                this.card.target_x = Settings.WIDTH * 0.5f + EnemyShowCardAndAddToHandEffect.PADDING + AbstractCard.IMG_WIDTH;
                break;
            }
            case 3: {
                this.card.target_x = Settings.WIDTH * 0.5f - (EnemyShowCardAndAddToHandEffect.PADDING + AbstractCard.IMG_WIDTH) * 2.0f;
                break;
            }
            case 4: {
                this.card.target_x = Settings.WIDTH * 0.5f + (EnemyShowCardAndAddToHandEffect.PADDING + AbstractCard.IMG_WIDTH) * 2.0f;
                break;
            }
            default: {
                this.card.target_x = MathUtils.random(Settings.WIDTH * 0.1f, Settings.WIDTH * 0.9f);
                this.card.target_y = MathUtils.random(Settings.HEIGHT * 0.2f, Settings.HEIGHT * 0.8f);
                break;
            }
        }
        this.card.current_x = this.card.target_x;
        this.card.current_y = this.card.target_y - 200.0f * Settings.scale;
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0f) {
            this.isDone = true;
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
