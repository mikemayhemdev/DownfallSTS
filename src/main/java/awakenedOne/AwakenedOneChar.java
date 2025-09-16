package awakenedOne;

import awakenedOne.cards.Defend;
import awakenedOne.cards.Hymn;
import awakenedOne.cards.Strike;
import awakenedOne.cards.TalonRake;
import awakenedOne.effects.IroncladVictoryFlameEffectBlue;
import awakenedOne.effects.ReverseAwakenedWingParticle;
import awakenedOne.relics.RippedDoll;
import awakenedOne.util.Wiz;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.AwakenedEyeParticle;
import com.megacrit.cardcrawl.vfx.AwakenedWingParticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reskinContent.patches.CharacterSelectScreenPatches;

import java.util.ArrayList;
import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.AwakenedOneMod.placeholderColor;
import static hermit.characters.hermit.update_timer;

public class AwakenedOneChar extends CustomPlayer {
    public static final String ID = makeID("awakenedOne");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    private static final String[] orbTextures = {
            "awakenedResources/images/mainChar/orb/layer1.png",
            "awakenedResources/images/mainChar/orb/layer2.png",
            "awakenedResources/images/mainChar/orb/layer3.png",
            "awakenedResources/images/mainChar/orb/layer4.png",
            "awakenedResources/images/mainChar/orb/layer5.png",
            "awakenedResources/images/mainChar/orb/layer6.png",
            "awakenedResources/images/mainChar/orb/layer1d.png",
            "awakenedResources/images/mainChar/orb/layer2d.png",
            "awakenedResources/images/mainChar/orb/layer3d.png",
            "awakenedResources/images/mainChar/orb/layer4d.png",
            "awakenedResources/images/mainChar/orb/layer5d.png",};
    private static final Logger logger = LogManager.getLogger(AwakenedOne.class.getName());
    private final String atlasURL = "awakenedResources/images/mainChar/awakened.atlas";
    private final String jsonURL = "awakenedResources/images/mainChar/awakened.json";
    private final boolean form1 = true;
    private final ArrayList<ReverseAwakenedWingParticle> wParticles = new ArrayList();
    private final ArrayList<AwakenedWingParticle> wParticles2 = new ArrayList();
    public float renderscale = 1.2F;
    public boolean animateParticles = false;
    private float fireTimer = 0.0F;
    private boolean revived = false;
    private Bone eye;
    private Bone back;


