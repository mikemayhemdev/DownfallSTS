package slimebound.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import slimebound.cards.*;
import slimebound.patches.AbstractCardEnum;
import slimebound.patches.SlimeboundEnum;
import slimebound.relics.AbsorbEndCombat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SlimeboundCharacter extends CustomPlayer {
    public static Color cardRenderColor = new Color(0.0F, 0.1F, 0.0F, 1.0F);
    public float renderscale = 1.0F;
    public float hatX;
    public float hatY;
    public boolean moved = false;
    public boolean foughtSlimeBoss;
    public float leftScale = 0.15F;
    public float xStartOffset = (float) Settings.WIDTH * 0.23F;
    private static float xSpaceBetweenSlots = 90 * Settings.scale;
    private static float xSpaceBottomAlternatingOffset = 0 * Settings.scale;
    public boolean puddleForm;

    private static final CharacterStrings charStrings;
    public static final String NAME;
    public static final String DESCRIPTION;


    private static float yStartOffset = AbstractDungeon.floorY + (100 * Settings.scale);

    private static float ySpaceBottomAlternatingOffset = -100 * Settings.scale;
    private static float ySpaceAlternatingOffset = -50 * Settings.scale;

    private String atlasURL = "slimeboundResources/SlimeboundImages/char/skeleton.atlas";
    private String jsonURL = "slimeboundResources/SlimeboundImages/char/skeleton.json";
    private String jsonURLPuddle = "slimeboundResources/SlimeboundImages/char/skeletonPuddle.json";

    private String currentJson = jsonURL;


    public float[] orbPositionsX = {0,0,0,0,0,0,0,0,0,0};

    public float[] orbPositionsY = {0,0,0,0,0,0,0,0,0,0};


    public static final String[] orbTextures = {"slimeboundResources/SlimeboundImages/char/orb/layer1.png", "slimeboundResources/SlimeboundImages/char/orb/layer2.png", "slimeboundResources/SlimeboundImages/char/orb/layer3.png", "slimeboundResources/SlimeboundImages/char/orb/layer4.png", "slimeboundResources/SlimeboundImages/char/orb/layer5.png", "slimeboundResources/SlimeboundImages/char/orb/layer6.png", "slimeboundResources/SlimeboundImages/char/orb/layer1d.png", "slimeboundResources/SlimeboundImages/char/orb/layer2d.png", "slimeboundResources/SlimeboundImages/char/orb/layer3d.png", "slimeboundResources/SlimeboundImages/char/orb/layer4d.png", "slimeboundResources/SlimeboundImages/char/orb/layer5d.png"};

    public void puddleForm(){
        this.currentJson = jsonURLPuddle;
        reloadAnimation();
        this.puddleForm = true;

    }

    public void removePuddleForm(){
        if (this.puddleForm) {
            this.currentJson = jsonURL;
            reloadAnimation();
            this.puddleForm = false;
        }
    }

    public void setRenderscale(float renderscale) {
        this.renderscale = renderscale;
        reloadAnimation();


    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (this.puddleForm){
            removePuddleForm();
        }
    }

    public SlimeboundCharacter(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "slimeboundResources/SlimeboundImages/char/orb/vfx.png", (String) null, (String) null);


        this.initializeClass((String) null, "slimeboundResources/SlimeboundImages/char/shoulder2.png", "slimeboundResources/SlimeboundImages/char/shoulder.png", "slimeboundResources/SlimeboundImages/char/corpse.png", this.getLoadout(), 0.0F, 0.0F, 300.0F, 180.0F, new EnergyManager(3));
        this.reloadAnimation();


       // this.dialogX = -200 * Settings.scale;
        this.dialogY += -100 * Settings.scale;
        initializeSlotPositions();

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
        this.loadAnimation(atlasURL, this.currentJson, renderscale);
        TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());

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
        orbPositionsX[0] = xStartOffset + (xSpaceBetweenSlots * 1);
        orbPositionsX[1] = xStartOffset + (xSpaceBetweenSlots * 1) + xSpaceBottomAlternatingOffset;
        orbPositionsX[2] = xStartOffset + (xSpaceBetweenSlots * 2);
        orbPositionsX[3] = xStartOffset + (xSpaceBetweenSlots * 2) + xSpaceBottomAlternatingOffset;
        orbPositionsX[4] = xStartOffset + (xSpaceBetweenSlots * 3);
        orbPositionsX[5] = xStartOffset + (xSpaceBetweenSlots * 3) + xSpaceBottomAlternatingOffset;
        orbPositionsX[6] = xStartOffset + (xSpaceBetweenSlots * 4);
        orbPositionsX[7] = xStartOffset + (xSpaceBetweenSlots * 4) + xSpaceBottomAlternatingOffset;
        orbPositionsX[8] = xStartOffset + (xSpaceBetweenSlots * 5);
        orbPositionsX[9] = xStartOffset + (xSpaceBetweenSlots * 5) + xSpaceBottomAlternatingOffset;

        orbPositionsY[0] = yStartOffset;
        orbPositionsY[1] = yStartOffset + ySpaceBottomAlternatingOffset;
        orbPositionsY[2] = yStartOffset + ySpaceAlternatingOffset;
        orbPositionsY[3] = yStartOffset + ySpaceBottomAlternatingOffset + ySpaceAlternatingOffset;
        orbPositionsY[4] = yStartOffset;
        orbPositionsY[5] = yStartOffset + ySpaceBottomAlternatingOffset;
        orbPositionsY[6] = yStartOffset + ySpaceAlternatingOffset;
        orbPositionsY[7] = yStartOffset + ySpaceBottomAlternatingOffset + ySpaceAlternatingOffset;
        orbPositionsY[8] = yStartOffset;
        orbPositionsY[9] = yStartOffset + ySpaceBottomAlternatingOffset;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAME, DESCRIPTION, 70, 70, 4, 99, 5, this,

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
        return new Strike_Slimebound();
    }

    public Color getCardTrailColor() {
        return cardRenderColor.cpy();
    }

    public int getAscensionMaxHPLoss() {
        return 10;
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
        return charStrings.TEXT[1] ;
    }

    public Color getSlashAttackColor() {
        return Color.GREEN;
    }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }


    public String getVampireText() {
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[5];
    }

    @Override
    public void applyStartOfTurnCards() {
        //Failsafe, should be removed through victory or intangible being removed, but just in case of weird buff nullify effects I don't know about...
        super.applyStartOfTurnCards();
        if (this.puddleForm && !this.hasPower(IntangiblePlayerPower.POWER_ID)){

                removePuddleForm();

        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (!this.moved) this.movePosition((float)Settings.WIDTH * this.leftScale, AbstractDungeon.floorY); this.moved = true;


        this.hatX = this.skeleton.findBone("eyeback1").getX();
        this.hatY = this.skeleton.findBone("eyeback1").getY();

    }

    @SpireOverride
    public void renderPowerIcons(SpriteBatch sb, float x, float y) {
        float offset = 10.0F ;
        int powersIterated = 0;
        float YOffset = 0;
        Iterator var5;
        AbstractPower p;
        for (var5 = this.powers.iterator(); var5.hasNext(); offset += 48.0F) {
            p = (AbstractPower) var5.next();
            p.renderIcons(sb, x + (offset* Settings.scale), y + ((-48.0F + YOffset) * Settings.scale), Color.WHITE);
            powersIterated++;
            if (powersIterated == 9 || powersIterated == 18) {
                YOffset += -42F;
                offset = -38.0F;
            }
        }

        offset = 0.0F;
        powersIterated = 0;
        YOffset = 0.0F;

        for (var5 = this.powers.iterator(); var5.hasNext(); offset += 48.0F) {
            p = (AbstractPower) var5.next();
            p.renderAmount(sb, x + ((offset + 32.0F) * Settings.scale), y + ((-66.0F + YOffset) * Settings.scale), Color.WHITE);
            powersIterated++;
            if (powersIterated == 9 || powersIterated == 18) {
                YOffset += -42F;
                offset = -48.0F;
            }
        }
    }

    static {
        charStrings = CardCrawlGame.languagePack.getCharacterString("Slimebound");
        NAME = charStrings.NAMES[0];
        DESCRIPTION = charStrings.TEXT[0];

    }

}


