package downfall.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import downfall.util.BossCardReward;
import expansioncontent.expansionContentMod;


public class SensoryStone_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:SensoryStone";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final String INTRO_TEXT;
    private static final String INTRO_TEXT_2;
    private static final String MEMORY_SLIME_TEXT;
    private static final String MEMORY_GUARDIAN_TEXT;
    private static final String MEMORY_HEXA_TEXT;
    private static final String MEMORY_AUTOMATON_TEXT;
    private static final String MEMORY_CHAMP_TEXT;
    private static final String MEMORY_COLLECTOR_TEXT;
    private static final String MEMORY_DONUDECA_TEXT;
    private static final String MEMORY_ANCIENTONE_TEXT;
    private static final String MEMORY_TIMEEATER_TEXT;
    private CurScreen screen;
    private int choice;
    private AbstractCard memoryCard = null;

    public SensoryStone_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/sensoryStone.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        this.imageEventText.setDialogOption(OPTIONS[5]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SENSORY");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                this.imageEventText.updateBodyText(INTRO_TEXT_2);
                this.imageEventText.updateDialogOption(0, OPTIONSALT[0]);
                this.imageEventText.setDialogOption(OPTIONSALT[1] + 5 + OPTIONS[3]);
                this.imageEventText.setDialogOption(OPTIONSALT[2] + 10 + OPTIONS[3]);
                this.screen = CurScreen.INTRO_2;
                break;
            case INTRO_2:
                switch(buttonPressed) {
                    case 0:
                        this.screen = CurScreen.ACCEPT;
                        this.choice = 1;
                        this.reward(this.choice);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        getMemoryText();
                        break;
                    case 1:
                        this.screen = CurScreen.ACCEPT;
                        this.choice = 2;
                        this.reward(this.choice);
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, 5, DamageType.HP_LOSS));
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        getMemoryText();
                        break;
                    case 2:
                        this.screen = CurScreen.ACCEPT;
                        this.choice = 3;
                        this.reward(this.choice);
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, 10, DamageType.HP_LOSS));
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        getMemoryText();
                }

                this.imageEventText.clearRemainingOptions();
                break;
            case ACCEPT:
                this.reward(this.choice);
            default:
                this.openMap();
        }

    }

    public void setMemoryCard(AbstractCard c){
        this.memoryCard = c;
       // if (memoryCard != null) //SlimeboundMod.logger.info("setting memory card: " + c.name);
    }

    public void getMemoryText() {
        String memory = "";
        AbstractCard c = this.memoryCard;

        boolean debug = false;
        if (c != null) {
            if (!debug) {
              //  //SlimeboundMod.logger.info("searching tags for " + c.name);
                if (c.hasTag(expansionContentMod.STUDY_SLIMEBOSS)) memory = MEMORY_SLIME_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_AUTOMATON)) memory = MEMORY_AUTOMATON_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_AWAKENEDONE)) memory = MEMORY_ANCIENTONE_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_CHAMP)) memory = MEMORY_CHAMP_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_COLLECTOR)) memory = MEMORY_COLLECTOR_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_GUARDIAN)) memory = MEMORY_GUARDIAN_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_HEXAGHOST)) memory = MEMORY_HEXA_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_SHAPES)) memory = MEMORY_DONUDECA_TEXT;
                else if (c.hasTag(expansionContentMod.STUDY_TIMEEATER)) memory = MEMORY_TIMEEATER_TEXT;
                else {
                    //SHOULD NEVER HAPPEN
                    memory = DESCRIPTIONSALT[AbstractDungeon.cardRng.random(0, 8)];
                }
            } else {
                //Set this to whatever text needs to be debugged and set the above DEBUG variable to true
                memory = MEMORY_TIMEEATER_TEXT;
            }
        } else {
            //triggers if no card is chosen
            memory = DESCRIPTIONSALT[9];
        }
     //   //SlimeboundMod.logger.info(memory);
        this.imageEventText.updateBodyText(memory);
    }

    private void reward(int num) {
        AbstractDungeon.getCurrRoom().rewards.clear();

        for(int i = 0; i < num; ++i) {
            AbstractDungeon.getCurrRoom().addCardReward(new BossCardReward());
        }

        AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
        this.screen = CurScreen.LEAVE;
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("SensoryStone");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
        INTRO_TEXT = DESCRIPTIONS[0];
        INTRO_TEXT_2 = DESCRIPTIONS[1];
        MEMORY_SLIME_TEXT = DESCRIPTIONSALT[0];
        MEMORY_GUARDIAN_TEXT = DESCRIPTIONSALT[1];
        MEMORY_HEXA_TEXT = DESCRIPTIONSALT[2];
        MEMORY_AUTOMATON_TEXT = DESCRIPTIONSALT[3];
        MEMORY_CHAMP_TEXT = DESCRIPTIONSALT[4];
        MEMORY_COLLECTOR_TEXT = DESCRIPTIONSALT[5];
        MEMORY_DONUDECA_TEXT = DESCRIPTIONSALT[6];
        MEMORY_ANCIENTONE_TEXT = DESCRIPTIONSALT[7];
        MEMORY_TIMEEATER_TEXT = DESCRIPTIONSALT[8];
    }

    private static enum CurScreen {
        INTRO,
        INTRO_2,
        ACCEPT,
        LEAVE;

        private CurScreen() {
        }
    }
}
