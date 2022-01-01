package collector;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import collector.Relics.EmeraldTorch;
import collector.actions.ApplyAggroAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.cards.CollectorCards.Attacks.SoulHarvest;
import collector.cards.CollectorCards.Attacks.SoulStitch;
import collector.cards.CollectorCards.Attacks.Strike;
import collector.cards.CollectorCards.Skills.Contemplate;
import collector.cards.CollectorCards.Skills.Defend;
import collector.util.FlashTargetArrowEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.GlowyFireEyesEffect;
import com.megacrit.cardcrawl.vfx.StaffFireEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;

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
    private String atlasURL = "collectorResources/images/char/mainChar/skeleton.atlas";
    private String jsonURL = "collectorResources/images/char/mainChar/skeleton.json";
    public static TorchChar torch;
    public AbstractCreature front = this;
    private float fireTimer = 0.0F;
    public static boolean frontChangedThisTurn = false;

    // The aggro of Dragon
    public int aggro;

    public boolean isReticleAttackIcon;
    public Color attackIconColor = CardHelper.getColor(48, 160, 108);
    public static Texture attackerIcon = null;

    public boolean dragonAttackAnimation;
    public boolean attackAnimation;
    private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;
    public CollectorChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "collectorResources/images/char/mainChar/orb/vfx.png", (String) null, (String) null);
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
        return ImageMaster.loadImage(CollectorMod.getModID() + "Resources/images/charSelect/leaderboard.png");
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
        for (int i = 0; i < 3; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(Contemplate.ID);
        retVal.add(SoulStitch.ID);
        retVal.add(SoulHarvest.ID);
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
        return Enums.COLLECTOR;
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

    public static class Enums {
        @SpireEnum
        public static PlayerClass THE_COLLECTOR;
        @SpireEnum(name = "THE_COLLECTOR")
        public static AbstractCard.CardColor COLLECTOR;
        @SpireEnum(name = "THE_COLLECTOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
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

    public void renderOnlyDT(SpriteBatch sb) {
        renderHealth(sb);

        sb.setColor(Color.WHITE);
        sb.draw(img, drawX - img.getWidth() * Settings.scale / 2.0F + animX, drawY, img.getWidth() * Settings.scale, img.getHeight() * Settings.scale, 0, 0, img.getWidth(), img.getHeight(), flipHorizontal, flipVertical);
        hb.render(sb);
        healthHb.render(sb);
    }

    @Override
    public void renderReticle(SpriteBatch sb) {
        if (isReticleAttackIcon) {
            this.reticleRendered = true;
            attackIconColor.a = this.reticleAlpha;
            sb.setColor(attackIconColor);
            sb.draw(getAttackIcon(), this.hb.cX - 64, this.hb.cY - 64, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        }
    }

    @Override
    public void renderHand(SpriteBatch sb) {
        super.renderHand(sb);
        if (this.hoveredCard != null && (this.isDraggingCard || this.inSingleTargetMode) && this.isHoveringDropZone) {
            if (hoveredCard instanceof AbstractCollectorCard) {
                isReticleAttackIcon = hoveredCard.type == AbstractCard.CardType.ATTACK;
            } else {
                isReticleAttackIcon = false;
            }
        } else {
            isReticleAttackIcon = false;
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

        aggro = 0;
        front = this;
        addAggro(0);
        if (AbstractDungeon.player.hasRelic(EmeraldTorch.ID)) {
            addAggro(1);
        }
        frontChangedThisTurn = false;
    }

    public void setFront(AbstractCreature newTarget) {
        if (front != newTarget) {
            front = newTarget;
            PowerBuffEffect effect = new PowerBuffEffect(front.hb.cX - front.animX, front.hb.cY + front.hb.height / 2.0F + 60.0f * Settings.scale,
                    ApplyAggroAction.TEXT[front == this ? 2 : 3]);
            ReflectionHacks.setPrivate(effect, PowerBuffEffect.class, "targetColor", new Color(0.7f, 0.75f, 0.7f, 1.0f));
            AbstractDungeon.effectsQueue.add(effect);
            MonsterGroup group = AbstractDungeon.getMonsters();
            if (group != null) {
                for (AbstractMonster m : group.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        AbstractDungeon.effectsQueue.add(new FlashTargetArrowEffect(m, getCurrentTarget(m)));
                    }
                }
            }

            updateIntents();
            CollectorMod.targetMarker.move(newTarget);
            CollectorMod.targetMarker.flash();

            frontChangedThisTurn = true;
            AbstractDungeon.player.hand.applyPowers();
            AbstractDungeon.player.hand.glowCheck();
        }
    }


    public void setAggro(int aggro) {
        this.aggro = aggro;
        if (aggro > 0) {
            setFront(torch);
        } else {
            setFront(this);
        }
        if (AbstractDungeon.player != null) {
            AbstractDungeon.player.hand.applyPowers();
        }
    }

    public void addAggro(int delta) {
        setAggro(this.aggro + delta);
    }

    public void updateIntents() {
        if (getCurrRoom().monsters != null) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                m.applyPowers();
            }
        }
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        attackAnimation = true;
        dragonAttackAnimation = false;
        super.useCard(c, monster, energyOnUse);
        attackAnimation = true;
        dragonAttackAnimation = false;
    }

    @Override
    public void useFastAttackAnimation() {
        if (dragonAttackAnimation) {
            torch.useFastAttackAnimation();
        }
        if (attackAnimation) {
            super.useFastAttackAnimation();
        }
        attackAnimation = true;
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

    public Texture getAttackIcon() {
        if (attackerIcon == null) {
            attackerIcon = new Texture(CollectorMod.makePath("ui/Attacker.png"));
        }
        return attackerIcon;
    }

    public static TorchChar getTorchHead() {
        if (AbstractDungeon.player instanceof CollectorChar) {
            return torch;
        }
        return null;
    }

    public static TorchChar getLivingTorchHead() {
        if (AbstractDungeon.player instanceof CollectorChar) {
            TorchChar dragon = torch;
            if (dragon.isDead) return null;
            return dragon;
        }
        return null;
    }

    public static boolean isFrontTorchHead() {
        if (AbstractDungeon.player instanceof CollectorChar) {
            TorchChar torch = CollectorChar.torch;
            if (torch.isDead) return false;
            return ((CollectorChar) AbstractDungeon.player).front == torch;
        }
        return false;
    }

    public static boolean isRearYou() {
        if (AbstractDungeon.player instanceof CollectorChar) {
            TorchChar dragon = torch;
            if (dragon.isDead) return true;
            return ((CollectorChar) AbstractDungeon.player).front != AbstractDungeon.player;
        }
        return true;
    }

    public static AbstractCreature getCurrentTarget(AbstractCreature monster) {

        if (AbstractDungeon.player instanceof CollectorChar && !torch.isDead) {
            return ((CollectorChar) AbstractDungeon.player).front;
        } else {
            return AbstractDungeon.player;
        }
    }

    public static boolean isSolo() {
        if (AbstractDungeon.player instanceof CollectorChar) {
            return torch.isDead;
        } else {
            return true;
        }
    }

    public static int getAggro() {
        if (AbstractDungeon.player instanceof CollectorChar) {
            return ((CollectorChar) AbstractDungeon.player).aggro;
        } else {
            return 0;
        }
    }
}
