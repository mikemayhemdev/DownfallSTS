package gremlin.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gremlin.GremlinMod;
import gremlin.actions.DisableEndTurnButtonAction;
import gremlin.actions.ThinkAction;
import gremlin.cards.GremlinDance;
import gremlin.cards.pseudocards.LeaderChoice;
import gremlin.cards.pseudocards.NobChoice;
import gremlin.orbs.*;
import gremlin.patches.AbstractCardEnum;
import gremlin.patches.GremlinEnum;
import gremlin.patches.GremlinMobState;
import gremlin.powers.GremlinPower;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.lastCombatMetricKey;

public class GremlinCharacter extends CustomPlayer {
    public static CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Gremlin");

    private static final int ENERGY_PER_TURN = 3;

    private static final String SHOULDER_2 = "char/shoulder2.png"; // campfire pose
    private static final String SHOULDER_1 = "char/shoulder.png"; // another campfire pose
    private static final String CORPSE = "char/corpse.png"; // dead corpse
    private static final String CHAR_FOLDER = "char/";
    private static final String SKELETON_ATLAS = "/skeleton.atlas";
    private static final String SKELETON_JSON = "/skeleton.json";
    private static final String[] orbTextures = {
            "gremlinResources/images/char/orb/layer1.png",
            "gremlinResources/images/char/orb/layer2.png",
            "gremlinResources/images/char/orb/layer3.png",
            "gremlinResources/images/char/orb/layer4.png",
            "gremlinResources/images/char/orb/layer5.png",
            "gremlinResources/images/char/orb/layer6.png",
            "gremlinResources/images/char/orb/layer1d.png",
            "gremlinResources/images/char/orb/layer2d.png",
            "gremlinResources/images/char/orb/layer3d.png",
            "gremlinResources/images/char/orb/layer4d.png",
            "gremlinResources/images/char/orb/layer5d.png"
    };

    public float xStartOffset = (float) Settings.WIDTH * 0.25F;
    private static float xSpaceBetweenSlots = 90 * Settings.scale;
    private static float xSpaceBetweenSlotsCramp = 85 * Settings.scale;
    private static float yStartOffset = AbstractDungeon.floorY + (30 * Settings.scale);
    public float[] orbPositionsX = {0,0,0,0,0,0,0,0,0,0};
    public float[] orbPositionsXCramp = {0,0,0,0,0,0,0,0,0,0};
    public float[] orbPositionsY = {0,0,0,0,0,0,0,0,0,0};

    public String currentGremlin;
    public String currentAnimation;

    public GremlinMobState mobState;

    public boolean nob = false;

    private int trueGameHandSize;
    private boolean cowering = false;
    private int enemyGremTalk = 0;

    private static String[] NOBUITEXT = CardCrawlGame.languagePack.getUIString("Gremlin:NobFight").TEXT;
    private static String[] GREMUITEXT = CardCrawlGame.languagePack.getUIString("Gremlin:GremFight").TEXT;

    public GremlinCharacter(String name) {
        super(name, GremlinEnum.GREMLIN, orbTextures,
                "gremlinResources/images/char/orb/vfx.png", (String)null, null);

        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 170.0F * Settings.scale);

        initializeClass(null, GremlinMod.getResourcePath(SHOULDER_2),
                GremlinMod.getResourcePath(SHOULDER_1),
                GremlinMod.getResourcePath(CORPSE),
                getLoadout(), 20.0F, -10.0F, 180.0F, 110.0F, new EnergyManager(ENERGY_PER_TURN));

        initializeSlotPositions();

        mobState = new GremlinMobState();
        int load_hp = 20;
        if(AbstractDungeon.ascensionLevel >= 14){
            load_hp -= getAscensionMaxHPLoss();
        }
        if(AbstractDungeon.ascensionLevel >= 6){
            load_hp = MathUtils.round((float)load_hp * 0.9f);
        }
        mobState.initialRandom(load_hp);

