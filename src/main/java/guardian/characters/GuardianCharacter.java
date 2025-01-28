package guardian.characters;

import automaton.vfx.CompileVictoryEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryNumberEffect;
import downfall.util.TextureLoader;
import guardian.modules.EnergyOrbGuardian;
import guardian.powers.ModeShiftPower;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import guardian.GuardianMod;
import guardian.cards.*;
import guardian.patches.AbstractCardEnum;
import guardian.patches.GuardianEnum;
import guardian.relics.ModeShifter;

import java.util.ArrayList;
import java.util.List;


public class GuardianCharacter extends CustomPlayer {
    public static final String ID = "Guardian";
    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String NAME = charStrings.NAMES[0];;
    public static final String DESCRIPTION = charStrings.TEXT[0];

    public static final String[] orbTextures = {
            "guardianResources/GuardianImages/char/orb/1.png",
            "guardianResources/GuardianImages/char/orb/2.png",
            "guardianResources/GuardianImages/char/orb/3.png",
            "guardianResources/GuardianImages/char/orb/4.png",
            "guardianResources/GuardianImages/char/orb/5.png",
            "guardianResources/GuardianImages/char/orb/6.png",
            "guardianResources/GuardianImages/char/orb/7.png",
            "guardianResources/GuardianImages/char/orb/1d.png",
            "guardianResources/GuardianImages/char/orb/2d.png",
            "guardianResources/GuardianImages/char/orb/3d.png",
            "guardianResources/GuardianImages/char/orb/4d.png",
            "guardianResources/GuardianImages/char/orb/5d.png",
            "guardianResources/GuardianImages/char/orb/6d.png",};

    public static float orbScaleFinal = 1.0f;
    public static Color cardRenderColor = GuardianMod.mainGuardianColor;


    public float renderscale = 2.5F;
	public float renderscale2 = 3.0F;

    private String atlasURL = "guardianResources/GuardianImages/char/skeleton.atlas";
    private String jsonURL = "guardianResources/GuardianImages/char/skeleton.json";
    private String jsonURLPuddle = "guardianResources/GuardianImages/char/skeletonPuddle.json";

	private String atlasURL2 = "reskinContent/img/GuardianMod/GuardianChan/animation/Guardian.atlas";
	private String jsonURL2 = "reskinContent/img/GuardianMod/GuardianChan/animation/Guardian.json";

    private String currentJson = jsonURL;
	private String currentJson2 = jsonURL2;

    public boolean inDefensive;
    private boolean inShattered;

    public GuardianCharacter(String name, PlayerClass setClass) {
//        super(name, setClass, orbTextures, "guardianResources/GuardianImages/char/orb/vfx.png", (String)null, (String)null);
        super(name, setClass, new EnergyOrbGuardian(orbTextures,"guardianResources/GuardianImages/char/orb/vfx.png"), (String)null, (String)null);


        if(CharacterSelectScreenPatches.characters[0].reskinCount == 1){
            this.initializeClass(null,
                    CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].SHOULDER1,
                    CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].SHOULDER2,
                    CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].CORPSE,
                    this.getLoadout(), 0.0F, -10F, 400.0F, 350.0F, new EnergyManager(3));
        }else {
            this.initializeClass(null,
                    CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].SHOULDER1,
                    CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].SHOULDER2,
                    CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].CORPSE,
                    this.getLoadout(),0.0F, -10F, 310.0F, 260.0F, new EnergyManager(3));
        }

//        if(CharacterSelectScreenPatches.characters[0].isOriginal()){
//        this.initializeClass(null,
//                "guardianResources/GuardianImages/char/shoulder.png",
//                "guardianResources/GuardianImages/char/shoulderR.png",
//                "guardianResources/GuardianImages/char/corpse.png", this.getLoadout(),
//                0.0F, -10F, 310.0F, 260.0F, new EnergyManager(3));
//        }else
//            {
//                this.initializeClass((String) null,
//                 "reskinContent/img/GuardianMod/GuardianChan/shoulder2.png",
//                 "reskinContent/img/GuardianMod/GuardianChan/shoulder.png",
//                 "reskinContent/img/GuardianMod/GuardianChan/corpse.png", this.getLoadout(),
//                  0.0F, -10F, 400.0F, 350.0F, new EnergyManager(3));
//
//            }

		this.reloadAnimation();


    }

    //TODO - Victory screens

    public CharSelectInfo getInfo() {
        return getLoadout();
    }

    @Override
    public Texture getCutsceneBg() {
        return TextureLoader.getTexture("images/scenes/greenBg.jpg");

    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("guardianResources/GuardianImages/scenes/guardian1.png", "MONSTER_GUARDIAN_DESTROY"));
        panels.add(new CutscenePanel("guardianResources/GuardianImages/scenes/guardian2.png", "GUARDIAN_ROLL_UP"));
        panels.add(new CutscenePanel("guardianResources/GuardianImages/scenes/guardian3.png"));
        return panels;
    }

    public void reloadAnimation() {
        this.loadAnimation(
                CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].atlasURL,
                CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].jsonURL,
                CharacterSelectScreenPatches.characters[0].skins[CharacterSelectScreenPatches.characters[0].reskinCount].renderscale);
        this.state.setAnimation(0, "idle", true);


    }
