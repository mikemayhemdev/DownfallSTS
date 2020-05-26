package theHexaghost;

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
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.BobEffect;
import theHexaghost.cards.Defend;
import theHexaghost.cards.Float;
import theHexaghost.cards.Sear;
import theHexaghost.cards.Strike;
import theHexaghost.ghostflames.*;
import theHexaghost.relics.SpiritBrand;
import theHexaghost.util.TextureLoader;
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
    private static final String ID = makeID("theHexaghost");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    private static float oscillatingTimer = 0.0f;
    private static float oscillatingFader = 0.0f;
    public MyBody myBody;
    private BobEffect effect = new BobEffect(0.75F);

    public TheHexaghost(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, "hexamodResources/images/char/mainChar/orb/vfx.png", null), new SpriterAnimation(
                "hexamodResources/images/char/mainChar/static_character.scml"));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 10.0F, 0.0F, 450.0F, 450.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        myBody = new MyBody();
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
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !isDead) {
            if (activeGhostFlame != null) activeGhostFlame.renderGhostflameTriggerUI(sb);
            oscillarator();
            sb.setColor(oscillarator());
            for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                if (activeGhostFlame == gf || showAll) {
                    sb.setColor(partialTransparent);
                    float x = 0;
                    float y = 0;
                    switch (hexaGhostFlames.indexOf(gf)) {
                        case 0:
                            x = AbstractDungeon.player.drawX - (115.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (440.0F * Settings.scale);
                            break;
                        case 1:
                            x = AbstractDungeon.player.drawX + (180.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (440.0F * Settings.scale);
                            break;
                        case 2:
                            x = AbstractDungeon.player.drawX + (240.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (270.0F * Settings.scale);
                            break;
                        case 3:
                            x = AbstractDungeon.player.drawX + (170.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (100.0F * Settings.scale);
                            break;
                        case 4:
                            x = AbstractDungeon.player.drawX - (115.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (100.0F * Settings.scale);
                            break;
                        case 5:
                            x = AbstractDungeon.player.drawX - (200.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (270.0F * Settings.scale);
                            break;
                    }
                    /*
                    if (showAll) {
                        Texture b = gf.getHelperTexture();
                        sb.draw(b, x - (10 * Settings.scale), y - (10 * Settings.scale), 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);
                    }
                    */

                    float flashP = (gf.flashTimer - 1F) / .5F;
                    //SlimeboundMod.logger.info(gf.flashTimer + "," + flashP);
                    float fontScale = Settings.scale * 0.8F;
                    if (flashP > 0F) {
                        if (flashP > .5F) {
                            if (gf.charged) {
                                sb.setColor(Interpolation.exp5Out.apply(0F, 1F, flashP), 1F, Interpolation.exp5Out.apply(0F, 1F, flashP), 1F);
                                fontScale = Interpolation.linear.apply(Settings.scale * 2.5F, Settings.scale * 0.8F, flashP);
                            } else {
                                sb.setColor(1F, 1F, 1F, 1F);
                                fontScale = Interpolation.pow2Out.apply(Settings.scale * 3F, Settings.scale * 0.8F, flashP);

                            }
                        } else {
                            if (gf.charged) {
                                sb.setColor(Interpolation.exp5Out.apply(1F, 0F, flashP), 1F, Interpolation.exp5Out.apply(1F, 0F, flashP), 1F);

                                fontScale = Interpolation.linear.apply(Settings.scale * 0.8F, Settings.scale * 2.5F, flashP);
                            } else {
                                sb.setColor(1F, 1F, 1F, 1F);
                                fontScale = Interpolation.exp5In.apply(Settings.scale * 0.8F, Settings.scale * 3F, flashP);
                            }
                        }
                    } else {
                        sb.setColor(1F, 1F, 1F, 1F);
                    }

                    Texture b = gf.getHelperEffectTexture();
                    sb.setColor(1F,1F,1F,0.6F);
                    sb.draw(b, x - (gf.effectIconXOffset * Settings.scale), y + gf.effectIconYOffset * Settings.scale, 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);

                    sb.setColor(1F,1F,1F,1F);
                    FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, gf.returnHoverHelperText(), x, y, gf.textColor, fontScale);// 150 153

                    if (gf instanceof BolsteringGhostflame){
                        sb.setColor(1F,1F,1F,0.6F);
                        Texture c = TextureLoader.getTexture(HexaMod.makeUIPath("strength.png"));
                        sb.draw(c, x - (gf.effectIconXOffset * Settings.scale), y + (gf.effectIconYOffset * Settings.scale) - (50F * Settings.scale), 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);
                        sb.setColor(1F,1F,1F,1F);
                        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, "1", x, y - 50F * Settings.scale, gf.textColor, fontScale);// 150 153

                    }
                }
            }
        }
        super.render(sb);
    }

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
}
