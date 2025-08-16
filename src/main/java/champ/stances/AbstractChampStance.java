package champ.stances;

import champ.ChampChar;
import champ.ChampMod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.stances.AbstractStance;
import downfall.util.TextureLoader;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;

public abstract class AbstractChampStance extends AbstractStance {

    public int damage = -1;
    public int block = -1;
    public int magic = -1;

    public boolean charged = false;


    public Hitbox hitbox2;

    public float flashTimer = 1.0F;

    public Color textColor = new Color(1F, 1F, 1F, 1F);

    public float[] animAlphaBySlot = new float[3];
    private final boolean[] useBrightTexture = new boolean[3];


    public static final float whiteOverlayTimer = .4F;

    private final Texture whiteOverlay = TextureLoader.getTexture(HexaMod.makeUIPath("whiteOverlay.png"));

    private static final long sfxId = -1L;
    public String STANCE_ID = "guardianmod:AbstractMode";

    public static Texture bruh = TextureLoader.getTexture(ChampMod.makeUIPath("crushing.png"));
    public static Texture bruhB = TextureLoader.getTexture(ChampMod.makeUIPath("crushingBright.png"));

    public AbstractChampStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();// 23


        hitbox2 = new Hitbox(AbstractDungeon.player.drawX - (115F * Settings.scale), AbstractDungeon.player.drawY + (380.0F * Settings.scale), 270 * Settings.scale, 80 * Settings.scale);


    }

    public abstract String getKeywordString();

    @Override
    public void updateAnimation() {

    }



    public int getRemainingChargeCount(){
        int count=0;
        if ((useBrightTexture[2]) && (animAlphaBySlot[2] <= 0F)) count++;
        if ((useBrightTexture[1]) && (animAlphaBySlot[1] <= 0F)) count++;
        if ((useBrightTexture[0]) && (animAlphaBySlot[0] <= 0F)) count++;
        return count;
    }

    public boolean spendTechniqueCharge() {
        if ((useBrightTexture[2]) || (animAlphaBySlot[2] > 0F)) {
            useBrightTexture[2] = false;
            animAlphaBySlot[2] = whiteOverlayTimer;
            return true;
        } else {
            if ((useBrightTexture[1]) || (animAlphaBySlot[1] > 0F)) {
                useBrightTexture[1] = false;
                animAlphaBySlot[1] =  whiteOverlayTimer;
                return true;
            } else {
                if ((useBrightTexture[0]) || (animAlphaBySlot[0] > 0F)) {
                    useBrightTexture[0] = false;
                    animAlphaBySlot[0] =  whiteOverlayTimer;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onEnterStance() {
        useBrightTexture[0] = true;
        useBrightTexture[1] = true;
        useBrightTexture[2] = true;

        // CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");

        //AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new IntenseZoomEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false), 0.05F, true));
        //AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.DARK_GRAY, true));
    }

    @Override
    public void onExitStance() {
        stopIdleSfx();
        /*if (AbstractDungeon.player.stance instanceof NeutralStance) {
            if (AbstractDungeon.player instanceof ChampChar) {
                ((ChampChar) AbstractDungeon.player).switchStanceVisual(NeutralStance.STANCE_ID);
            }
        }
        */
    }

    public void stopIdleSfx() {
        /*
             if (sfxId != -1L) {
                   CardCrawlGame.sound.stop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"), sfxId);
                   sfxId = -1L;
             }
             */
    }

    public abstract void updateDescription();

    public void techique() {
        technique();
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        if (card.type == AbstractCard.CardType.SKILL) {
            if (spendTechniqueCharge()) {
                technique();
            }
        }
    }

    public abstract void technique();

    public void fisher() {
        finisher();
    }

    public abstract void finisher();


    public Texture getHelperTexture() {
        return bruh;

    }

    public Texture getHelperTextureBright() {
        return bruhB;

    }

    public void update() {
        hitbox2.update();
        if (flashTimer > 1.0F)
            flashTimer -= Gdx.graphics.getDeltaTime();
        for (int i = 0; i < 3; i++) {
            if (animAlphaBySlot[i] > 0F) {
                animAlphaBySlot[i] -= Gdx.graphics.getDeltaTime();
                if (animAlphaBySlot[i] < whiteOverlayTimer / 2F) {
                    useBrightTexture[i] = false;
                }
                if (animAlphaBySlot[i] < 0F) {
                    animAlphaBySlot[i] = 0F;
                }
            }
        }
        if (flashTimer < 1.0F)
            flashTimer = 1.0F;
    }

    public abstract String getName(
    );

    public abstract String getDescription();

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        renderTechniqueTriggerUI(sb);
        if (hitbox2.hovered) {
            // showAll = true;
            if ((float) InputHelper.mX < 1400.0F * Settings.scale) {
                TipHelper.renderGenericTip(
                        (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                        getName(),
                        getDescription());
            } else {
                TipHelper.renderGenericTip((float) InputHelper.mX - 350.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                        getName(),
                        getDescription());
            }
        }
    }

    public void renderTechniqueTriggerUI(SpriteBatch sb) {
        int slots = 3;

        float allSlotXOffset = GhostflameHelper.globalX + -20F * Settings.scale;

        float triggerSlot1x = AbstractDungeon.player.drawX - (100.0F * Settings.scale) + allSlotXOffset;
        float triggerSlot1y = AbstractDungeon.player.drawY + (380.0F * Settings.scale);

        float triggerSlot2x = AbstractDungeon.player.drawX + allSlotXOffset;
        float triggerSlot2y = AbstractDungeon.player.drawY + (380.0F * Settings.scale);

        float triggerSlot3x = AbstractDungeon.player.drawX + (100.0F * Settings.scale) + allSlotXOffset;
        float triggerSlot3y = AbstractDungeon.player.drawY + (380.0F * Settings.scale);

        for (int i = 0; i < slots; i++) {
            float x = 0F;
            float y = 0F;

            switch (slots) {
                case 1: {
                    x = triggerSlot2x;
                    y = triggerSlot2y;
                    break;
                }
                case 2: {
                    switch (i) {
                        case 0: {
                            x = triggerSlot2x - ((triggerSlot2x - triggerSlot1x) / 2);
                            y = triggerSlot2y;
                            break;
                        }
                        case 1: {
                            x = triggerSlot2x + ((triggerSlot2x - triggerSlot1x) / 2);
                            y = triggerSlot2y;
                            break;
                        }
                    }
                    break;
                }
                case 3: {
                    switch (i) {
                        case 0: {
                            x = triggerSlot1x;
                            y = triggerSlot1y;
                            break;
                        }
                        case 1: {
                            x = triggerSlot2x;
                            y = triggerSlot2y;
                            break;
                        }
                        case 2: {
                            x = triggerSlot3x;
                            y = triggerSlot3y;
                            break;
                        }
                    }
                    break;
                }
            }

            Texture b;
            if (useBrightTexture[i]) {
                b = getHelperTextureBright();
            } else {
                b = getHelperTexture();
            }
            sb.setColor(1F, 1F, 1F, .9F);
            sb.draw(b, x, y, 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);

            if (animAlphaBySlot[i] > 0F) {
                ////SlimeboundMod.logger.info("Anim alpha slot " + i + " animating: " + animAlphaBySlot[i]);
                if (animAlphaBySlot[i] < whiteOverlayTimer / 2F) {
                    float alpha = Interpolation.linear.apply(0F, .95F, animAlphaBySlot[i] / (whiteOverlayTimer / 2F));
                    sb.setColor(1F, 1F, 1F, alpha);
                } else {
                    float alpha = Interpolation.linear.apply(0F, .95F, (animAlphaBySlot[i] - (whiteOverlayTimer / 2F)) / (whiteOverlayTimer / 2F));
                    sb.setColor(1F, 1F, 1F, alpha);
                }

                sb.draw(whiteOverlay, x, y, 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);

            }


            sb.setColor(1F, 1F, 1F, 1F);

        }


    }

}
