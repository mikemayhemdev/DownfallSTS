package guardian.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;
import guardian.actions.DestroyOrbSlotForDamageAction;
import guardian.actions.ReturnStasisCardToHandAction;
import guardian.actions.StasisEvokeIfRoomInHandAction;
import guardian.cards.*;
import guardian.powers.BeamBuffPower;
import guardian.relics.StasisUpgradeRelic;
import guardian.vfx.AddCardToStasisEffect;
import guardian.vfx.SmallLaserEffectColored;


public class StasisOrb extends AbstractOrb {
    public static final String[] DESC;
    private static final OrbStrings orbString;
    private static String ID = GuardianMod.makeID("StasisOrb");

    static {
        orbString = CardCrawlGame.languagePack.getOrbString(ID);
        DESC = orbString.DESCRIPTION;
    }

    public AbstractCard stasisCard;
    private float vfxTimer = 1.0F;
    private float vfxIntervalMin = 0.15F;
    private float vfxIntervalMax = 0.8F;
    private AbstractGameEffect stasisStartEffect;
    private boolean initialized;

    public StasisOrb(AbstractCard card, boolean costHack) {
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

        if (card.freeToPlayOnce && !costHack) {
            this.basePassiveAmount = this.passiveAmount = 1;
        }

        if (this.basePassiveAmount < 1) {
            this.basePassiveAmount = this.passiveAmount = 1;
        }

        if(card.cost == -1){
            this.basePassiveAmount = this.passiveAmount = AbstractDungeon.player.energy.energy + 1;
        }

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
        if (this.stasisCard instanceof FierceBash) {
            ((FierceBash) this.stasisCard).stasisBonus();
        }
        if (this.stasisCard instanceof Orbwalk) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));

        }
        if (this.stasisCard instanceof ShieldCharger) {
            stasisCard.applyPowers();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.stasisCard.block));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        if (this.stasisCard instanceof ChargeCore) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
        if (this.stasisCard instanceof GatlingBeam) {


            for (int i = 0; i < ((GatlingBeam) stasisCard).turnsInStasis + 1; i++) {
                AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(m.hb.cX, m.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, Color.BLUE), 0.1F));
                stasisCard.applyPowers();
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(AbstractDungeon.player, stasisCard.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
            }
            ((GatlingBeam) stasisCard).turnsInStasis++;

        }
        if (this.stasisCard instanceof MultiBeam) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BeamBuffPower(AbstractDungeon.player, AbstractDungeon.player, stasisCard.magicNumber), stasisCard.magicNumber));

        }
        if (this.stasisCard instanceof ChargeUp) {
            stasisCard.applyPowers();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.stasisCard.block));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, stasisCard.magicNumber), stasisCard.magicNumber));

        }
        if (this.passiveAmount > 0) {
            this.passiveAmount -= 1;
            this.evokeAmount -= 1;
        }

        if (this.passiveAmount <= 0) {
            AbstractDungeon.actionManager.addToTop(new StasisEvokeIfRoomInHandAction(this));
        }
    }

    public void onEvoke() {
        if (this.stasisCard instanceof TimeBomb) {
            AbstractDungeon.player.exhaustPile.addToTop(this.stasisCard);
            AbstractDungeon.actionManager.addToBottom(new DestroyOrbSlotForDamageAction(this.stasisCard.magicNumber, this));
        } else if (this.stasisCard.hasTag(GuardianMod.VOLATILE)) {
            AbstractDungeon.player.exhaustPile.addToTop(this.stasisCard);

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

    @Override
    public void update() {
        if (!initialized) {
            if (AbstractDungeon.player.drawPile.contains(stasisCard)) {
                AbstractDungeon.player.drawPile.removeCard(stasisCard);
                stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, AbstractDungeon.overlayMenu.combatDeckPanel.current_x + (100F * Settings.scale), AbstractDungeon.overlayMenu.combatDeckPanel.current_y + (100F * Settings.scale), AbstractDungeon.overlayMenu.combatDeckPanel.current_x + (200F * Settings.scale), AbstractDungeon.overlayMenu.combatDeckPanel.current_y + (600F * Settings.scale));
                AbstractDungeon.effectsQueue.add(stasisStartEffect);
            } else if (AbstractDungeon.player.hand.contains(stasisCard)) {
                AbstractDungeon.player.hand.removeCard(stasisCard);
                stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, stasisCard.current_x, stasisCard.current_y, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (200F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (600F * Settings.scale));
                AbstractDungeon.effectsQueue.add(stasisStartEffect);
            } else if (AbstractDungeon.player.discardPile.contains(stasisCard)) {
                AbstractDungeon.player.discardPile.removeCard(stasisCard);
                stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (100F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (100F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_x - (200F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (600F * Settings.scale));
                AbstractDungeon.effectsQueue.add(stasisStartEffect);
            } else if (AbstractDungeon.player.exhaustPile.contains(stasisCard)) {
                AbstractDungeon.player.exhaustPile.removeCard(stasisCard);
                stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (100F * Settings.scale), AbstractDungeon.overlayMenu.exhaustPanel.current_y + (100F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_x - (200F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (600F * Settings.scale));
                AbstractDungeon.effectsQueue.add(stasisStartEffect);
            } else if (AbstractDungeon.player.limbo.contains(stasisCard)) {
                AbstractDungeon.player.limbo.removeCard(stasisCard);
                stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, Settings.WIDTH / 2, Settings.HEIGHT * .25F, Settings.WIDTH / 2, Settings.HEIGHT / 2);
                AbstractDungeon.effectsQueue.add(stasisStartEffect);
            } else {
                stasisStartEffect = new AddCardToStasisEffect(stasisCard, this, Settings.WIDTH / 2, Settings.HEIGHT * .25F, Settings.WIDTH / 2, Settings.HEIGHT / 2);
                AbstractDungeon.effectsQueue.add(stasisStartEffect);
            }
            this.stasisCard.targetDrawScale = GuardianMod.stasisCardRenderScale;
            this.stasisCard.retain = false;
            this.initialized = true;
        }

        super.update();
        this.stasisCard.target_x = this.tX;
        this.stasisCard.target_y = this.tY;
        this.stasisCard.applyPowers();
        this.stasisCard.update();

        if (this.hb.hovered) {
            this.stasisCard.targetDrawScale = 1F;

        } else {
            this.stasisCard.targetDrawScale = GuardianMod.stasisCardRenderScale;
        }
    }

    public void render(SpriteBatch sb) {
        if (this.stasisStartEffect == null && !this.hb.hovered) {
            renderActual(sb);
        } else if (this.stasisStartEffect.isDone == true && !this.hb.hovered) {
            renderActual(sb);
        }
    }

    public void renderActual(SpriteBatch sb) {
        this.stasisCard.render(sb);
        if (!this.hb.hovered) this.renderText(sb);
        this.hb.render(sb);
    }

    public void renderPreview(SpriteBatch sb) {
        if (this.stasisStartEffect == null && this.hb.hovered) {
            renderActual(sb);
        } else if (this.stasisStartEffect.isDone == true && this.hb.hovered) {
            renderActual(sb);
        }
    }

    public void playChannelSFX() {
        // CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);

        // GuardianMod.updateStasisCount();
    }

    public AbstractOrb makeCopy() {
        StasisOrb so = new StasisOrb(this.stasisCard, false);
        so.passiveAmount = so.basePassiveAmount = this.passiveAmount;
        so.evokeAmount = so.baseEvokeAmount = this.evokeAmount;
        return so;
    }
}
