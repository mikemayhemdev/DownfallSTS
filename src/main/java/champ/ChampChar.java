package champ;

import basemod.abstracts.CustomPlayer;
import champ.cards.Defend;
import champ.cards.Execute;
import champ.cards.Strike;
import champ.cards.Taunt;
import champ.relics.ChampionCrown;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.stances.NeutralStance;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

import static champ.ChampMod.*;

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

    public float renderscale = 1.0F;

    private String atlasURL = "champResources/images/char/mainChar/champ.atlas";
    private String jsonURL = "champResources/images/char/mainChar/champ.json";

    /*
    private String atlasURL2 = "reskinContent/img/HexaghostMod/animation/Hexaghost_self_downfall.atlas";
    private String jsonURL2 = "reskinContent/img/HexaghostMod/animation/Hexaghost_self_downfall.json";
    */

    public ChampChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "champResources/images/char/mainChar/orb/vfx.png", (String)null, (String)null);

        /*
        if(!reskinContent.hexaghostReskinAnimation) {

        }else {
            if(reskinContent.hexaghostMask){
            initializeClass(null,
                    "reskinContent/img/HexaghostMod/shoulder2.png",
                    "reskinContent/img/HexaghostMod/shoulder.png",
                    CORPSE,
                    getLoadout(), -15.0F, 0.0F, 450.0F, 450.0F, new EnergyManager(3));

            }else {

            }
        }
        */
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), -15.0F, 0.0F, 450.0F, 450.0F, new EnergyManager(3));

        this.reloadAnimation();

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

    }

    public void reloadAnimation() {

        this.loadAnimation(atlasURL, this.jsonURL, renderscale);
        this.state.setAnimation(0, "Idle", true);


    }


    /*
    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(HexaMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }
    */

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                90, 90, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    public void switchStanceVisual(String ID) {

        switch (ID) {
            case DefensiveStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "IdleDefensive", 0.3F);
                this.stateData.setMix("HitDefensive", "IdleDefensive", 0.3F);
                this.state.setAnimation(0, "IdleDefensive", true);
                currentIdle = "IdleDefensive";
                break;
            }
            case GladiatorStance
                    .STANCE_ID: {
                SlimeboundMod.logger.info("Found Gladiator Stance");
                this.stateData.setMix(currentIdle, "IdleGladiator", 0.4F);
                this.stateData.setMix("HitGladiator", "IdleGladiator", 0.4F);
                this.state.setAnimation(0, "IdleGladiator", true);
                currentIdle = "IdleGladiator";
                break;
            }
            case BerserkerStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "IdleBerserker", 0.4F);
                this.state.setAnimation(0, "IdleBerserker", true);
                this.stateData.setMix("HitBerserker", "IdleBerserker", 0.4F);
                currentIdle = "IdleBerserker";
                break;
            }
            case NeutralStance
                    .STANCE_ID: {
                this.stateData.setMix(currentIdle, "Idle", 0.4F);
                this.state.setAnimation(0, "Idle", true);
                currentIdle = "Idle";
                break;
            }
        }


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

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(Execute.ID);
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
        return 9;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CHAMP_GRAY;
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

    public static class Enums {
        @SpireEnum
        public static PlayerClass THE_CHAMP;
        @SpireEnum(name = "THE_CHAMP_GRAY")
        public static AbstractCard.CardColor CHAMP_GRAY;
        @SpireEnum(name = "THE_CHAMP_GRAY")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
