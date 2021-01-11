package champ.events;


import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.relics.LiftRelic;
import champ.util.OpenerModBerserker;
import champ.util.OpenerModDefensive;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.Iterator;

public class Gym extends AbstractImageEvent {
    public static final String ID = "champ:Gym";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;
    private int maxHP = 10;
    private boolean pickCard = false;
    private StanceChosen stance;

    public Gym() {
        super(NAME, DESCRIPTIONS[0], "champResources/images/events/gym.png");
        this.screen = CurScreen.INTRO;

        this.imageEventText.setDialogOption(OPTIONS[0] + this.maxHP + OPTIONS[1]);

        if (getNonOpenerCards().size() == 0) {
            this.imageEventText.setDialogOption(OPTIONS[8], true);

        } else {
            this.imageEventText.setDialogOption(OPTIONS[2]);

        }

        this.imageEventText.setDialogOption(OPTIONS[3]);
    }


    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            switch (stance){
                case DEFENSIVE:
                    CardModifierManager.addModifier(c, new OpenerModDefensive());
                    break;
                case BERSERKER:
                    CardModifierManager.addModifier(c, new OpenerModBerserker());
                    break;
            }
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
            this.imageEventText.clearAllDialogs();
            this.imageEventText.updateDialogOption(0, OPTIONS[4]);
            this.pickCard = false;
            this.screen = CurScreen.RESULT;

        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractDungeon.player.increaseMaxHp(maxHP, true);
                        this.screen = CurScreen.RESULT;
                        return;
                    case 1:
                       // this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        this.imageEventText.setDialogOption(OPTIONS[7]);
                        this.screen = CurScreen.STANCECHOICE;
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new LiftRelic());

                        this.screen = CurScreen.RESULT;
                        return;
                    default:
                        return;
                }
            case STANCECHOICE:
                switch (buttonPressed) {
                    case 0:
                        this.pickCard = true;
                        stance = StanceChosen.DEFENSIVE;
                        AbstractDungeon.gridSelectScreen.open(getNonOpenerCards(), 1, OPTIONS[5], false, false, false, false);

                        return;
                    case 1:
                        this.pickCard = true;
                        stance = StanceChosen.BERSERKER;
                        AbstractDungeon.gridSelectScreen.open(getNonOpenerCards(), 1, OPTIONS[7], false, false, false, false);
                        return;
                    default:
                        return;
                }

            default:
                this.openMap();
        }
    }

    public static CardGroup getNonOpenerCards() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while (var2.hasNext()) {
            AbstractCard c = (AbstractCard) var2.next();
            if (!c.hasTag(ChampMod.OPENER) && !c.hasTag(ChampMod.FINISHER)) {
                retVal.group.add(c);
            }
        }
      //  //SlimeboundMod.logger.info("Non Opener card count: " + retVal.group.size());
        return retVal;
    }

    private enum CurScreen {
        INTRO,
        RESULT,
        STANCECHOICE;
        CurScreen() {
        }
    }

    private enum StanceChosen {
        GLADIATOR,
        DEFENSIVE,
        BERSERKER;
        StanceChosen() {
        }
    }
}
