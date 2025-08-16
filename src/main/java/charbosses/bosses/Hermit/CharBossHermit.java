package charbosses.bosses.Hermit;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct1SharpshooterNewAge;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct3DoomsdayNewAge;
import charbosses.bosses.Hermit.Simpler.ArchetypeAct1SharpshooterSimple;
import charbosses.bosses.Hermit.Simpler.ArchetypeAct2WheelOfFateSimple;
import charbosses.bosses.Hermit.Simpler.ArchetypeAct3BasicsSimple;
import charbosses.core.EnemyEnergyManager;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import charbosses.powers.bossmechanicpowers.HermitWheelOfFortune;
import charbosses.ui.EnergyOrbHermit;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Slot;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import downfall.downfallMod;
import downfall.util.LocalizeHelper;
import expansioncontent.expansionContentMod;
import hermit.characters.hermit;
import hermit.effects.HermitEyeParticle;

import static charbosses.cards.AbstractBossCard.HAND_SCALE;
import static charbosses.cards.AbstractBossCard.HOVER_SCALE;
import static charbosses.cards.EnemyCardGroup.HAND_HEIGHT_OFFSET;
import static charbosses.cards.EnemyCardGroup.HAND_ROW_LENGTH;
import static hermit.HermitMod.THE_DEFAULT_SKELETON_ATLAS;
import static hermit.HermitMod.THE_DEFAULT_SKELETON_JSON;

public class CharBossHermit extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Hermit");
    public static final String NAME = LocalizeHelper.DonwfallRunHistoryMonsterNames.TEXT[6];

    public Slot eye;
    private float fireTimer = 0.0F;

    private boolean noRender = false;
    public CharBossHermit() {
        super(NAME, ID, 72, 0.0F, -5.0F, 240.0F, 270.0F, null, 0.0f, 20.0f, hermit.Enums.HERMIT);
        this.energyOrb = new EnergyOrbHermit();
        this.energy = new EnemyEnergyManager(3);
        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.flipHorizontal = true;
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        this.energyString = "[E]";
        type = EnemyType.BOSS;


        this.eye = this.skeleton.findSlot("eye");
    }


    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;
        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1SharpshooterNewAge();
            downfallMod.overrideBossDifficulty = false;
            this.currentHealth -= 100;
        } else
            if (!downfallMod.useLegacyBosses){
                switch (AbstractDungeon.actNum) {
                    case 1:
                        archetype = new ArchetypeAct1SharpshooterSimple();
                        break;
                    case 2:
                        archetype = new ArchetypeAct2WheelOfFateSimple();
                        break;
                    case 3:
                        archetype = new ArchetypeAct3BasicsSimple();
                        break;
                    default:
                        archetype = new ArchetypeAct1SharpshooterSimple();
                        break;
                }
            } else {
                switch (AbstractDungeon.actNum) {
                    case 1:
                        archetype = new ArchetypeAct1SharpshooterNewAge();
                        break;
                    case 2:
                        archetype = new ArchetypeAct2WheelOfFateNewAge();
                        break;
                    case 3:
                        archetype = new ArchetypeAct3DoomsdayNewAge();
                        break;
                    default:
                        archetype = new ArchetypeAct1SharpshooterNewAge();
                        break;
                }
            }

        archetype.initialize();
        chosenArchetype = archetype;
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }

    }


    @Override
    public void onPlayAttackCardSound() {
        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_GREMLINCALM_1A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_GREMLINCALM_1B");
                break;
            default:
                break;
        }
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.6F);
        }

        super.damage(info);
    }

/*
    @Override
    public void die() {

        super.die();

        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_GREMLINCALM_2A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_GREMLINCALM_2B");
                break;
            default:
                break;
        }

    }
    */

    public static AbstractCard previewCard;
    public static final float PREVIEW_CARD_SIZE = 0.5F;


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (chosenArchetype.turn < 1 && chosenArchetype instanceof ArchetypeAct2WheelOfFateSimple) {
            noRender = true;
        } else {
            noRender = false;
        }
        if (hasPower(HermitWheelOfFortune.POWER_ID)) {
            if (chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
                if (previewCard == null && !((ArchetypeAct2WheelOfFateNewAge) chosenArchetype).mockDeck.isEmpty()) {
                    AbstractCard q = ((ArchetypeAct2WheelOfFateNewAge) chosenArchetype).mockDeck.get(0);
                    previewCard = q.makeStatEquivalentCopy();
                }
            }

            if (chosenArchetype instanceof ArchetypeAct2WheelOfFateSimple) {
                if (previewCard == null && !((ArchetypeAct2WheelOfFateSimple) chosenArchetype).mockDeck.isEmpty()) {
                    AbstractCard q = ((ArchetypeAct2WheelOfFateSimple) chosenArchetype).mockDeck.get(0);
                    previewCard = q.makeStatEquivalentCopy();
                }
            }

            if (previewCard != null && !isDead && !isDying && !noRender) {
                int cardsinrow = Math.min(3 - HAND_ROW_LENGTH * (int) Math.floor((float) 3 / (float) HAND_ROW_LENGTH), HAND_ROW_LENGTH);
                float widthspacing = AbstractCard.IMG_WIDTH_S + 100.0f * Settings.scale;
                int tar = 4;
                previewCard.target_x = previewCard.current_x = Settings.WIDTH * .9F - ((cardsinrow + 0.5f) * (widthspacing * HAND_SCALE)) + (widthspacing * HAND_SCALE) * (tar % HAND_ROW_LENGTH);
                previewCard.target_y = previewCard.current_y = Settings.HEIGHT * HAND_HEIGHT_OFFSET + (AbstractCard.IMG_HEIGHT_S * HAND_SCALE) * ((float) Math.floor(((float) tar) / (float) HAND_ROW_LENGTH) + (tar > HAND_ROW_LENGTH ? 0.0f : 1.0f));
                previewCard.drawScale = previewCard.targetDrawScale = previewCard.hb.hovered ? HOVER_SCALE : HAND_SCALE;
                previewCard.render(sb);
                FontHelper.cardDescFont_N.getData().setScale(1.0f);
                FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, hermit.characterStrings.TEXT[4], previewCard.current_x, previewCard.current_y - ((previewCard.hb.hovered ? 175 : 100) * Settings.scale));
            }
        }
    }



    @Override
    public void update() {
        super.update();
        if (!this.isDying && this.hasPower(HermitConcentrationPower.POWER_ID)) {
            if (this.getPower(HermitConcentrationPower.POWER_ID).amount > 0) {
                this.fireTimer -= Gdx.graphics.getDeltaTime();
                if (this.fireTimer < 0.0F) {
                    this.fireTimer = 0.1F;
                    HermitEyeParticle shine = new HermitEyeParticle(this.skeleton.getX() + this.eye.getBone().getWorldX(), this.skeleton.getY() + this.eye.getBone().getWorldY(), this, this.skeleton);
                    AbstractDungeon.effectList.add(shine);
                }
            }
        }
        if (previewCard != null && !isDying && !isDead) {
            previewCard.update();
            previewCard.updateHoverLogic();
        }
    }
}