        swapBody(mobState.getFrontGremlin(), mobState.getFrontAnimation());
    }

    public String swapBody(String assetFolder, String animationName){
        String s_atlas = CHAR_FOLDER + assetFolder + SKELETON_ATLAS;
        String s_json = CHAR_FOLDER + assetFolder + SKELETON_JSON;
        this.loadAnimation(GremlinMod.getResourcePath(s_atlas), GremlinMod.getResourcePath(s_json), 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, animationName, true);
        e.setTime(e.getEndTime() * MathUtils.random());
        String oldGremlin = currentGremlin;
        currentGremlin = assetFolder;
        currentAnimation = animationName;
        return oldGremlin;
    }

    public void becomeNob(){
        nob = true;
        dialogX += 50;
        dialogY += 60;
        String s_atlas = CHAR_FOLDER + "nob" + SKELETON_ATLAS;
        String s_json = CHAR_FOLDER + "nob" + SKELETON_JSON;
        this.loadAnimation(GremlinMod.getResourcePath(s_atlas), GremlinMod.getResourcePath(s_json), 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void revertNob(){
        nob = false;
        dialogX -= 50;
        dialogY -= 60;
        swapBody(currentGremlin, currentAnimation);
        GremlinStandby gremlin = GremlinMod.getGremlinOrb(currentGremlin);
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, gremlin.getPower(), 1));
    }

    @Override
    public String getPortraitImageName() {
        return "charSelect/portrait.png";
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Gremlin:Strike");
        retVal.add("Gremlin:Strike");
        retVal.add("Gremlin:Strike");
        retVal.add("Gremlin:Strike");
        retVal.add("Gremlin:Defend");
        retVal.add("Gremlin:Defend");
        retVal.add("Gremlin:Defend");
        retVal.add("Gremlin:Defend");
        retVal.add("Gremlin:TagTeam");
        retVal.add("Gremlin:GremlinDance");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Gremlin:GremlinKnob");
        UnlockTracker.markRelicAsSeen("Gremlin:GremlinKnob");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0],
                characterStrings.TEXT[0],
                20, 20, 4, 99,
                5, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.GREMLIN;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.RED;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new GremlinDance();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.RED.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 1;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("VO_GREMLINANGRY_1A", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "VO_GREMLINANGRY_1A";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new GremlinCharacter(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.RED;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[5];
    }

    public void initializeSlotPositions() {
        orbPositionsX[0] = xStartOffset + (xSpaceBetweenSlots * -1) - this.drawX;
        orbPositionsX[1] = xStartOffset + (xSpaceBetweenSlots * -2) - this.drawX;
        orbPositionsX[2] = xStartOffset + (xSpaceBetweenSlots * -3) - this.drawX;
        orbPositionsX[3] = xStartOffset + (xSpaceBetweenSlots * -4) - this.drawX;
        orbPositionsX[4] = xStartOffset + (xSpaceBetweenSlots * -5) - this.drawX;
        orbPositionsX[5] = xStartOffset + (xSpaceBetweenSlots * -6) - this.drawX;
        orbPositionsX[6] = xStartOffset + (xSpaceBetweenSlots * -7) - this.drawX;
        orbPositionsX[7] = xStartOffset + (xSpaceBetweenSlots * -8) - this.drawX;
        orbPositionsX[8] = xStartOffset + (xSpaceBetweenSlots * -9) - this.drawX;
        orbPositionsX[9] = xStartOffset + (xSpaceBetweenSlots * -10) - this.drawX;

        orbPositionsXCramp[0] = xStartOffset + (xSpaceBetweenSlotsCramp * -1) - this.drawX;
        orbPositionsXCramp[1] = xStartOffset + (xSpaceBetweenSlotsCramp * -2) - this.drawX;
        orbPositionsXCramp[2] = xStartOffset + (xSpaceBetweenSlotsCramp * -3) - this.drawX;
        orbPositionsXCramp[3] = xStartOffset + (xSpaceBetweenSlotsCramp * -4) - this.drawX;
        orbPositionsXCramp[4] = xStartOffset + (xSpaceBetweenSlotsCramp * -5) - this.drawX;
        orbPositionsXCramp[5] = xStartOffset + (xSpaceBetweenSlotsCramp * -6) - this.drawX;
        orbPositionsXCramp[6] = xStartOffset + (xSpaceBetweenSlotsCramp * -7) - this.drawX;
        orbPositionsXCramp[7] = xStartOffset + (xSpaceBetweenSlotsCramp * -8) - this.drawX;
        orbPositionsXCramp[8] = xStartOffset + (xSpaceBetweenSlotsCramp * -9) - this.drawX;
        orbPositionsXCramp[9] = xStartOffset + (xSpaceBetweenSlotsCramp * -10) - this.drawX;

        orbPositionsY[0] = yStartOffset;
        orbPositionsY[1] = yStartOffset;
        orbPositionsY[2] = yStartOffset;
        orbPositionsY[3] = yStartOffset;
        orbPositionsY[4] = yStartOffset;
        orbPositionsY[5] = yStartOffset;
        orbPositionsY[6] = yStartOffset;
        orbPositionsY[7] = yStartOffset;
        orbPositionsY[8] = yStartOffset;
        orbPositionsY[9] = yStartOffset;
    }

    public void startOfBattle() {
        enemyGremTalk = 0;
        mobState.startOfBattle(this);
        GremlinPower startPower = GremlinMod.getGremlinOrb(mobState.getFrontGremlin()).getPower();
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, startPower, 1));
        if (((AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) && (lastCombatMetricKey.equals(MonsterHelper.GREMLIN_NOB_ENC))){
            beforeNobFight();
        }
        if (((AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) && (lastCombatMetricKey.equals(MonsterHelper.GREMLIN_LEADER_ENC))){
            beforeLeaderFight();
        }
    }

    public void updateMobState() {
        if(nob){
            revertNob();
        }
        if(cowering){
            removeCower(false);
        }
        mobState.updateMobState(this);
    }

    public void enslave(String victim) {
        mobState.enslave(victim);
    }

    public void resurrect() {
        mobState.resurrect(this.maxHealth);
    }

    public boolean canRez() {
        return mobState.canRez();
    }

    // Max HP changes are divided by 5 rounded up/down
    @Override
    public void increaseMaxHp(final int amount, final boolean showEffect) {
        int adjustedAmount = (amount + 4) / 5; // Divide by 5 round up
        super.increaseMaxHp(adjustedAmount, showEffect);
        mobState.campfireHeal(adjustedAmount, this.maxHealth);
    }

    @Override
    public void decreaseMaxHealth(final int amount) {
        int adjustedAmount = (amount + 4) / 5; // Divide by 5 round up
        super.decreaseMaxHealth(adjustedAmount);
        mobState.campfireHeal(0, this.maxHealth);
    }

    @Override
    public void channelOrb(AbstractOrb orbToSet) {
        if(orbToSet instanceof GremlinStandby){
            GremlinStandby grem = (GremlinStandby) orbToSet;
            for(int i=0;i<this.maxOrbs;i++){
                if(this.orbs.get(i) instanceof GremlinStandby){
                    GremlinStandby orb = (GremlinStandby) this.orbs.get(i);
                    if(orb.assetFolder.equals(grem.assetFolder)){
                        return;
                    }
                }
            }
        }
        super.channelOrb(orbToSet);
    }

    public void gremlinDeathSFX() {
        gremlinDeathSFX(currentGremlin);
    }

    public void gremlinDeathSFX(String gremlin) {
        int roll;
        switch (gremlin) {
            case "angry":
                roll = MathUtils.random(1);
                if (roll == 0) {
                    CardCrawlGame.sound.play("VO_GREMLINANGRY_2A");
                } else {
                    CardCrawlGame.sound.play("VO_GREMLINANGRY_2B");
                }
                break;
            case "fat":
                roll = MathUtils.random(2);
                if (roll == 0) {
                    CardCrawlGame.sound.play("VO_GREMLINFAT_2A");
                } else if (roll == 1) {
                    CardCrawlGame.sound.play("VO_GREMLINFAT_2B");
                } else {
                    CardCrawlGame.sound.play("VO_GREMLINFAT_2C");
                }
                break;
            case "shield":
                roll = MathUtils.random(1);
                if (roll == 0) {
                    CardCrawlGame.sound.play("VO_GREMLINCALM_2A");
                } else {
                    CardCrawlGame.sound.play("VO_GREMLINCALM_2B");
                }
                break;
            case "sneak":
                roll = MathUtils.random(2);
                if (roll == 0) {
                    CardCrawlGame.sound.play("VO_GREMLINSPAZZY_2A");
                } else if (roll == 1) {
                    CardCrawlGame.sound.play("VO_GREMLINSPAZZY_2B");
                } else {
                    CardCrawlGame.sound.play("VO_GREMLINSPAZZY_2C");
                }
                break;
            case "wizard":
                roll = MathUtils.random(2);
                if (roll == 0) {
                    CardCrawlGame.sound.play("VO_GREMLINDOPEY_2A");
                } else if (roll == 1) {
                    CardCrawlGame.sound.play("VO_GREMLINDOPEY_2B");
                } else {
                    CardCrawlGame.sound.play("VO_GREMLINDOPEY_2C");
                }
                break;
            default:
                break;
        }
    }

    private void beforeNobFight(){
        AbstractMonster nob = null;
        GremlinMod.logger.debug("HERE!");
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(mo instanceof GremlinNob){
                nob = mo;
                break;
            }
        }
        if(nob != null){
            GremlinMod.logger.debug("THERE!");
            cower();
            AbstractDungeon.actionManager.addToBottom(new TalkAction(nob, NOBUITEXT[0]));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new NobChoice()));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(2.0f));
            AbstractDungeon.actionManager.addToBottom(new ThinkAction(NOBUITEXT[7], 2.0f, 2.0f));
        }
    }

    private void beforeLeaderFight(){
        AbstractMonster leader = null;
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(mo instanceof GremlinLeader){
                leader = mo;
                break;
            }
        }
        if(leader != null){
            int textNum = -1;
            String rearGrem = mobState.getRearLivingGremlin();
            switch (rearGrem){
                case "angry":
                    textNum = 1;
                    break;
                case "fat":
                    textNum = 2;
                    break;
                case "shield":
                    textNum = 3;
                    break;
                case "sneak":
                    textNum = 4;
                    break;
                case "wizard":
                    textNum = 5;
                    break;
            }
            if(textNum > 0) {
                cower();
                AbstractDungeon.actionManager.addToBottom(new TalkAction(leader, NOBUITEXT[textNum]));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new LeaderChoice()));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(2.0f));
                AbstractDungeon.actionManager.addToBottom(new ThinkAction(NOBUITEXT[7], 2.0f, 2.0f));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(leader, NOBUITEXT[6]));
            }
        }
    }

    private void cower() {
        trueGameHandSize = gameHandSize;
        gameHandSize = 0;
        cowering = true;
        AbstractDungeon.actionManager.addToBottom(new DisableEndTurnButtonAction());
    }

    public void removeCower(boolean endTurnButton) {
        if(cowering){
            cowering = false;
            gameHandSize = trueGameHandSize;
            if(endTurnButton) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, AbstractDungeon.player.gameHandSize));
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction());
                AbstractDungeon.overlayMenu.showCombatPanels();
                AbstractDungeon.player.applyStartOfCombatLogic();

                AbstractDungeon.getCurrRoom().skipMonsterTurn = false;
                AbstractDungeon.player.applyStartOfTurnRelics();
                AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
                AbstractDungeon.player.applyStartOfTurnCards();
                AbstractDungeon.player.applyStartOfTurnPowers();
                AbstractDungeon.player.applyStartOfTurnOrbs();
            }
        }
    }

    @Override
    public void applyStartOfCombatLogic() {
        if(!cowering) {
            super.applyStartOfCombatLogic();
        }
    }

    @Override
    public void applyStartOfTurnRelics() {
        if(!cowering) {
            super.applyStartOfTurnRelics();
        }
    }

    @Override
    public void applyStartOfTurnPostDrawRelics() {
        if(!cowering) {
            super.applyStartOfTurnPostDrawRelics();
        }
    }

    @Override
    public void applyStartOfTurnCards() {
        if(!cowering) {
            super.applyStartOfTurnCards();
        }
    }

    @Override
    public void applyStartOfTurnPowers() {
        if(!cowering) {
            super.applyStartOfTurnPowers();
        }
    }

    @Override
    public void applyStartOfTurnOrbs() {
        if(!cowering) {
            super.applyStartOfTurnOrbs();
        }
    }

    public void gremlinTalk(AbstractCreature enemyGremlin){
        if(enemyGremTalk < GREMUITEXT.length && lastCombatMetricKey != null &&
                !lastCombatMetricKey.equals(MonsterHelper.GREMLIN_LEADER_ENC) &&
                !lastCombatMetricKey.equals("Gremlin Mirror") &&
                GameActionManager.turn <= 1) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(enemyGremlin, GREMUITEXT[enemyGremTalk]));
            enemyGremTalk++;
        }
    }

    public void damageGremlins(int dmg){
        mobState.damageAll(dmg);
    }
    public void healGremlins(int hp){
        mobState.campfireHeal(hp, this.maxHealth);
    }
}
