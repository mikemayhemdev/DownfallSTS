package champ;

import basemod.abstracts.CustomPlayer;
import champ.cards.*;
import champ.cards.stancecards.Execute;
import champ.relics.ChampionCrown;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import champ.stances.UltimateStance;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.downfallMod;

import java.util.ArrayList;

import static champ.ChampMod.makeID;
import static champ.ChampMod.placeholderColor;

public class ChampChar extends CustomPlayer {
    public static final String ID = makeID("theChamp");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] orbTextures = {
            "champResources/images/char/mainChar/orb/layer1.png",
            "champResources/images/char/mainChar/orb/layer2.png",
            "champResources/images/char/mainChar/orb/layer3.png",
            "champResources/images/char/mainChar/orb/layer4.png",
            "champResources/images/char/mainChar/orb/layer5.png",
            "champResources/images/char/mainChar/orb/layer6.png",
            "champResources/images/char/mainChar/orb/layer1d.png",
            "champResources/images/char/mainChar/orb/layer2d.png",
            "champResources/images/char/mainChar/orb/layer3d.png",
            "champResources/images/char/mainChar/orb/layer4d.png",
            "champResources/images/char/mainChar/orb/layer5d.png",};
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private static String currentIdle = "Idle";

    public float renderscale = 1.2F;
    public float renderscale2 = 2.5F;

    public float stanceSwitchAnimTimer = 0.0F;
    private final ArrayList<String> stanceSwitchQueue = new ArrayList<>();


    private final String atlasURL = "champResources/images/char/mainChar/champ.atlas";
    private final String jsonURL = "champResources/images/char/mainChar/champ.json";

    private final String atlasURL2 = "reskinContent/img/ChampMod/Chan-P/animation/TheChampPlayer.atlas";
    private final String jsonURL2 = "reskinContent/img/ChampMod/Chan-P/animation/TheChampPlayer.json";

    /*
    private String atlasURL2 = "reskinContent/img/HexaghostMod/animation/Hexaghost_self_downfall.atlas";
    private String jsonURL2 = "reskinContent/img/HexaghostMod/animation/Hexaghost_self_downfall.json";
    */

    public ChampChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "champResources/images/char/mainChar/orb/vfx.png", null, (String) null);


        String SHOULDER1 = "champResources/images/char/mainChar/shoulder.png";
        String SHOULDER2 = "champResources/images/char/mainChar/shoulderR.png";
        String CORPSE = "champResources/images/char/mainChar/corpse.png";

        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), -15.0F, -30.0F, 250.0F, 300.0F, new EnergyManager(3));


//        if (!CharacterSelectScreenPatches.characters[4].isOriginal()) {
//            initializeClass(null,
//                    "reskinContent/img/ChampMod/Chan-P/shoulder2.png",
//                    "reskinContent/img/ChampMod/Chan-P/shoulder.png",
//                    "reskinContent/img/ChampMod/Chan-P/corpse.png",
//                    getLoadout(), -15.0F, -30.0F, 250.0F, 300.0F, new EnergyManager(3));
//
//        } else {
//            initializeClass(null,
//                    SHOULDER1,
//                    SHOULDER2,
//                    CORPSE,
//                    getLoadout(), -15.0F, -30.0F, 250.0F, 300.0F, new EnergyManager(3));
//        }


        this.reloadAnimation();

        this.drawY = this.drawY + 25F * Settings.scale;
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

    }

    public void reloadAnimation() {

        String atlasURL = "champResources/images/char/mainChar/champ.atlas";
        String jsonURL = "champResources/images/char/mainChar/champ.json";
        float renderscale = 1.2f;

        this.loadAnimation(
                atlasURL,
                jsonURL,
                renderscale);
//
//        if (!CharacterSelectScreenPatches.characters[4].isOriginal() && CharacterSelectScreenPatches.characters[4].reskinUnlock) {
//            this.loadAnimation(atlasURL2, this.jsonURL2, renderscale2);
//        } else {
//            this.loadAnimation(atlasURL, this.jsonURL, renderscale);
//        }
        this.state.setAnimation(0, "Idle", true);
    }


    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(ChampMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }


    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                80, 80, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public void update() {
        super.update();
        tickStanceVisualTimer();
    }

    public void switchStanceVisual(String ID) {

        if (currentIdle == "Idle") {
            stanceSwitchQueue.add(ID);
        } else {
            //  stanceSwitchQueue.add(NeutralStance.STANCE_ID);
            stanceSwitchQueue.add(ID);
        }
    }

    private void tickStanceVisualTimer() {
        if (stanceSwitchQueue.size() > 0) {
            // //SlimeboundMod.logger.info("stance queue is ticking");
            stanceSwitchAnimTimer = stanceSwitchAnimTimer - Gdx.graphics.getDeltaTime();
            if (stanceSwitchAnimTimer <= 0F) {
                switchStanceVisualGo(stanceSwitchQueue.get(0));
                stanceSwitchQueue.remove(0);
                if (stanceSwitchQueue.size() > 0) {
                    stanceSwitchAnimTimer = 0.6F;
                }
            }
        }
    }

    public void switchStanceVisualGo(String ID) {
        //   //SlimeboundMod.logger.info("stance queue has ordered a visual for " + ID);

        switch (ID) {
            case DefensiveStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "IdleDefensive", 0.5F);
                this.stateData.setMix("HitDefensive", "IdleDefensive", 0.5F);
                this.state.setAnimation(0, "IdleDefensive", true);
                currentIdle = "IdleDefensive";
                break;
            }
            case BerserkerStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "IdleBerserker", 0.5F);
                this.state.setAnimation(0, "IdleBerserker", true);
                this.stateData.setMix("HitBerserker", "IdleBerserker", 0.5F);
                currentIdle = "IdleBerserker";
                break;
            }
            case GladiatorStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "IdleGladiator", 0.5F);
                this.state.setAnimation(0, "IdleGladiator", true);
                currentIdle = "IdleGladiator";
                break;
            }
            case NeutralStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "Idle", 0.5F);
                this.state.setAnimation(0, "Idle", true);
                currentIdle = "Idle";
                break;
            }
            case UltimateStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "IdleUltimate", 0.5F);
                this.state.setAnimation(0, "IdleUltimate", true);
                currentIdle = "IdleUltimate";
                break;
            }
        }


    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 3; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(BerserkersShout.ID);
        retVal.add(DefensiveShout.ID);
        retVal.add(Taunt.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(ChampionCrown.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        if (MathUtils.randomBoolean()) {// 71
            CardCrawlGame.sound.play("VO_CHAMP_3A", 0.1F);// 72
        } else {
            CardCrawlGame.sound.play("VO_CHAMP_3B", 0.1F);// 74
        }
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public void onStanceChange(String id) {
        switchStanceVisual(id);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        if (MathUtils.randomBoolean()) {// 71
            return "VO_CHAMP_3A";// 72
        } else {
            return "VO_CHAMP_3B";// 74
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return downfallMod.Enums.CHAMP_GRAY;
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
        return new Taunt();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new ChampChar(name, chosenClass);
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
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
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
}
