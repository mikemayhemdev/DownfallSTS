package theHexaghost;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import theHexaghost.cards.Defend;
import theHexaghost.cards.Float;
import theHexaghost.cards.Sear;
import theHexaghost.cards.Strike;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.relics.SpiritBrand;
import theHexaghost.vfx.MyBody;

import java.util.ArrayList;
import java.util.Iterator;

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

    @Override
    protected void updateEscapeAnimation() {
        super.updateEscapeAnimation();
        if (escapeTimer > 0.0F)
            renderFlames = false;
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

    public TheHexaghost(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, "hexamodResources/images/char/mainChar/orb/vfx.png", null), new SpriterAnimation(
                "hexamodResources/images/char/mainChar/static_character.scml"));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 10.0F, 200.0F, 150.0F, 150.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        myBody = new MyBody();
    }

    public MyBody myBody;

    @Override
    public void render(SpriteBatch sb) {
        if (!(AbstractDungeon.getCurrRoom() instanceof RestRoom) && !isDead)
            myBody.render(sb);
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                if (activeGhostFlame == gf || showAll) {
                    sb.setColor(partialTransparent);
                    float x = 0;
                    float y = 0;
                    switch (hexaGhostFlames.indexOf(gf)) {
                        case 0:
                            x = AbstractDungeon.player.drawX - (130.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (440.0F * Settings.scale);
                            break;
                        case 1:
                            x = AbstractDungeon.player.drawX + (130.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (440.0F * Settings.scale);
                            break;
                        case 2:
                            x = AbstractDungeon.player.drawX + (200.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (260.0F * Settings.scale);
                            break;
                        case 3:
                            x = AbstractDungeon.player.drawX + (130.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (100.0F * Settings.scale);
                            break;
                        case 4:
                            x = AbstractDungeon.player.drawX - (130.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (100.0F * Settings.scale);
                            break;
                        case 5:
                            x = AbstractDungeon.player.drawX - (200.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (260.0F * Settings.scale);
                            break;
                    }
                    sb.draw(gf.getHelperTexture(), x - (10 * Settings.scale), y - (10 * Settings.scale));
                    sb.setColor(Color.WHITE.cpy());
                    FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, gf.returnHoverHelperText(), x, y, Color.WHITE, Settings.scale * 0.75F);// 150 153
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
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
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