/*
    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        this.state.setTimeScale(.75F);
        this.state.setAnimation(0, "idle", true);
    }
*/

    public void switchToDefensiveMode(){
        orbScaleFinal = 0.7f;
        if (!inShattered) {
            if (!inDefensive) {
                if(CharacterSelectScreenPatches.characters[0].isOriginal()){

                    this.stateData.setMix("idle", "defensive", 0.2F);
                    this.state.setTimeScale(.75F);
                    this.state.setAnimation(0, "defensive", true);

                    inDefensive = true;
                }else {
                    reloadAnimation();
                    this.state.setTimeScale(2.0F);
                    this.state.setAnimation(0, "transition", false);
                    this.state.addAnimation(0, "defensive", true,0.0f);
//                    this.stateData.setMix("idle", "defensive", 0.2F);
//                    this.state.setTimeScale(2.0F);
//                    this.state.addAnimation(0, "defensive", true, 0.0F);

                    inDefensive = true;}
            }
        }
    }

    /*
    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(GuardianMod.getResourcePath("images/charSelect/leaderboard.png"));
    }
    */

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ModeShiftPower(AbstractDungeon.player, AbstractDungeon.player, 20), 20));

    }

    public void switchToOffensiveMode() {
        orbScaleFinal = 1.0f;
        if (!inShattered) {
            if (inDefensive) {
                if(CharacterSelectScreenPatches.characters[0].isOriginal()){
                    CardCrawlGame.sound.playA("GUARDIAN_ROLL_UP", .25F);
                    this.stateData.setMix("defensive", "idle", 0.2F);
                    this.state.setTimeScale(.75F);
                    this.state.setAnimation(0, "idle", true);

                    inDefensive = false;
                }else{
                    CardCrawlGame.sound.playA("GUARDIAN_ROLL_UP", .25F);
                    this.stateData.setMix("defensive", "offensive", 0.1F);
                    this.state.setTimeScale(.75F);
                    this.state.setAnimation(0, "offensive", false);this.state.addAnimation(0, "idle", true, 0.0F);

                    inDefensive = false;
                }
            }
        } else {
            this.stateData.setMix("transition", "idle", 0.2F);
            this.state.setTimeScale(.75F);
            this.state.setAnimation(0, "idle", true);
            inShattered = false;
        }
    }

    public void switchToShatteredMode(){
        if (!inShattered) {
            if (inDefensive) {
                this.stateData.setMix("defensive", "transition", 0.2F);
                this.state.setTimeScale(.75F);
                this.state.setAnimation(0, "transition", true);
                inShattered = true;
            } else {
                this.stateData.setMix("idle", "transition", 0.2F);
                this.state.setTimeScale(.75F);
                this.state.setAnimation(0, "transition", true);
                inShattered = true;
            }
        }
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Strike_Guardian.ID);
        retVal.add(Strike_Guardian.ID);
        retVal.add(Strike_Guardian.ID);
        retVal.add(Strike_Guardian.ID);
        retVal.add(Defend_Guardian.ID);
        retVal.add(Defend_Guardian.ID);
        retVal.add(Defend_Guardian.ID);
        retVal.add(Defend_Guardian.ID);

        retVal.add(CurlUp.ID);
        retVal.add(TwinSlam.ID);

        return retVal;
    }

    public ArrayList<String> getStartingRelics() {

        ArrayList<String> retVal = new ArrayList();
        retVal.add(ModeShifter.ID);
        UnlockTracker.markRelicAsSeen(ModeShifter.ID);
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAME,DESCRIPTION,
                80, 80, 3, 99, 5, this,

                getStartingRelics(), getStartingDeck(), false);

    }

    public String getTitle(PlayerClass playerClass) {
        return NAME;
    }

    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.GUARDIAN;
    }

    public Color getCardRenderColor() {

        return cardRenderColor;
    }

    public AbstractCard getStartCardForEvent() {
        return new TwinSlam();
    }

    public Color getCardTrailColor() {
        return cardRenderColor.cpy();
    }

    public int getAscensionMaxHPLoss() {
        return 5;
    }

    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("MONSTER_GUARDIAN_DESTROY", MathUtils.random(-0.1F, 0.1F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    public String getCustomModeCharacterButtonSoundKey() {
        return "MONSTER_GUARDIAN_DESTROY";
    }

    public String getLocalizedCharacterName() {
        return NAME;
    }

    public AbstractPlayer newInstance() {
        return new GuardianCharacter(NAME, GuardianEnum.GUARDIAN);
    }

    public String getSpireHeartText() {
        return charStrings.TEXT[1];
    }

    public Color getSlashAttackColor() {
        return Color.FIREBRICK;
    }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL, AbstractGameAction.AttackEffect.SLASH_HEAVY};
    }

    public String getVampireText() {
        return charStrings.TEXT[3];
    }


    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
            if (effects.stream().filter(e -> e instanceof DefectVictoryNumberEffect).count() < 8)
                effects.add(new DefectVictoryNumberEffect());
    }


    @Override
    public void onVictory() {
        super.onVictory();
        switchToOffensiveMode();
    }
}


