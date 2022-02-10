package hermit.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Slot;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.green.Terror;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.AwakenedEyeParticle;
import com.megacrit.cardcrawl.vfx.AwakenedWingParticle;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import com.megacrit.cardcrawl.vfx.scene.SilentVictoryStarEffect;
import hermit.effects.HermitEyeParticle;
import hermit.effects.HermitVictoryEmbers;
import hermit.effects.HermitVictoryMoon;
import hermit.patches.EnumPatch;
import hermit.powers.Concentration;
import hermit.powers.Rugged;
import hermit.relics.Memento;

import hermit.HermitMod;
import hermit.cards.*;

import java.util.ArrayList;
import java.util.List;

import static hermit.HermitMod.*;
import static hermit.characters.hermit.Enums.COLOR_YELLOW;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources

public class hermit extends CustomPlayer {


    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass HERMIT;
        @SpireEnum(name = "HERMIT_YELLOW") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_YELLOW;
        @SpireEnum(name = "HERMIT_YELLOW") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;
    public static float update_timer = 0;
    public static boolean moon_fade = false;

    // Imma just put these here.

    public Slot eye;
    private float fireTimer = 0.0F;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("hermit");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "hermitResources/images/char/hermit/orb/layer1.png",
            "hermitResources/images/char/hermit/orb/layer2.png",
            "hermitResources/images/char/hermit/orb/layer3.png",
            "hermitResources/images/char/hermit/orb/layer4.png",
            "hermitResources/images/char/hermit/orb/layer5.png",
            "hermitResources/images/char/hermit/orb/layer6.png",
            "hermitResources/images/char/hermit/orb/layer1d.png",
            "hermitResources/images/char/hermit/orb/layer2d.png",
            "hermitResources/images/char/hermit/orb/layer3d.png",
            "hermitResources/images/char/hermit/orb/layer4d.png",
            "hermitResources/images/char/hermit/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public hermit(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "hermitResources/images/char/hermit/orb/vfx.png", null,
                new AbstractAnimation() {
                    @Override
                    public Type type() {
                        return Type.NONE;
                    }
                });



        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_2, // campfire pose
                THE_DEFAULT_SHOULDER_1, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================

        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.eye = this.skeleton.findSlot("eye");

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        System.out.println("Begin loading starter Deck Strings");

        retVal.add(Strike_Hermit.ID);
        retVal.add(Strike_Hermit.ID);
        retVal.add(Strike_Hermit.ID);
        retVal.add(Strike_Hermit.ID);

        retVal.add(Defend_Hermit.ID);
        retVal.add(Defend_Hermit.ID);
        retVal.add(Defend_Hermit.ID);
        retVal.add(Defend_Hermit.ID);

        retVal.add(Snapshot.ID);
        retVal.add(Covet.ID);

        return retVal;

    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Memento.ID);

        //UnlockTracker.markRelicAsSeen(PlaceholderRelic.ID);


        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA(makeID("GUN1"), 1.0f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT,
                true); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return makeID("GUN1");
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_YELLOW;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return Color.YELLOW;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike_Hermit();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new hermit(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return Color.YELLOW;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return Color.YELLOW;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        AbstractGameAction.AttackEffect[] effects = {EnumPatch.HERMIT_GUN2, EnumPatch.HERMIT_GUN2, EnumPatch.HERMIT_GUN2, EnumPatch.HERMIT_GUN2, EnumPatch.HERMIT_GUN2, EnumPatch.HERMIT_GUN};
        return effects;
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e;
            if (hasPower(Rugged.POWER_ID))
                e = this.state.setAnimation(0, "HitRugged", false);
            else
                e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.6F);
        }

        super.damage(info);
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("images/scenes/greenBg.jpg");
    }


    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();

        moon_fade = false;

        panels.add(new CutscenePanel(getModID() + "Resources/images/ending/ending_1.png", makeID("GUN1")));
        panels.add(new CutscenePanel(getModID() + "Resources/images/ending/ending_2.png"));
        panels.add(new CutscenePanel(getModID() + "Resources/images/ending/ending_3.png"));

        return panels;
    }

    // Sweet victory effects go here
    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {

        if (!moon_fade) {
            effects.add(new HermitVictoryMoon());
            moon_fade = true;
        }
        update_timer += Gdx.graphics.getDeltaTime();

        for(float i = 0; i+(1.0/120.0) <= update_timer; update_timer -= (1.0/120.0)) {
            effects.add(new HermitVictoryEmbers());
        }
    }

    public void update() {
        super.update();

        if (!this.isDying && AbstractDungeon.player.hasPower(Concentration.POWER_ID)) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.1F;
                HermitEyeParticle shine = new HermitEyeParticle(this.skeleton.getX() + this.eye.getBone().getWorldX(), this.skeleton.getY() + this.eye.getBone().getWorldY());
                shine.parent = this;
                shine.skeleton = this.skeleton;
                AbstractDungeon.effectList.add(shine);
            }
        }
    }

}
