package collector;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import collector.cards.AbstractCollectorCard;
import collector.cards.CollectorCards.Attacks.SoulHarvest;
import collector.cards.CollectorCards.Attacks.SoulStitch;
import collector.cards.CollectorCards.Attacks.Strike;
import collector.cards.CollectorCards.Skills.Contemplate;
import collector.cards.CollectorCards.Skills.Defend;
import collector.relics.EmeraldTorch;
import collector.util.FlashTargetArrowEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.GlowyFireEyesEffect;
import com.megacrit.cardcrawl.vfx.StaffFireEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import downfall.downfallMod;

import java.util.ArrayList;

import static collector.CollectorMod.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;

public class CollectorChar extends CustomPlayer {
    public static final String ID = makeID("theCollector");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] orbTextures = {
            "collectorResources/images/char/mainChar/orb/layer1.png",
            "collectorResources/images/char/mainChar/orb/layer2.png",
            "collectorResources/images/char/mainChar/orb/layer3.png",
            "collectorResources/images/char/mainChar/orb/layer4.png",
            "collectorResources/images/char/mainChar/orb/layer5.png",
            "collectorResources/images/char/mainChar/orb/layer6.png",
            "collectorResources/images/char/mainChar/orb/layer1d.png",
            "collectorResources/images/char/mainChar/orb/layer2d.png",
            "collectorResources/images/char/mainChar/orb/layer3d.png",
            "collectorResources/images/char/mainChar/orb/layer4d.png",
            "collectorResources/images/char/mainChar/orb/layer5d.png",};
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    public float renderscale = 1.2F;
    private final String atlasURL = "collectorResources/images/char/mainChar/skeleton.atlas";
    private final String jsonURL = "collectorResources/images/char/mainChar/skeleton.json";
    public static TorchChar torch;
    private float fireTimer = 0.0F;

    // The aggro of Dragon
    public int aggro;

    public boolean isReticleAttackIcon;
    public Color attackIconColor = CardHelper.getColor(48, 160, 108);
    public static Texture attackerIcon = null;

    public boolean dragonAttackAnimation;
    public boolean attackAnimation;
    private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;

    public CollectorChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "collectorResources/images/char/mainChar/orb/vfx.png", null, (String) null);
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 0.0F, -40.0F, 300.0F, 390.0F, new EnergyManager(3));
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
        torch = new TorchChar(TorchChar.characterStrings.NAMES[0], 0.0f, 0.0f, 220.0f, 290.0f, this);
        torch.initializeClass();
        this.reloadAnimation();

    }

    public void reloadAnimation() {
        this.loadAnimation(atlasURL, jsonURL, 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        flipHorizontal = !flipHorizontal;
    }


    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(downfallMod.collectorModID + "Resources/images/charSelect/leaderboard.png");
    }


    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                65, 65, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(EmeraldTorch.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("MONSTER_AUTOMATON_SUMMON", 0.1F);
        } else {
            CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1F);
        }
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {

        if (MathUtils.randomBoolean()) {
            return "MONSTER_AUTOMATON_SUMMON";
        } else {
            return "AUTOMATON_ORB_SPAWN";
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return downfallMod.Enums.COLLECTOR;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
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
        return null;
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new CollectorChar(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
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

    @Override
    public void movePosition(float x, float y) {
        super.movePosition(x, y);
        torch.move();
    }

    @Override
    public void update() {
        super.update();
        if (getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            CollectorMod.targetMarker.update();
            torch.update();
        }
        if (!this.isDying && getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.07F;
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(this.skeleton.getX() + this.skeleton.findBone("lefteyefireslot").getX(), this.skeleton.getY() + this.skeleton.findBone("lefteyefireslot").getY() + 140.0F * Settings.scale));
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(this.skeleton.getX() + this.skeleton.findBone("righteyefireslot").getX(), this.skeleton.getY() + this.skeleton.findBone("righteyefireslot").getY() + 140.0F * Settings.scale));
                AbstractDungeon.effectList.add(new StaffFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() - 120.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 390.0F * Settings.scale));
            }
        }
    }

    @Override
    public void combatUpdate() {
        torch.combatUpdate();
        super.combatUpdate();
    }

    @Override
    public void showHealthBar() {
        torch.showHealthBar();
        super.showHealthBar();
    }

    @Override
    public void render(SpriteBatch sb) {
        torch.render(sb);
        super.render(sb);
        if (getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            CollectorMod.targetMarker.render(sb);
        }
    }

    @Override
    public void renderPlayerBattleUi(SpriteBatch sb) {
        super.renderPlayerBattleUi(sb);
        torch.renderPlayerBattleUi(sb);
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        torch.preBattlePrep();
    }

    public void updateIntents() {
        if (getCurrRoom().monsters != null) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                m.applyPowers();
            }
        }
    }

    @Override
    public void updateAnimations() {
        super.updateAnimations();
        torch.updateAnimations();
    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        torch.applyStartOfCombatLogic();
    }

    @Override
    public void applyStartOfTurnPowers() {
        super.applyStartOfTurnPowers();
        if (!torch.isDead) {
            AbstractDungeon.player = torch; // To make FlameBarrierPower wear off on Dragon. Are you serious devs?
            torch.applyStartOfTurnPowers();
            AbstractDungeon.player = this;
        }
    }

    @Override
    public void applyStartOfTurnPostDrawPowers() {
        super.applyStartOfTurnPostDrawPowers();
        if (!torch.isDead) {
            torch.applyStartOfTurnPostDrawPowers();
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        if (!torch.isDead) {
            torch.applyEndOfTurnTriggers();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        torch.onVictory();
    }
}
