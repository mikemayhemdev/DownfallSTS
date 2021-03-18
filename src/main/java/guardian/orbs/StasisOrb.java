package guardian.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;
import guardian.actions.ReturnStasisCardToHandAction;
import guardian.actions.StasisEvokeIfRoomInHandAction;
import guardian.cards.InStasisCard;
import guardian.relics.StasisUpgradeRelic;
import guardian.vfx.AddCardToStasisEffect;


public class StasisOrb extends AbstractOrb {
    public static final String[] DESC;
    private static final OrbStrings orbString;
    public static final String ID = GuardianMod.makeID("StasisOrb");

    static {
        orbString = CardCrawlGame.languagePack.getOrbString(ID);
        DESC = orbString.DESCRIPTION;
    }

    public AbstractCard stasisCard;
    private AbstractGameEffect stasisStartEffect;

    public StasisOrb(AbstractCard card) {
        this(card, null);
    }

    public StasisOrb(AbstractCard card, CardGroup source) {
        this(card, source, false);
    }

    public StasisOrb(AbstractCard card, CardGroup source, boolean selfStasis) {
        this.stasisCard = card;
        GuardianMod.logger.info("New Stasis Orb made");
        this.stasisCard.beginGlowing();
        this.name = orbString.NAME + stasisCard.name;

        this.channelAnimTimer = 0.5F;
        if (card.isCostModifiedForTurn) {
            this.basePassiveAmount = this.passiveAmount = card.costForTurn + 1;
        } else {
            this.basePassiveAmount = this.passiveAmount = card.cost + 1;
        }

        if (card.freeToPlay() && !selfStasis) {
            this.basePassiveAmount = this.passiveAmount = 1;
        }

        if (this.basePassiveAmount < 1) {
            this.basePassiveAmount = this.passiveAmount = 1;
        }

//        if(card.cost == -1 && AbstractDungeon.player != null){
//            this.basePassiveAmount = this.passiveAmount = AbstractDungeon.player.energy.energy + 1;
//        }

        this.baseEvokeAmount = this.basePassiveAmount;
        this.evokeAmount = this.passiveAmount;
        card.targetAngle = 0F;

        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasRelic(StasisUpgradeRelic.ID)) {
                card.upgrade();
            }
        }
        this.stasisCard.tags.add(GuardianMod.STASISGLOW);
        this.updateDescription();

        initialize(source, selfStasis);
    }

    @Override
    public void applyFocus() {
        //FuturePlans orbs are not affected by Focus
    }

    public void updateDescription() {
        this.applyFocus();
        if (this.stasisCard.hasTag(GuardianMod.VOLATILE)) {
            if (this.passiveAmount > 1) {
                this.description = this.stasisCard.name + DESC[4] + this.passiveAmount + DESC[5];
            } else {
                this.description = this.stasisCard.name + DESC[3];
            }
        } else {
            if (this.passiveAmount > 1) {
                this.description = this.stasisCard.name + DESC[1] + this.passiveAmount + DESC[2];
            } else {
                this.description = this.stasisCard.name + DESC[0];
            }
        }
    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        if (GuardianMod.stasisDelay) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(1.4F));
            GuardianMod.stasisDelay = false;
        }

        if (stasisCard instanceof InStasisCard) {
            stasisCard.applyPowers();
            ((InStasisCard) stasisCard).onStartOfTurn(this);
        }

        if (this.passiveAmount > 0) {
            this.passiveAmount -= 1;
            this.evokeAmount -= 1;

            updateDescription();
        }

        if (this.passiveAmount <= 0) {
            AbstractDungeon.actionManager.addToTop(new StasisEvokeIfRoomInHandAction(this));
        }
    }

    public void onEvoke() {
        if (this.stasisCard instanceof InStasisCard) {
            ((InStasisCard) this.stasisCard).onEvoke(this);
        }

        if (this.stasisCard.hasTag(GuardianMod.VOLATILE)) {
            AbstractDungeon.player.limbo.addToTop(this.stasisCard);
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this.stasisCard, AbstractDungeon.player.limbo));
        } else {
            if (this.passiveAmount <= 0) {
                if (stasisCard.cost > 0) {
                    stasisCard.freeToPlayOnce = true;
                } else {
                    stasisCard.tags.remove(GuardianMod.STASISGLOW);
                }
            } else {
                stasisCard.tags.remove(GuardianMod.STASISGLOW);
            }
            AbstractDungeon.actionManager.addToTop(new ReturnStasisCardToHandAction(this.stasisCard));
            this.stasisCard.superFlash(Color.GOLDENROD);

        }
        //GuardianMod.updateStasisCount();
    }

    public void triggerEvokeAnimation() {
    }

    public void updateAnimation() {
        super.updateAnimation();
    }

    private void initialize(CardGroup source, boolean selfStasis) {
        if (source != null) {
            source.removeCard(stasisCard);

            switch (source.type) {
                case HAND:
                    stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, stasisCard.current_x, stasisCard.current_y, !selfStasis);
                    break;
                case DRAW_PILE:
                    stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, AbstractDungeon.overlayMenu.combatDeckPanel.current_x + (100F * Settings.scale), AbstractDungeon.overlayMenu.combatDeckPanel.current_y + (100F * Settings.scale), !selfStasis);
                    break;
                case DISCARD_PILE:
                    stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (100F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (100F * Settings.scale), !selfStasis);
                    break;
                case EXHAUST_PILE:
                    stasisCard.unfadeOut();
                    stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (100F * Settings.scale), AbstractDungeon.overlayMenu.exhaustPanel.current_y + (100F * Settings.scale), !selfStasis);
                    break;
                default:
                    stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, !selfStasis);
            }
        } else {
            stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, !selfStasis);
        }

        AbstractDungeon.effectsQueue.add(stasisStartEffect);
        stasisCard.retain = false;
    }

    @Override
    public void update() {
        super.update();
        if (stasisStartEffect == null || stasisStartEffect.isDone) {
            this.stasisCard.target_x = this.tX;
            this.stasisCard.target_y = this.tY;
            this.stasisCard.applyPowers();

            if (this.hb.hovered) {
                this.stasisCard.targetDrawScale = 1F;

            } else {
                this.stasisCard.targetDrawScale = GuardianMod.stasisCardRenderScale;
            }
        }

        this.stasisCard.update();
    }

    public void render(SpriteBatch sb) {
        if (!this.hb.hovered && (this.stasisStartEffect == null || this.stasisStartEffect.isDone)) {
            renderActual(sb);
        }
    }

    public void renderActual(SpriteBatch sb) {
        this.stasisCard.render(sb);
        if (!this.hb.hovered) this.renderText(sb);
        this.hb.render(sb);
    }

    public void renderPreview(SpriteBatch sb) {
        if (this.hb.hovered && (this.stasisStartEffect == null || this.stasisStartEffect.isDone)) {
            renderActual(sb);
        }
    }

    public void playChannelSFX() {
        // CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);

        // GuardianMod.updateStasisCount();
    }

    public AbstractOrb makeCopy() {
        StasisOrb so = new StasisOrb(this.stasisCard);
        so.passiveAmount = so.basePassiveAmount = this.passiveAmount;
        so.evokeAmount = so.baseEvokeAmount = this.evokeAmount;
        return so;
    }
}
