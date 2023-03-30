package guardian.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

import java.util.Iterator;

//Copied from vanilla ShowCardAndObtainEffect , except it add the card to your deck but then remove from it instantly (on save&load
// on floor 0, the gem disappears, so I used another s&l friendly way to add the gem)

public class AddGemToStartingDeckEffect extends AbstractGameEffect {
    private AbstractCard card;
    private static final float PADDING;
    private boolean converge;

    public AddGemToStartingDeckEffect(AbstractCard card, float x, float y, boolean convergeCards) {

        UnlockTracker.markCardAsSeen(card.cardID);
        CardHelper.obtain(card.cardID, card.rarity, card.color);
        this.card = card;
        if (Settings.FAST_MODE) {
            this.duration = 0.5F;
        } else {
            this.duration = 2.0F;
        }

        this.identifySpawnLocation(x, y);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
    }

    public AddGemToStartingDeckEffect(AbstractCard card, float x, float y) {
        this(card, x, y, true);
    }

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        Iterator var4 = AbstractDungeon.effectList.iterator();

        while(var4.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)var4.next();
            if (e instanceof AddGemToStartingDeckEffect) {
                ++effectCount;
            }
        }

        this.card.current_x = x;
        this.card.current_y = y;
        if (this.converge) {
            this.card.target_y = (float)Settings.HEIGHT * 0.5F;
            switch(effectCount) {
                case 0:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F;
                    break;
                case 1:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
                    break;
                case 2:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
                    break;
                case 3:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                    break;
                case 4:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                    break;
                default:
                    this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
                    this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
            }
        } else {
            this.card.target_x = this.card.current_x;
            this.card.target_y = this.card.current_y;
        }

    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            Iterator var1 = AbstractDungeon.player.relics.iterator();

            AbstractRelic r;
            while(var1.hasNext()) {
                r = (AbstractRelic)var1.next();
                r.onObtainCard(this.card);
            }

            this.isDone = true;
            this.card.shrink();
            AbstractDungeon.getCurrRoom().souls.obtain(this.card, true);
            AbstractDungeon.player.masterDeck.removeCard(this.card);
            var1 = AbstractDungeon.player.relics.iterator();

            while(var1.hasNext()) {
                r = (AbstractRelic)var1.next();
                r.onMasterDeckChange();
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

    static {
        PADDING = 30.0F * Settings.scale;
    }
}
