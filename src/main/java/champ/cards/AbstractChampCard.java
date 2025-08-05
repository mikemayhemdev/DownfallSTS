package champ.cards;

import basemod.helpers.TooltipInfo;
import champ.ChampChar;
import champ.ChampMod;
import champ.ChampTextHelper;
import champ.patches.SignatureMovePatch;
import champ.powers.CalledShotPower;
import champ.powers.DancingMasterPower;
import champ.relics.SignatureFinisher;
import champ.stances.AbstractChampStance;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import champ.util.OnFinisherSubscriber;
import champ.util.OnOpenerSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import expansioncontent.cards.AbstractDownfallCard;
import hermit.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;

import static champ.ChampMod.*;

public abstract class AbstractChampCard extends AbstractDownfallCard {
    protected final CardStrings cardStrings;
    public String betaArtPath;
    protected final String NAME;
    public int cool;
    public int baseCool;
    public boolean upgradedCool;
    public boolean isCoolModified;
    public int myHpLossCost;
    public String DESCRIPTION;
    public String UPGRADE_DESCRIPTION;
    public String[] EXTENDED_DESCRIPTION;
    public boolean reInitDescription = true;

    public boolean techniqueLast = true;


    public AbstractChampCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, ChampChar.Enums.CHAMP_GRAY, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractChampCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public static String getCorrectPlaceholderImage(CardType type, String id) {
        String img = makeCardPath(id.replaceAll((getModID() + ":"), "") + ".png");
        if ((!Gdx.files.internal(img).exists()))
            switch (type) {
                case ATTACK:
                    return makeCardPath("Attack.png");
                case SKILL:
                    return makeCardPath("Skill.png");
                case POWER:
                    return makeCardPath("Power.png");
            }
        return img;
    }

    public static String makeID(String name) {
        return getModID() + ":" + name;
    }

    public static boolean bcombo() {
        return (AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID));
    }

    public static boolean dcombo() {
        return (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID));
    }

    public static boolean inBerserker() {
        return AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID);
    }

    public static boolean inDefensive() {
        return AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID);
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedCool) {
            cool = baseCool;
            isCoolModified = true;
        }
    }

    void upgradeCool(int amount) {
        baseCool += amount;
        cool = baseCool;
        upgradedCool = true;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (this.rawDescription.contains("[crown_icon]") && !this.rawDescription.contains("champ:Technique")) {
            tips.add(new TooltipInfo(ChampChar.characterStrings.TEXT[40], ChampChar.characterStrings.TEXT[41]));
        }
        if (this.rawDescription.contains("[fist_icon]") && !this.rawDescription.contains("champ:Finisher")) {
            tips.add(new TooltipInfo(ChampChar.characterStrings.TEXT[42], ChampChar.characterStrings.TEXT[43]));
        }
        return tips;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (this.hasTag(FINISHER)) {
            this.glowColor = AbstractDungeon.player.stance instanceof AbstractChampStance ? Color.RED.cpy() : BLUE_BORDER_GLOW_COLOR;
        }
    }

    public void triggerOpenerRelics(boolean fromNeutral) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof OnOpenerSubscriber) ((OnOpenerSubscriber) r).onOpener(fromNeutral);
        }
    }

    @Override
    public void switchedStance() {
        initializeDescription();
        super.switchedStance();
    }

    public void berserkOpen() {
      //  berserkerStance();
       // triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    public void defenseOpen() {
     //   defensiveStance();
      //  triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    protected void berserkerStance() {
        //SlimeboundMod.logger.info("Switching to Berserker (Abstract)");
        atb(new ChangeStanceAction(BerserkerStance.STANCE_ID));
    }

    protected void defensiveStance() {
        //SlimeboundMod.logger.info("Switching to Defensive (Abstract)");
        atb(new ChangeStanceAction(DefensiveStance.STANCE_ID));
    }

    protected void ultimateStance() {
        //SlimeboundMod.logger.info("Switching to THE ULTIMATE STANCE!!! (Abstract)");
        atb(new ChangeStanceAction(UltimateStance.STANCE_ID));
    }

    public void exitStance() {
        //SlimeboundMod.logger.info("Switching to Neutral (Abstract)");
        atb(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }

    public void techique() {
        if (AbstractDungeon.player.stance instanceof AbstractChampStance)
            ((AbstractChampStance) AbstractDungeon.player.stance).techique();
    }

    public void finisher (boolean noExit){

        if (AbstractDungeon.player.hasPower(DancingMasterPower.POWER_ID)){
            if (finishersThisTurn == 0){
                AbstractDungeon.player.getPower(DancingMasterPower.POWER_ID).onSpecificTrigger();
            }
        }

        ChampMod.finishersThisTurn++;
        ChampMod.finishersThisCombat++; //If there is a finishers this combat problem, maybe look here

        if (!AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
            boolean leaveStance = true;
            if (noExit || AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID)) {
                leaveStance = false;
            }
            if (AbstractDungeon.player.hasRelic(SignatureFinisher.ID)) {
                SignatureFinisher s = (SignatureFinisher) AbstractDungeon.player.getRelic(SignatureFinisher.ID);
                if (SignatureMovePatch.inSignatureMove.get(this)) {
                    leaveStance = false;
                }
            }
            if (leaveStance) {
                exitStance();
            }
            if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
                ((AbstractChampStance) AbstractDungeon.player.stance).fisher();
            }
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OnFinisherSubscriber) {
                    ((OnFinisherSubscriber) p).onFinisher();
                }
            }
        }
    }

    public void finisher() {
        finisher(false);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        ChampTextHelper.colorCombos(this, false);
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        ChampTextHelper.colorCombos(this, true);
    }

    public void postInit(){
        if (baseDamage > 0) techniqueLast = true;
    }

    @Override
    public void initializeDescription() {
        ChampTextHelper.calculateTagText(this);
        super.initializeDescription();
    }

    @Override
    public void update() {
        if (this.reInitDescription) {
            initializeDescription();
            this.reInitDescription = false;
        }
        super.update();
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean bottled = false;
        if (hasTag(FINISHER)) {
            if (p.hasRelic(SignatureFinisher.ID)){
                if ((((SignatureFinisher)p.getRelic(SignatureFinisher.ID)).card.uuid == this.uuid)){
                    bottled = true;
                };
            }
            if (!bottled) {
                if ((AbstractDungeon.player.stance instanceof NeutralStance)) {
                    this.cantUseMessage = ChampChar.characterStrings.TEXT[61];
                    return false;
                }
            }
        }
        return super.canUse(p, m);
    }

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    newPath = "champResources/images/betacards/" + newPath;
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

}