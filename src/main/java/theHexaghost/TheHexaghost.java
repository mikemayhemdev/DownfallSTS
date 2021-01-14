package theHexaghost;

import com.megacrit.cardcrawl.helpers.*;
import reskinContent.reskinContent;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.BobEffect;
import sneckomod.SneckoMod;
import theHexaghost.cards.Defend;
import theHexaghost.cards.Float;
import theHexaghost.cards.Sear;
import theHexaghost.cards.Strike;
import theHexaghost.ghostflames.*;
import theHexaghost.relics.SpiritBrand;
import downfall.util.TextureLoader;
import theHexaghost.vfx.MyBody;

import java.util.ArrayList;

import static theHexaghost.GhostflameHelper.*;
import static theHexaghost.HexaMod.*;
import static theHexaghost.TheHexaghost.Enums.GHOST_GREEN;

public class TheHexaghost extends CustomPlayer {
    private static final String[] orbTextures = {
            "hexamodResources/images/char/mainChar/orb/layer1.png",
            "hexamodResources/images/char/mainChar/orb/layer2.png",
            "hexamodResources/images/char/mainChar/orb/layer3.png",
            "hexamodResources/images/char/mainChar/orb/layer4.png",
            "hexamodResources/images/char/mainChar/orb/layer5.png",
            "hexamodResources/images/char/mainChar/orb/layer6.png",
            "hexamodResources/images/char/mainChar/orb/layer1d.png",
            "hexamodResources/images/char/mainChar/orb/layer2d.png",
            "hexamodResources/images/char/mainChar/orb/layer3d.png",
            "hexamodResources/images/char/mainChar/orb/layer4d.png",
            "hexamodResources/images/char/mainChar/orb/layer5d.png",};
    public static final String ID = makeID("theHexaghost");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    private static float oscillatingTimer = 0.0f;
    private static float oscillatingFader = 0.0f;
    public MyBody myBody;
    private BobEffect effect = new BobEffect(0.75F);

    public float renderscale = 1.0F;
    public float renderscale2 = 1.0F;

    public static AbstractGhostflame startingFlame;

    private String atlasURL = "reskinContent/img/HexaghostMod/animation/Hexaghost_original.atlas";
    private String jsonURL = "reskinContent/img/HexaghostMod/animation/Hexaghost_original.json";

    private String atlasURL2 = "reskinContent/img/HexaghostMod/animation/Hexaghost_self_downfall.atlas";
    private String jsonURL2 = "reskinContent/img/HexaghostMod/animation/Hexaghost_self_downfall.json";

    public TheHexaghost(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "hexamodResources/images/char/mainChar/orb/vfx.png", (String)null, (String)null);
        if(!reskinContent.hexaghostReskinAnimation) {
            initializeClass(null,
                    SHOULDER1,
                    SHOULDER2,
                    CORPSE,
                    getLoadout(), -15.0F, 0.0F, 450.0F, 450.0F, new EnergyManager(3));
        }else {
            if(reskinContent.hexaghostMask){
            initializeClass(null,
                    "reskinContent/img/HexaghostMod/shoulder2.png",
                    "reskinContent/img/HexaghostMod/shoulder.png",
                    CORPSE,
                    getLoadout(), -15.0F, 0.0F, 450.0F, 450.0F, new EnergyManager(3));

            }else {
                initializeClass(null,
                        "reskinContent/img/HexaghostMod/shoulderMask2.png",
                        "reskinContent/img/HexaghostMod/shoulderMask.png",
                        CORPSE,
                        getLoadout(), -15.0F, 0.0F, 450.0F, 450.0F, new EnergyManager(3));
            }
        }

        this.reloadAnimation();

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        myBody = new MyBody();
    }

    public void reloadAnimation() {
        if(reskinContent.hexaghostReskinAnimation && reskinContent.hexaghostReskinUnlock){
            this.loadAnimation(atlasURL2, this.jsonURL2, renderscale2);
        }else {
            this.loadAnimation(atlasURL, this.jsonURL, renderscale);
        }
        if(reskinContent.hexaghostMask){
            this.state.setAnimation(0, "idle2", true);
        }else {
            this.state.setAnimation(0, "idle2_mask", true);
        }



    }


    public static Color oscillarator() {
        oscillatingFader += Gdx.graphics.getRawDeltaTime() / 2;
        if (oscillatingFader > 0.66F) {
            oscillatingFader = 0.66F;
            oscillatingTimer += Gdx.graphics.getRawDeltaTime() / 2 * 1.5f;
        }
        Color col = Color.WHITE.cpy();
        col.a = (0.33F + (MathUtils.cos(oscillatingTimer) + 1.0F) / 3.0F) * oscillatingFader;
        return col;
    }

    @Override
    protected void updateEscapeAnimation() {
        super.updateEscapeAnimation();
        if (escapeTimer > 0.0F)
            renderFlames = false;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!(AbstractDungeon.getCurrRoom() instanceof RestRoom) && !isDead)
            myBody.render(sb);
        super.render(sb);
    }

    /*
    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(HexaMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }
    */

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        myBody.targetRotationSpeed = 100.0F;// 274
    }

    @Override
    public void onVictory() {
        super.onVictory();
        myBody.targetRotationSpeed = 20.0F;
    }

    @Override
    public void update() {
        super.update();
        myBody.update();
        this.effect.update();// 43
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                70, 70, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
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
        retVal.add(Sear.ID);
        retVal.add(Float.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SpiritBrand.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        if (MathUtils.randomBoolean()) {// 71
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);// 72
        } else {
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_2", 0.3F);// 74
        }
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        if (MathUtils.randomBoolean()) {// 71
            return "GHOST_ORB_IGNITE_1";// 72
        } else {
            return "GHOST_ORB_IGNITE_2";// 74
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return GHOST_GREEN;
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
        return new Sear();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheHexaghost(name, chosenClass);
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
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
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
        public static AbstractPlayer.PlayerClass THE_SPIRIT;
        @SpireEnum(name = "HEXA_GHOST_PURPLE")
        public static AbstractCard.CardColor GHOST_GREEN;
        @SpireEnum(name = "HEXA_GHOST_PURPLE")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    @Override
    public void applyStartOfTurnPowers() {
        startingFlame = activeGhostFlame;
        super.applyStartOfTurnPowers();

    }
}