    public AwakenedOneChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "awakenedResources/images/mainChar/orb/vfx.png", null, (String) null);
        initializeClass(null,
                CharacterSelectScreenPatches.characters[6].skins[CharacterSelectScreenPatches.characters[6].reskinCount].SHOULDER1,
                CharacterSelectScreenPatches.characters[6].skins[CharacterSelectScreenPatches.characters[6].reskinCount].SHOULDER2,
                CharacterSelectScreenPatches.characters[6].skins[CharacterSelectScreenPatches.characters[6].reskinCount].CORPSE,
                getLoadout(), 0.0F, -30.0F, 270.0F, 310.0F, new EnergyManager(3));

        this.reloadAnimation();

    }

    public void reloadAnimation() {
        this.loadAnimation(atlasURL, jsonURL, 1.2F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle_1", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle_1", 0.3F);
        this.stateData.setMix("Hit", "Idle_2", 0.2F);
        this.stateData.setMix("Attack_1", "Idle_1", 0.2F);
        this.stateData.setMix("Attack_2", "Idle_2", 0.2F);
        this.state.getData().setMix("Idle_1", "Idle_2", 1.0F);
        this.eye = this.skeleton.findBone("Eye");
        Iterator var4 = this.skeleton.getBones().iterator();

        while (var4.hasNext()) {
            Bone b = (Bone) var4.next();
            logger.info(b.getData().getName());
        }

        this.back = this.skeleton.findBone("Hips");

        this.state.setAnimation(0, "Idle_1", true);

    }


    //not used
    public void revival() {
        revived = true;
        animateParticles = true;
    }

    public void update() {

        super.update();

        animateParticles = !this.isDying && Wiz.isAwakened() && Wiz.isInCombat();

        if (this.isDying || !Wiz.isInCombat()) {
            animateParticles = false;
        }

        if (this.animateParticles) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.1F;
                //todo: replace with non-leaky animation
                AbstractDungeon.effectList.add(new AwakenedEyeParticle(this.skeleton.getX() + this.eye.getWorldX(), this.skeleton.getY() + this.eye.getWorldY()));
                if (!this.flipHorizontal) {
                    this.wParticles.add(new ReverseAwakenedWingParticle());
                }
                if (this.flipHorizontal) {
                    this.wParticles2.add(new AwakenedWingParticle());
                }

            }
        }

        Iterator<ReverseAwakenedWingParticle> p = this.wParticles.iterator();

        while (p.hasNext()) {
            ReverseAwakenedWingParticle e = p.next();
            e.update();
            if (e.isDone) {
                p.remove();
            }
        }

        if (this.flipHorizontal) {
            Iterator<AwakenedWingParticle> p2 = this.wParticles2.iterator();

            while (p2.hasNext()) {
                AwakenedWingParticle e2 = p2.next();
                e2.update();
                if (e2.isDone) {
                    p2.remove();
                }
            }
        }

    }


    public void render(SpriteBatch sb) {
        if (!this.isDying) {
            Iterator var2 = this.wParticles.iterator();

            ReverseAwakenedWingParticle p;
            while (var2.hasNext()) {
                p = (ReverseAwakenedWingParticle) var2.next();
                if (p.renderBehind) {
                    p.render(sb, (this.skeleton.getX() - this.back.getWorldX()), this.skeleton.getY() + this.back.getWorldY());
                }
            }

            super.render(sb);
            var2 = this.wParticles.iterator();

            while (var2.hasNext()) {
                p = (ReverseAwakenedWingParticle) var2.next();
                if (!p.renderBehind) {
                    p.render(sb, this.skeleton.getX() - this.back.getWorldX(), this.skeleton.getY() + this.back.getWorldY());
                }
            }

        }
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0 && skeleton.getData().findAnimation("Hit") != null) {
            this.state.setAnimation(0, "Hit", false);
            if (this.form1) {
                this.state.addAnimation(0, "Idle_1", true, 0.0F);
            } else {
                this.state.addAnimation(0, "Idle_2", true, 0.0F);
            }
        }
        super.damage(info);
    }


    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(AwakenedOneMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }


    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                70, 70, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(TalonRake.ID);
        retVal.add(Hymn.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(RippedDoll.ID);
        return retVal;
    }


    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {

        update_timer += Gdx.graphics.getDeltaTime();

        update_timer += Gdx.graphics.getDeltaTime();

        for (float i = 0; i + (1.0 / 120.0) <= update_timer; update_timer -= (1.0 / 120.0)) {
            float spawn = (float) MathUtils.random(0, 20);
            if (spawn == 1) {
                effects.add(new IroncladVictoryFlameEffectBlue());
                AbstractDungeon.effectsQueue.add(new IroncladVictoryFlameEffectBlue());
            }
        }
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("awakened:CHANT", 0.3F);
        } else {
            CardCrawlGame.sound.play("awakened:CHANT", 0.3F);
        }
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        if (MathUtils.randomBoolean()) {
            return "awakened:CHANT";
        } else {
            return "awakened:CHANT";
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.AWAKENED_BLUE;
    }

    @Override
    public Color getCardTrailColor() {
        return placeholderColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new TalonRake();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new AwakenedOneChar(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return placeholderColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return placeholderColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static PlayerClass AWAKENED_ONE;
        @SpireEnum(name = "AWAKENED_BLUE")
        public static AbstractCard.CardColor AWAKENED_BLUE;
        @SpireEnum(name = "AWAKENED_BLUE")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
