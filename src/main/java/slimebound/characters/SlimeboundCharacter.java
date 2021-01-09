package slimebound.characters;

import com.megacrit.cardcrawl.helpers.*;
import reskinContent.reskinContent;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import guardian.GuardianMod;
import slimebound.SlimeboundMod;
import slimebound.cards.*;
import slimebound.patches.AbstractCardEnum;
import slimebound.patches.SlimeboundEnum;
import slimebound.relics.AbsorbEndCombat;

import java.util.ArrayList;
import java.util.List;


public class SlimeboundCharacter extends CustomPlayer {
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] orbTextures = {"slimeboundResources/SlimeboundImages/char/orb/layer1.png", "slimeboundResources/SlimeboundImages/char/orb/layer2.png", "slimeboundResources/SlimeboundImages/char/orb/layer3.png", "slimeboundResources/SlimeboundImages/char/orb/layer4.png", "slimeboundResources/SlimeboundImages/char/orb/layer5.png", "slimeboundResources/SlimeboundImages/char/orb/layer6.png", "slimeboundResources/SlimeboundImages/char/orb/layer1d.png", "slimeboundResources/SlimeboundImages/char/orb/layer2d.png", "slimeboundResources/SlimeboundImages/char/orb/layer3d.png", "slimeboundResources/SlimeboundImages/char/orb/layer4d.png", "slimeboundResources/SlimeboundImages/char/orb/layer5d.png"};
    private static final CharacterStrings charStrings;
    public static Color cardRenderColor = new Color(0.0F, 0.1F, 0.0F, 1.0F);

    private static float mainRenderYOffset = 75 * Settings.scale;


    static {
        charStrings = CardCrawlGame.languagePack.getCharacterString("Slimebound");
        NAME = charStrings.NAMES[0];
        DESCRIPTION = charStrings.TEXT[0];

    }

    public float renderscale = 1.2F;
    public float hatX;
    public float hatY;
    public boolean moved = false;
    public boolean foughtSlimeBoss;
    public float leftScale = 0.2F;
    public float xJumpCharOffset = (float) Settings.WIDTH * 0.21F;
    public float x = (float) Settings.WIDTH * 0.1F;
    public boolean puddleForm;
    public float[] orbPositionsX = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public float[] orbPositionsY = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	
    private String atlasURL = "slimeboundResources/SlimeboundImages/char/skeleton.atlas";
    private String jsonURL = "slimeboundResources/SlimeboundImages/char/skeleton.json";
    private String jsonURLPuddle = "slimeboundResources/SlimeboundImages/char/skeletonPuddle.json";
	
	private String atlasURL2 = "reskinContent/img/Slimebound/animation/TheSlimeBossWaifuDownFall.atlas";
    private String atlasURLPuddle2 = "reskinContent/img/Slimebound/animation/Slime_acid_char_puddle.atlas";

    private String jsonURL2 = "reskinContent/img/Slimebound/animation/TheSlimeBossWaifuDownFall.json";
    private String jsonURLPuddle2 = "reskinContent/img/Slimebound/animation/Slime_acid_char_puddle.json";

	
	
    private String currentJson = jsonURL;
	
    private String currentAtlas2 = atlasURL2;
    private String currentJson2 = jsonURL2;

    public SlimeboundCharacter(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "slimeboundResources/SlimeboundImages/char/orb/vfx.png", (String)null, (String)null);
        if(!reskinContent.slimeReskinAnimation){
        this.initializeClass(null,
                "slimeboundResources/SlimeboundImages/char/shoulder.png",
                "slimeboundResources/SlimeboundImages/char/shoulderR.png",
                "slimeboundResources/SlimeboundImages/char/corpse.png",
                this.getLoadout(), 0.0F, 0.0F, 320.0F, 200.0F, new EnergyManager(3));
        }else {
        this.initializeClass((String) null,
               "reskinContent/img/Slimebound/shoulder2.png",
                "reskinContent/img/Slimebound/shoulder.png",
                "reskinContent/img/Slimebound/corpse.png", this.getLoadout(),
                0.0F, 0.0F, 300.0F, 180.0F, new EnergyManager(3));

        }
        this.reloadAnimation();


        this.initializeSlotPositions();
        // this.dialogX = -200 * Settings.scale;
        //this.dialogY += -100 * Settings.scale;
        //initializeSlotPositions();

    }

    public void puddleForm(){
        this.currentJson = jsonURLPuddle;

        this.currentAtlas2 = atlasURLPuddle2;
        this.currentJson2 = jsonURLPuddle2;
        reloadAnimation();
        this.puddleForm = true;
    }

    public void removePuddleForm(){
        if (this.puddleForm) {
            this.currentJson = jsonURL;

            this.currentAtlas2 = atlasURL2;
            this.currentJson2 = jsonURL2;
            reloadAnimation();
            this.puddleForm = false;
        }
    }

    public void setRenderscale(float renderscale) {
        this.renderscale = renderscale;
        reloadAnimation();


    }

    /*
    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(SlimeboundMod.getResourcePath("images/charSelect/leaderboard.png"));
    }
    */

    @Override
    public void onVictory() {
        super.onVictory();
        if (this.puddleForm) {
            removePuddleForm();
        }
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("images/scenes/greenBg.jpg");

    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("slimeboundResources/SlimeboundImages/scenes/slimebound1.png", "VO_SLIMEBOSS_1A"));
        panels.add(new CutscenePanel("slimeboundResources/SlimeboundImages/scenes/slimebound2.png"));
        panels.add(new CutscenePanel("slimeboundResources/SlimeboundImages/scenes/slimebound3.png"));
        return panels;
    }

    public void reloadAnimation() {
        if(reskinContent.slimeReskinAnimation && reskinContent.slimeReskinUnlock){
            this.loadAnimation(currentAtlas2, this.currentJson2, renderscale);
        }else {
            this.loadAnimation(atlasURL, this.currentJson, renderscale);
        }
        TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());
    }


    public void movePosition(float x, float y) {
        super.movePosition(x, y);
        this.drawY = this.drawY + mainRenderYOffset;
        this.dialogY = this.drawY + 30F*Settings.scale;
        this.refreshHitboxLocation();
    }


    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        if (ModHelper.isModEnabled("Red Cards")) {
            CardLibrary.addRedCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Green Cards")) {
            CardLibrary.addGreenCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Blue Cards")) {
            CardLibrary.addBlueCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Purple Cards")) {
            CardLibrary.addPurpleCards(tmpPool);
        }

        return super.getCardPool(tmpPool);
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList();
        retVal.add(Strike_Slimebound.ID);
        retVal.add(Strike_Slimebound.ID);
        retVal.add(Strike_Slimebound.ID);
        retVal.add(Tackle.ID);
        retVal.add(Defend_Slimebound.ID);
        retVal.add(Defend_Slimebound.ID);
        retVal.add(Defend_Slimebound.ID);
        retVal.add(Defend_Slimebound.ID);
        retVal.add(Split.ID);
        retVal.add(CorrosiveSpit.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList();
        retVal.add(AbsorbEndCombat.ID);
        UnlockTracker.markRelicAsSeen(AbsorbEndCombat.ID);
        return retVal;
    }

    public void initializeSlotPositions() {
        float xStartOffset = this.drawX + Settings.scale * -150F;
        float yStartOffset = this.drawY + Settings.scale * -55F;
        float ySpaceBottomAlternatingOffset = -70 * Settings.scale;
        float ySpaceAlternatingOffset = -20 * Settings.scale;
        float xSpaceBetweenSlots = 110 * Settings.scale;
        float xSpaceBottomAlternatingOffset = -0.2F * Settings.scale;


        orbPositionsX[0] = xStartOffset + (xSpaceBetweenSlots * 0);
        orbPositionsX[1] = xStartOffset + (xSpaceBetweenSlots * 1);
        orbPositionsX[2] = xStartOffset + (xSpaceBetweenSlots * 2);
        orbPositionsX[3] = xStartOffset + (xSpaceBetweenSlots * 3);
        orbPositionsX[4] = xStartOffset + (xSpaceBetweenSlots * 4);

        orbPositionsY[0] = yStartOffset;
        orbPositionsY[1] = yStartOffset + ySpaceAlternatingOffset;
        orbPositionsY[2] = yStartOffset;
        orbPositionsY[3] = yStartOffset + ySpaceAlternatingOffset;
        orbPositionsY[4] = yStartOffset;

    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAME, DESCRIPTION, 65, 65, 3, 99, 5, this,

                getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(PlayerClass playerClass) {
        return NAME;
    }

    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.SLIMEBOUND;
    }

    public Color getCardRenderColor() {

        return cardRenderColor;
    }

    public AbstractCard getStartCardForEvent() {
        return new Split();
    }

    public Color getCardTrailColor() {
        return cardRenderColor.cpy();
    }

    public int getAscensionMaxHPLoss() {
        return 6;
    }

    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("SLIME_SPLIT", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    public String getCustomModeCharacterButtonSoundKey() {
        return "SLIME_SPLIT";
    }

    public String getLocalizedCharacterName() {
        return NAME;
    }

    public AbstractPlayer newInstance() {
        return new SlimeboundCharacter(NAME, SlimeboundEnum.SLIMEBOUND);
    }

    public String getSpireHeartText() {
        return charStrings.TEXT[1];
    }

    public Color getSlashAttackColor() {
        return Color.GREEN;
    }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    public String getVampireText() {
        return charStrings.TEXT[2];
    }

    @Override
    public void applyStartOfTurnCards() {
        //Failsafe, should be removed through victory or intangible being removed, but just in case of weird buff nullify effects I don't know about...
        super.applyStartOfTurnCards();
        if (this.puddleForm && !this.hasPower(IntangiblePlayerPower.POWER_ID)) {

            removePuddleForm();

        }
    }


}


