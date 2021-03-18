package downfall.events;


import automaton.AutomatonChar;
import basemod.ReflectionHacks;
import champ.ChampChar;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.downfallMod;
import downfall.monsters.SneckoMirror;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.relics.HeartBlessingBlue;
import downfall.relics.HeartBlessingGreen;
import downfall.relics.HeartBlessingRed;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MindBloom_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:MindBloom";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private CurScreen screen;

    public MindBloom_Evil() {
        super(NAME, DESCRIPTIONSALT[0], "images/events/mindBloom.jpg");
        this.screen = CurScreen.INTRO;

        if (downfallMod.isDownfallCharacter(AbstractDungeon.player)){
            this.imageEventText.setDialogOption(OPTIONSALT[0]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0]);   //original Mind Bloom if you are not a villain
        }

        if (AddBustKeyButtonPatches.KeyFields.bustedRuby.get(AbstractDungeon.player) && AddBustKeyButtonPatches.KeyFields.bustedEmerald.get(AbstractDungeon.player) && AddBustKeyButtonPatches.KeyFields.bustedSapphire.get(AbstractDungeon.player)){
            this.imageEventText.setDialogOption(OPTIONSALT[2]);
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[3], true);
        }

        if (AbstractDungeon.floorNum % 50 <= 40) {
            this.imageEventText.setDialogOption(OPTIONSALT[1], CardLibrary.getCopy("Normality"));
        } else {
            this.imageEventText.setDialogOption(OPTIONS[2], CardLibrary.getCopy("Doubt"));
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        this.screen = CurScreen.FIGHT;
                        if (AbstractDungeon.player instanceof SlimeboundCharacter){
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Slime Boss");
                        } else
                        if (AbstractDungeon.player instanceof GuardianCharacter){

                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("The Guardian");
                        } else
                        if (AbstractDungeon.player instanceof TheHexaghost){

                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Hexaghost");
                        } else
                        if (AbstractDungeon.player instanceof ChampChar){
                            AbstractMonster m = new Champ();
                            m.maxHealth = Math.round(m.maxHealth * .6F);
                            m.currentHealth = m.maxHealth;
                            m.powers.add(new StrengthPower(m,-3));
                            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(m);
                        } else
                        if (AbstractDungeon.player instanceof AutomatonChar){
                            AbstractMonster m = new BronzeAutomaton();
                            m.maxHealth = Math.round(m.maxHealth * .6F);
                            m.currentHealth = m.maxHealth;
                            ReflectionHacks.setPrivate(m, BronzeAutomaton.class, "firstTurn", false);
                            m.powers.add(new StrengthPower(m,-3));
                            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(m);
                        } else
                        if (AbstractDungeon.player instanceof TheSnecko){

                            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new SneckoMirror());
                        } else {

                            ArrayList<String> list = new ArrayList();
                            list.add("Slime Boss");
                            list.add("Hexaghost");
                            list.add("The Guardian");

                            Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((String)list.get(0));
                        }

                        AbstractDungeon.getCurrRoom().rewards.clear();
                        if (AbstractDungeon.ascensionLevel >= 13) {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(25);
                        } else {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(50);
                        }

                        AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
                        this.enterCombatFromImage();
                        CardCrawlGame.music.fadeOutBGM();
                        CardCrawlGame.music.fadeOutTempBGM();
                        CardCrawlGame.music.playTempBgmInstantly("MINDBLOOM", true);
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[2]);
                        this.screen = CurScreen.LEAVE;
                        int effectCount = 0;
                        List<String> upgradedCards = new ArrayList();
                        List<String> obtainedRelic = new ArrayList();

                        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                            if (c.canUpgrade()) {
                                ++effectCount;
                                if (effectCount <= 20) {
                                    float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                                    float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                                }

                                upgradedCards.add(c.cardID);
                                c.upgrade();
                                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                            }
                        }

                        AbstractDungeon.player.loseRelic(HeartBlessingRed.ID);
                        AbstractDungeon.player.loseRelic(HeartBlessingBlue.ID);
                        AbstractDungeon.player.loseRelic(HeartBlessingGreen.ID);

                        AbstractDungeon.player.decreaseMaxHealth(10);

                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        break;
                    case 2:
                        if (AbstractDungeon.floorNum % 50 <= 40) {
                            this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                            this.screen = CurScreen.LEAVE;
                            List<String> cardsAdded = new ArrayList();
                            cardsAdded.add("Normality");
                            cardsAdded.add("Normality");
                            AbstractDungeon.effectList.add(new RainingGoldEffect(999));
                            AbstractDungeon.player.gainGold(999);
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Normality(), (float)Settings.WIDTH * 0.6F, (float)Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Normality(), (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                            this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                            this.screen = CurScreen.LEAVE;
                            AbstractCard curse = new Doubt();
                            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                            this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        }
                }

                this.imageEventText.clearRemainingOptions();
                break;
            case LEAVE:
                this.openMap();
                break;
            default:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("MindBloom");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private static enum CurScreen {
        INTRO,
        FIGHT,
        LEAVE;

        private CurScreen() {
        }
    }
}
